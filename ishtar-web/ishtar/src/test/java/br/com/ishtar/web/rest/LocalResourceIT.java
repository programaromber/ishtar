package br.com.ishtar.web.rest;

import br.com.ishtar.IshtarApp;
import br.com.ishtar.domain.Local;
import br.com.ishtar.repository.LocalRepository;
import br.com.ishtar.service.LocalService;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static br.com.ishtar.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link LocalResource} REST controller.
 */
@SpringBootTest(classes = IshtarApp.class)
public class LocalResourceIT {

    private static final String DEFAULT_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMEMTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMEMTO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBB";

    private static final BigDecimal DEFAULT_LATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LATITUDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONGITUDE = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATA_CADASTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CADASTRO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private LocalService localService;

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

    private MockMvc restLocalMockMvc;

    private Local local;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalResource localResource = new LocalResource(localService);
        this.restLocalMockMvc = MockMvcBuilders.standaloneSetup(localResource)
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
    public static Local createEntity(EntityManager em) {
        Local local = new Local()
            .logradouro(DEFAULT_LOGRADOURO)
            .complememto(DEFAULT_COMPLEMEMTO)
            .bairro(DEFAULT_BAIRRO)
            .cep(DEFAULT_CEP)
            .numero(DEFAULT_NUMERO)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .dataCadastro(DEFAULT_DATA_CADASTRO)
            .ativo(DEFAULT_ATIVO);
        return local;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Local createUpdatedEntity(EntityManager em) {
        Local local = new Local()
            .logradouro(UPDATED_LOGRADOURO)
            .complememto(UPDATED_COMPLEMEMTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .numero(UPDATED_NUMERO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .ativo(UPDATED_ATIVO);
        return local;
    }

    @BeforeEach
    public void initTest() {
        local = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocal() throws Exception {
        int databaseSizeBeforeCreate = localRepository.findAll().size();

        // Create the Local
        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isCreated());

        // Validate the Local in the database
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeCreate + 1);
        Local testLocal = localList.get(localList.size() - 1);
        assertThat(testLocal.getLogradouro()).isEqualTo(DEFAULT_LOGRADOURO);
        assertThat(testLocal.getComplememto()).isEqualTo(DEFAULT_COMPLEMEMTO);
        assertThat(testLocal.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testLocal.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testLocal.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testLocal.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testLocal.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testLocal.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
        assertThat(testLocal.isAtivo()).isEqualTo(DEFAULT_ATIVO);
    }

    @Test
    @Transactional
    public void createLocalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localRepository.findAll().size();

        // Create the Local with an existing ID
        local.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        // Validate the Local in the database
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLogradouroIsRequired() throws Exception {
        int databaseSizeBeforeTest = localRepository.findAll().size();
        // set the field null
        local.setLogradouro(null);

        // Create the Local, which fails.

        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBairroIsRequired() throws Exception {
        int databaseSizeBeforeTest = localRepository.findAll().size();
        // set the field null
        local.setBairro(null);

        // Create the Local, which fails.

        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCepIsRequired() throws Exception {
        int databaseSizeBeforeTest = localRepository.findAll().size();
        // set the field null
        local.setCep(null);

        // Create the Local, which fails.

        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = localRepository.findAll().size();
        // set the field null
        local.setNumero(null);

        // Create the Local, which fails.

        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataCadastroIsRequired() throws Exception {
        int databaseSizeBeforeTest = localRepository.findAll().size();
        // set the field null
        local.setDataCadastro(null);

        // Create the Local, which fails.

        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = localRepository.findAll().size();
        // set the field null
        local.setAtivo(null);

        // Create the Local, which fails.

        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLocals() throws Exception {
        // Initialize the database
        localRepository.saveAndFlush(local);

        // Get all the localList
        restLocalMockMvc.perform(get("/api/locals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(local.getId().intValue())))
            .andExpect(jsonPath("$.[*].logradouro").value(hasItem(DEFAULT_LOGRADOURO.toString())))
            .andExpect(jsonPath("$.[*].complememto").value(hasItem(DEFAULT_COMPLEMEMTO.toString())))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLocal() throws Exception {
        // Initialize the database
        localRepository.saveAndFlush(local);

        // Get the local
        restLocalMockMvc.perform(get("/api/locals/{id}", local.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(local.getId().intValue()))
            .andExpect(jsonPath("$.logradouro").value(DEFAULT_LOGRADOURO.toString()))
            .andExpect(jsonPath("$.complememto").value(DEFAULT_COMPLEMEMTO.toString()))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO.toString()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.intValue()))
            .andExpect(jsonPath("$.dataCadastro").value(DEFAULT_DATA_CADASTRO.toString()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLocal() throws Exception {
        // Get the local
        restLocalMockMvc.perform(get("/api/locals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocal() throws Exception {
        // Initialize the database
        localService.save(local);

        int databaseSizeBeforeUpdate = localRepository.findAll().size();

        // Update the local
        Local updatedLocal = localRepository.findById(local.getId()).get();
        // Disconnect from session so that the updates on updatedLocal are not directly saved in db
        em.detach(updatedLocal);
        updatedLocal
            .logradouro(UPDATED_LOGRADOURO)
            .complememto(UPDATED_COMPLEMEMTO)
            .bairro(UPDATED_BAIRRO)
            .cep(UPDATED_CEP)
            .numero(UPDATED_NUMERO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .ativo(UPDATED_ATIVO);

        restLocalMockMvc.perform(put("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocal)))
            .andExpect(status().isOk());

        // Validate the Local in the database
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeUpdate);
        Local testLocal = localList.get(localList.size() - 1);
        assertThat(testLocal.getLogradouro()).isEqualTo(UPDATED_LOGRADOURO);
        assertThat(testLocal.getComplememto()).isEqualTo(UPDATED_COMPLEMEMTO);
        assertThat(testLocal.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testLocal.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testLocal.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testLocal.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testLocal.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testLocal.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testLocal.isAtivo()).isEqualTo(UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingLocal() throws Exception {
        int databaseSizeBeforeUpdate = localRepository.findAll().size();

        // Create the Local

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLocalMockMvc.perform(put("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        // Validate the Local in the database
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLocal() throws Exception {
        // Initialize the database
        localService.save(local);

        int databaseSizeBeforeDelete = localRepository.findAll().size();

        // Delete the local
        restLocalMockMvc.perform(delete("/api/locals/{id}", local.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Local.class);
        Local local1 = new Local();
        local1.setId(1L);
        Local local2 = new Local();
        local2.setId(local1.getId());
        assertThat(local1).isEqualTo(local2);
        local2.setId(2L);
        assertThat(local1).isNotEqualTo(local2);
        local1.setId(null);
        assertThat(local1).isNotEqualTo(local2);
    }
}
