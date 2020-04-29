package br.com.ishtar.web.rest;

import br.com.ishtar.IshtarApp;
import br.com.ishtar.domain.Cidade;
import br.com.ishtar.repository.CidadeRepository;
import br.com.ishtar.service.CidadeService;
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

import br.com.ishtar.domain.enumeration.Municipio;
import br.com.ishtar.domain.enumeration.Estado;
/**
 * Integration tests for the {@Link CidadeResource} REST controller.
 */
@SpringBootTest(classes = IshtarApp.class)
public class CidadeResourceIT {

    private static final Municipio DEFAULT_MUNICIPIO = Municipio.BELEM;
    private static final Municipio UPDATED_MUNICIPIO = Municipio.CASTANHAL;

    private static final Estado DEFAULT_ESTADO = Estado.RO;
    private static final Estado UPDATED_ESTADO = Estado.AC;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

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

    private MockMvc restCidadeMockMvc;

    private Cidade cidade;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CidadeResource cidadeResource = new CidadeResource(cidadeService);
        this.restCidadeMockMvc = MockMvcBuilders.standaloneSetup(cidadeResource)
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
    public static Cidade createEntity(EntityManager em) {
        Cidade cidade = new Cidade()
            .municipio(DEFAULT_MUNICIPIO)
            .estado(DEFAULT_ESTADO);
        return cidade;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cidade createUpdatedEntity(EntityManager em) {
        Cidade cidade = new Cidade()
            .municipio(UPDATED_MUNICIPIO)
            .estado(UPDATED_ESTADO);
        return cidade;
    }

    @BeforeEach
    public void initTest() {
        cidade = createEntity(em);
    }

    @Test
    @Transactional
    public void createCidade() throws Exception {
        int databaseSizeBeforeCreate = cidadeRepository.findAll().size();

        // Create the Cidade
        restCidadeMockMvc.perform(post("/api/cidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cidade)))
            .andExpect(status().isCreated());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Cidade testCidade = cidadeList.get(cidadeList.size() - 1);
        assertThat(testCidade.getMunicipio()).isEqualTo(DEFAULT_MUNICIPIO);
        assertThat(testCidade.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createCidadeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cidadeRepository.findAll().size();

        // Create the Cidade with an existing ID
        cidade.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCidadeMockMvc.perform(post("/api/cidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cidade)))
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMunicipioIsRequired() throws Exception {
        int databaseSizeBeforeTest = cidadeRepository.findAll().size();
        // set the field null
        cidade.setMunicipio(null);

        // Create the Cidade, which fails.

        restCidadeMockMvc.perform(post("/api/cidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cidade)))
            .andExpect(status().isBadRequest());

        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cidadeRepository.findAll().size();
        // set the field null
        cidade.setEstado(null);

        // Create the Cidade, which fails.

        restCidadeMockMvc.perform(post("/api/cidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cidade)))
            .andExpect(status().isBadRequest());

        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCidades() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get all the cidadeList
        restCidadeMockMvc.perform(get("/api/cidades?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].municipio").value(hasItem(DEFAULT_MUNICIPIO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getCidade() throws Exception {
        // Initialize the database
        cidadeRepository.saveAndFlush(cidade);

        // Get the cidade
        restCidadeMockMvc.perform(get("/api/cidades/{id}", cidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cidade.getId().intValue()))
            .andExpect(jsonPath("$.municipio").value(DEFAULT_MUNICIPIO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCidade() throws Exception {
        // Get the cidade
        restCidadeMockMvc.perform(get("/api/cidades/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCidade() throws Exception {
        // Initialize the database
        cidadeService.save(cidade);

        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();

        // Update the cidade
        Cidade updatedCidade = cidadeRepository.findById(cidade.getId()).get();
        // Disconnect from session so that the updates on updatedCidade are not directly saved in db
        em.detach(updatedCidade);
        updatedCidade
            .municipio(UPDATED_MUNICIPIO)
            .estado(UPDATED_ESTADO);

        restCidadeMockMvc.perform(put("/api/cidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCidade)))
            .andExpect(status().isOk());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
        Cidade testCidade = cidadeList.get(cidadeList.size() - 1);
        assertThat(testCidade.getMunicipio()).isEqualTo(UPDATED_MUNICIPIO);
        assertThat(testCidade.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingCidade() throws Exception {
        int databaseSizeBeforeUpdate = cidadeRepository.findAll().size();

        // Create the Cidade

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCidadeMockMvc.perform(put("/api/cidades")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cidade)))
            .andExpect(status().isBadRequest());

        // Validate the Cidade in the database
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCidade() throws Exception {
        // Initialize the database
        cidadeService.save(cidade);

        int databaseSizeBeforeDelete = cidadeRepository.findAll().size();

        // Delete the cidade
        restCidadeMockMvc.perform(delete("/api/cidades/{id}", cidade.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertThat(cidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cidade.class);
        Cidade cidade1 = new Cidade();
        cidade1.setId(1L);
        Cidade cidade2 = new Cidade();
        cidade2.setId(cidade1.getId());
        assertThat(cidade1).isEqualTo(cidade2);
        cidade2.setId(2L);
        assertThat(cidade1).isNotEqualTo(cidade2);
        cidade1.setId(null);
        assertThat(cidade1).isNotEqualTo(cidade2);
    }
}
