package br.com.ishtar.web.rest;

import br.com.ishtar.IshtarApp;
import br.com.ishtar.domain.Participacao;
import br.com.ishtar.repository.ParticipacaoRepository;
import br.com.ishtar.service.ParticipacaoService;
import br.com.ishtar.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.ishtar.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ParticipacaoResource} REST controller.
 */
@SpringBootTest(classes = IshtarApp.class)
public class ParticipacaoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private ParticipacaoRepository participacaoRepository;

    @Autowired
    private ParticipacaoService participacaoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restParticipacaoMockMvc;

    private Participacao participacao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParticipacaoResource participacaoResource = new ParticipacaoResource(participacaoService);
        this.restParticipacaoMockMvc = MockMvcBuilders.standaloneSetup(participacaoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participacao createEntity(EntityManager em) {
        Participacao participacao = new Participacao()
            .nome(DEFAULT_NOME);
        return participacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participacao createUpdatedEntity(EntityManager em) {
        Participacao participacao = new Participacao()
            .nome(UPDATED_NOME);
        return participacao;
    }

    @BeforeEach
    public void initTest() {
        participacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createParticipacao() throws Exception {
        int databaseSizeBeforeCreate = participacaoRepository.findAll().size();

        // Create the Participacao
        restParticipacaoMockMvc.perform(post("/api/participacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participacao)))
            .andExpect(status().isCreated());

        // Validate the Participacao in the database
        List<Participacao> participacaoList = participacaoRepository.findAll();
        assertThat(participacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Participacao testParticipacao = participacaoList.get(participacaoList.size() - 1);
        assertThat(testParticipacao.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createParticipacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = participacaoRepository.findAll().size();

        // Create the Participacao with an existing ID
        participacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipacaoMockMvc.perform(post("/api/participacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participacao)))
            .andExpect(status().isBadRequest());

        // Validate the Participacao in the database
        List<Participacao> participacaoList = participacaoRepository.findAll();
        assertThat(participacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = participacaoRepository.findAll().size();
        // set the field null
        participacao.setNome(null);

        // Create the Participacao, which fails.

        restParticipacaoMockMvc.perform(post("/api/participacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participacao)))
            .andExpect(status().isBadRequest());

        List<Participacao> participacaoList = participacaoRepository.findAll();
        assertThat(participacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParticipacaos() throws Exception {
        // Initialize the database
        participacaoRepository.saveAndFlush(participacao);

        // Get all the participacaoList
        restParticipacaoMockMvc.perform(get("/api/participacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }
    
    @Test
    @Transactional
    public void getParticipacao() throws Exception {
        // Initialize the database
        participacaoRepository.saveAndFlush(participacao);

        // Get the participacao
        restParticipacaoMockMvc.perform(get("/api/participacaos/{id}", participacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(participacao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParticipacao() throws Exception {
        // Get the participacao
        restParticipacaoMockMvc.perform(get("/api/participacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParticipacao() throws Exception {
        // Initialize the database
        participacaoService.save(participacao);

        int databaseSizeBeforeUpdate = participacaoRepository.findAll().size();

        // Update the participacao
        Participacao updatedParticipacao = participacaoRepository.findById(participacao.getId()).get();
        // Disconnect from session so that the updates on updatedParticipacao are not directly saved in db
        em.detach(updatedParticipacao);
        updatedParticipacao
            .nome(UPDATED_NOME);

        restParticipacaoMockMvc.perform(put("/api/participacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParticipacao)))
            .andExpect(status().isOk());

        // Validate the Participacao in the database
        List<Participacao> participacaoList = participacaoRepository.findAll();
        assertThat(participacaoList).hasSize(databaseSizeBeforeUpdate);
        Participacao testParticipacao = participacaoList.get(participacaoList.size() - 1);
        assertThat(testParticipacao.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingParticipacao() throws Exception {
        int databaseSizeBeforeUpdate = participacaoRepository.findAll().size();

        // Create the Participacao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipacaoMockMvc.perform(put("/api/participacaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participacao)))
            .andExpect(status().isBadRequest());

        // Validate the Participacao in the database
        List<Participacao> participacaoList = participacaoRepository.findAll();
        assertThat(participacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParticipacao() throws Exception {
        // Initialize the database
        participacaoService.save(participacao);

        int databaseSizeBeforeDelete = participacaoRepository.findAll().size();

        // Delete the participacao
        restParticipacaoMockMvc.perform(delete("/api/participacaos/{id}", participacao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Participacao> participacaoList = participacaoRepository.findAll();
        assertThat(participacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Participacao.class);
        Participacao participacao1 = new Participacao();
        participacao1.setId(1L);
        Participacao participacao2 = new Participacao();
        participacao2.setId(participacao1.getId());
        assertThat(participacao1).isEqualTo(participacao2);
        participacao2.setId(2L);
        assertThat(participacao1).isNotEqualTo(participacao2);
        participacao1.setId(null);
        assertThat(participacao1).isNotEqualTo(participacao2);
    }
}
