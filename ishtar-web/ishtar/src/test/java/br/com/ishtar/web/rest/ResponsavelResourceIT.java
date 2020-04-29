package br.com.ishtar.web.rest;

import br.com.ishtar.IshtarApp;
import br.com.ishtar.domain.Responsavel;
import br.com.ishtar.repository.ResponsavelRepository;
import br.com.ishtar.service.ResponsavelService;
import br.com.ishtar.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static br.com.ishtar.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ResponsavelResource} REST controller.
 */
@SpringBootTest(classes = IshtarApp.class)
public class ResponsavelResourceIT {

    private static final LocalDate DEFAULT_DATA_CADASTRO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CADASTRO = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ATIVO = false;
    private static final Boolean UPDATED_ATIVO = true;

    @Autowired
    private ResponsavelRepository responsavelRepository;

    @Mock
    private ResponsavelRepository responsavelRepositoryMock;

    @Mock
    private ResponsavelService responsavelServiceMock;

    @Autowired
    private ResponsavelService responsavelService;

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

    private MockMvc restResponsavelMockMvc;

    private Responsavel responsavel;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResponsavelResource responsavelResource = new ResponsavelResource(responsavelService);
        this.restResponsavelMockMvc = MockMvcBuilders.standaloneSetup(responsavelResource)
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
    public static Responsavel createEntity(EntityManager em) {
        Responsavel responsavel = new Responsavel()
            .dataCadastro(DEFAULT_DATA_CADASTRO)
            .ativo(DEFAULT_ATIVO);
        return responsavel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Responsavel createUpdatedEntity(EntityManager em) {
        Responsavel responsavel = new Responsavel()
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .ativo(UPDATED_ATIVO);
        return responsavel;
    }

    @BeforeEach
    public void initTest() {
        responsavel = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponsavel() throws Exception {
        int databaseSizeBeforeCreate = responsavelRepository.findAll().size();

        // Create the Responsavel
        restResponsavelMockMvc.perform(post("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isCreated());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeCreate + 1);
        Responsavel testResponsavel = responsavelList.get(responsavelList.size() - 1);
        assertThat(testResponsavel.getDataCadastro()).isEqualTo(DEFAULT_DATA_CADASTRO);
        assertThat(testResponsavel.isAtivo()).isEqualTo(DEFAULT_ATIVO);
    }

    @Test
    @Transactional
    public void createResponsavelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = responsavelRepository.findAll().size();

        // Create the Responsavel with an existing ID
        responsavel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsavelMockMvc.perform(post("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isBadRequest());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataCadastroIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelRepository.findAll().size();
        // set the field null
        responsavel.setDataCadastro(null);

        // Create the Responsavel, which fails.

        restResponsavelMockMvc.perform(post("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isBadRequest());

        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAtivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = responsavelRepository.findAll().size();
        // set the field null
        responsavel.setAtivo(null);

        // Create the Responsavel, which fails.

        restResponsavelMockMvc.perform(post("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isBadRequest());

        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResponsavels() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get all the responsavelList
        restResponsavelMockMvc.perform(get("/api/responsavels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsavel.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataCadastro").value(hasItem(DEFAULT_DATA_CADASTRO.toString())))
            .andExpect(jsonPath("$.[*].ativo").value(hasItem(DEFAULT_ATIVO.booleanValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllResponsavelsWithEagerRelationshipsIsEnabled() throws Exception {
        ResponsavelResource responsavelResource = new ResponsavelResource(responsavelServiceMock);
        when(responsavelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restResponsavelMockMvc = MockMvcBuilders.standaloneSetup(responsavelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restResponsavelMockMvc.perform(get("/api/responsavels?eagerload=true"))
        .andExpect(status().isOk());

        verify(responsavelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllResponsavelsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ResponsavelResource responsavelResource = new ResponsavelResource(responsavelServiceMock);
            when(responsavelServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restResponsavelMockMvc = MockMvcBuilders.standaloneSetup(responsavelResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restResponsavelMockMvc.perform(get("/api/responsavels?eagerload=true"))
        .andExpect(status().isOk());

            verify(responsavelServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getResponsavel() throws Exception {
        // Initialize the database
        responsavelRepository.saveAndFlush(responsavel);

        // Get the responsavel
        restResponsavelMockMvc.perform(get("/api/responsavels/{id}", responsavel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(responsavel.getId().intValue()))
            .andExpect(jsonPath("$.dataCadastro").value(DEFAULT_DATA_CADASTRO.toString()))
            .andExpect(jsonPath("$.ativo").value(DEFAULT_ATIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingResponsavel() throws Exception {
        // Get the responsavel
        restResponsavelMockMvc.perform(get("/api/responsavels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsavel() throws Exception {
        // Initialize the database
        responsavelService.save(responsavel);

        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();

        // Update the responsavel
        Responsavel updatedResponsavel = responsavelRepository.findById(responsavel.getId()).get();
        // Disconnect from session so that the updates on updatedResponsavel are not directly saved in db
        em.detach(updatedResponsavel);
        updatedResponsavel
            .dataCadastro(UPDATED_DATA_CADASTRO)
            .ativo(UPDATED_ATIVO);

        restResponsavelMockMvc.perform(put("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResponsavel)))
            .andExpect(status().isOk());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
        Responsavel testResponsavel = responsavelList.get(responsavelList.size() - 1);
        assertThat(testResponsavel.getDataCadastro()).isEqualTo(UPDATED_DATA_CADASTRO);
        assertThat(testResponsavel.isAtivo()).isEqualTo(UPDATED_ATIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingResponsavel() throws Exception {
        int databaseSizeBeforeUpdate = responsavelRepository.findAll().size();

        // Create the Responsavel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResponsavelMockMvc.perform(put("/api/responsavels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsavel)))
            .andExpect(status().isBadRequest());

        // Validate the Responsavel in the database
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResponsavel() throws Exception {
        // Initialize the database
        responsavelService.save(responsavel);

        int databaseSizeBeforeDelete = responsavelRepository.findAll().size();

        // Delete the responsavel
        restResponsavelMockMvc.perform(delete("/api/responsavels/{id}", responsavel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Responsavel> responsavelList = responsavelRepository.findAll();
        assertThat(responsavelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Responsavel.class);
        Responsavel responsavel1 = new Responsavel();
        responsavel1.setId(1L);
        Responsavel responsavel2 = new Responsavel();
        responsavel2.setId(responsavel1.getId());
        assertThat(responsavel1).isEqualTo(responsavel2);
        responsavel2.setId(2L);
        assertThat(responsavel1).isNotEqualTo(responsavel2);
        responsavel1.setId(null);
        assertThat(responsavel1).isNotEqualTo(responsavel2);
    }
}
