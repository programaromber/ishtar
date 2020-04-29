package br.com.ishtar.web.rest;

import br.com.ishtar.IshtarApp;
import br.com.ishtar.domain.Polo;
import br.com.ishtar.repository.PoloRepository;
import br.com.ishtar.service.PoloService;
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
 * Integration tests for the {@Link PoloResource} REST controller.
 */
@SpringBootTest(classes = IshtarApp.class)
public class PoloResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DDD = "AA";
    private static final String UPDATED_DDD = "BB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    @Autowired
    private PoloRepository poloRepository;

    @Autowired
    private PoloService poloService;

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

    private MockMvc restPoloMockMvc;

    private Polo polo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PoloResource poloResource = new PoloResource(poloService);
        this.restPoloMockMvc = MockMvcBuilders.standaloneSetup(poloResource)
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
    public static Polo createEntity(EntityManager em) {
        Polo polo = new Polo()
            .nome(DEFAULT_NOME)
            .email(DEFAULT_EMAIL)
            .ddd(DEFAULT_DDD)
            .telefone(DEFAULT_TELEFONE);
        return polo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Polo createUpdatedEntity(EntityManager em) {
        Polo polo = new Polo()
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .ddd(UPDATED_DDD)
            .telefone(UPDATED_TELEFONE);
        return polo;
    }

    @BeforeEach
    public void initTest() {
        polo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPolo() throws Exception {
        int databaseSizeBeforeCreate = poloRepository.findAll().size();

        // Create the Polo
        restPoloMockMvc.perform(post("/api/polos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(polo)))
            .andExpect(status().isCreated());

        // Validate the Polo in the database
        List<Polo> poloList = poloRepository.findAll();
        assertThat(poloList).hasSize(databaseSizeBeforeCreate + 1);
        Polo testPolo = poloList.get(poloList.size() - 1);
        assertThat(testPolo.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPolo.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPolo.getDdd()).isEqualTo(DEFAULT_DDD);
        assertThat(testPolo.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    public void createPoloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = poloRepository.findAll().size();

        // Create the Polo with an existing ID
        polo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPoloMockMvc.perform(post("/api/polos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(polo)))
            .andExpect(status().isBadRequest());

        // Validate the Polo in the database
        List<Polo> poloList = poloRepository.findAll();
        assertThat(poloList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = poloRepository.findAll().size();
        // set the field null
        polo.setNome(null);

        // Create the Polo, which fails.

        restPoloMockMvc.perform(post("/api/polos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(polo)))
            .andExpect(status().isBadRequest());

        List<Polo> poloList = poloRepository.findAll();
        assertThat(poloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPolos() throws Exception {
        // Initialize the database
        poloRepository.saveAndFlush(polo);

        // Get all the poloList
        restPoloMockMvc.perform(get("/api/polos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(polo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].ddd").value(hasItem(DEFAULT_DDD.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())));
    }
    
    @Test
    @Transactional
    public void getPolo() throws Exception {
        // Initialize the database
        poloRepository.saveAndFlush(polo);

        // Get the polo
        restPoloMockMvc.perform(get("/api/polos/{id}", polo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(polo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.ddd").value(DEFAULT_DDD.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPolo() throws Exception {
        // Get the polo
        restPoloMockMvc.perform(get("/api/polos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePolo() throws Exception {
        // Initialize the database
        poloService.save(polo);

        int databaseSizeBeforeUpdate = poloRepository.findAll().size();

        // Update the polo
        Polo updatedPolo = poloRepository.findById(polo.getId()).get();
        // Disconnect from session so that the updates on updatedPolo are not directly saved in db
        em.detach(updatedPolo);
        updatedPolo
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .ddd(UPDATED_DDD)
            .telefone(UPDATED_TELEFONE);

        restPoloMockMvc.perform(put("/api/polos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPolo)))
            .andExpect(status().isOk());

        // Validate the Polo in the database
        List<Polo> poloList = poloRepository.findAll();
        assertThat(poloList).hasSize(databaseSizeBeforeUpdate);
        Polo testPolo = poloList.get(poloList.size() - 1);
        assertThat(testPolo.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPolo.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPolo.getDdd()).isEqualTo(UPDATED_DDD);
        assertThat(testPolo.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void updateNonExistingPolo() throws Exception {
        int databaseSizeBeforeUpdate = poloRepository.findAll().size();

        // Create the Polo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPoloMockMvc.perform(put("/api/polos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(polo)))
            .andExpect(status().isBadRequest());

        // Validate the Polo in the database
        List<Polo> poloList = poloRepository.findAll();
        assertThat(poloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePolo() throws Exception {
        // Initialize the database
        poloService.save(polo);

        int databaseSizeBeforeDelete = poloRepository.findAll().size();

        // Delete the polo
        restPoloMockMvc.perform(delete("/api/polos/{id}", polo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Polo> poloList = poloRepository.findAll();
        assertThat(poloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Polo.class);
        Polo polo1 = new Polo();
        polo1.setId(1L);
        Polo polo2 = new Polo();
        polo2.setId(polo1.getId());
        assertThat(polo1).isEqualTo(polo2);
        polo2.setId(2L);
        assertThat(polo1).isNotEqualTo(polo2);
        polo1.setId(null);
        assertThat(polo1).isNotEqualTo(polo2);
    }
}
