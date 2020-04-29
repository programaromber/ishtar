package br.com.ishtar.web.rest;

import br.com.ishtar.IshtarApp;
import br.com.ishtar.domain.Participante;
import br.com.ishtar.repository.ParticipanteRepository;
import br.com.ishtar.service.ParticipanteService;
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
 * Integration tests for the {@Link ParticipanteResource} REST controller.
 */
@SpringBootTest(classes = IshtarApp.class)
public class ParticipanteResourceIT {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DDD = "AA";
    private static final String UPDATED_DDD = "BB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NOTIFICAR = false;
    private static final Boolean UPDATED_NOTIFICAR = true;

    private static final Boolean DEFAULT_ACEITO = false;
    private static final Boolean UPDATED_ACEITO = true;

    private static final BigDecimal DEFAULT_LATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LATITUDE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONGITUDE = new BigDecimal(2);

    private static final LocalDate DEFAULT_DATA_ATUALIZACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ATUALIZACAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ParticipanteRepository participanteRepository;

    @Autowired
    private ParticipanteService participanteService;

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

    private MockMvc restParticipanteMockMvc;

    private Participante participante;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParticipanteResource participanteResource = new ParticipanteResource(participanteService);
        this.restParticipanteMockMvc = MockMvcBuilders.standaloneSetup(participanteResource)
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
    public static Participante createEntity(EntityManager em) {
        Participante participante = new Participante()
            .email(DEFAULT_EMAIL)
            .nome(DEFAULT_NOME)
            .ddd(DEFAULT_DDD)
            .telefone(DEFAULT_TELEFONE)
            .notificar(DEFAULT_NOTIFICAR)
            .aceito(DEFAULT_ACEITO)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .dataAtualizacao(DEFAULT_DATA_ATUALIZACAO);
        return participante;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Participante createUpdatedEntity(EntityManager em) {
        Participante participante = new Participante()
            .email(UPDATED_EMAIL)
            .nome(UPDATED_NOME)
            .ddd(UPDATED_DDD)
            .telefone(UPDATED_TELEFONE)
            .notificar(UPDATED_NOTIFICAR)
            .aceito(UPDATED_ACEITO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .dataAtualizacao(UPDATED_DATA_ATUALIZACAO);
        return participante;
    }

    @BeforeEach
    public void initTest() {
        participante = createEntity(em);
    }

    @Test
    @Transactional
    public void createParticipante() throws Exception {
        int databaseSizeBeforeCreate = participanteRepository.findAll().size();

        // Create the Participante
        restParticipanteMockMvc.perform(post("/api/participantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participante)))
            .andExpect(status().isCreated());

        // Validate the Participante in the database
        List<Participante> participanteList = participanteRepository.findAll();
        assertThat(participanteList).hasSize(databaseSizeBeforeCreate + 1);
        Participante testParticipante = participanteList.get(participanteList.size() - 1);
        assertThat(testParticipante.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testParticipante.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testParticipante.getDdd()).isEqualTo(DEFAULT_DDD);
        assertThat(testParticipante.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testParticipante.isNotificar()).isEqualTo(DEFAULT_NOTIFICAR);
        assertThat(testParticipante.isAceito()).isEqualTo(DEFAULT_ACEITO);
        assertThat(testParticipante.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testParticipante.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testParticipante.getDataAtualizacao()).isEqualTo(DEFAULT_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    public void createParticipanteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = participanteRepository.findAll().size();

        // Create the Participante with an existing ID
        participante.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParticipanteMockMvc.perform(post("/api/participantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participante)))
            .andExpect(status().isBadRequest());

        // Validate the Participante in the database
        List<Participante> participanteList = participanteRepository.findAll();
        assertThat(participanteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = participanteRepository.findAll().size();
        // set the field null
        participante.setEmail(null);

        // Create the Participante, which fails.

        restParticipanteMockMvc.perform(post("/api/participantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participante)))
            .andExpect(status().isBadRequest());

        List<Participante> participanteList = participanteRepository.findAll();
        assertThat(participanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNotificarIsRequired() throws Exception {
        int databaseSizeBeforeTest = participanteRepository.findAll().size();
        // set the field null
        participante.setNotificar(null);

        // Create the Participante, which fails.

        restParticipanteMockMvc.perform(post("/api/participantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participante)))
            .andExpect(status().isBadRequest());

        List<Participante> participanteList = participanteRepository.findAll();
        assertThat(participanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAceitoIsRequired() throws Exception {
        int databaseSizeBeforeTest = participanteRepository.findAll().size();
        // set the field null
        participante.setAceito(null);

        // Create the Participante, which fails.

        restParticipanteMockMvc.perform(post("/api/participantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participante)))
            .andExpect(status().isBadRequest());

        List<Participante> participanteList = participanteRepository.findAll();
        assertThat(participanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataAtualizacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = participanteRepository.findAll().size();
        // set the field null
        participante.setDataAtualizacao(null);

        // Create the Participante, which fails.

        restParticipanteMockMvc.perform(post("/api/participantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participante)))
            .andExpect(status().isBadRequest());

        List<Participante> participanteList = participanteRepository.findAll();
        assertThat(participanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParticipantes() throws Exception {
        // Initialize the database
        participanteRepository.saveAndFlush(participante);

        // Get all the participanteList
        restParticipanteMockMvc.perform(get("/api/participantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(participante.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].ddd").value(hasItem(DEFAULT_DDD.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
            .andExpect(jsonPath("$.[*].notificar").value(hasItem(DEFAULT_NOTIFICAR.booleanValue())))
            .andExpect(jsonPath("$.[*].aceito").value(hasItem(DEFAULT_ACEITO.booleanValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())))
            .andExpect(jsonPath("$.[*].dataAtualizacao").value(hasItem(DEFAULT_DATA_ATUALIZACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getParticipante() throws Exception {
        // Initialize the database
        participanteRepository.saveAndFlush(participante);

        // Get the participante
        restParticipanteMockMvc.perform(get("/api/participantes/{id}", participante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(participante.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.ddd").value(DEFAULT_DDD.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()))
            .andExpect(jsonPath("$.notificar").value(DEFAULT_NOTIFICAR.booleanValue()))
            .andExpect(jsonPath("$.aceito").value(DEFAULT_ACEITO.booleanValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.intValue()))
            .andExpect(jsonPath("$.dataAtualizacao").value(DEFAULT_DATA_ATUALIZACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParticipante() throws Exception {
        // Get the participante
        restParticipanteMockMvc.perform(get("/api/participantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParticipante() throws Exception {
        // Initialize the database
        participanteService.save(participante);

        int databaseSizeBeforeUpdate = participanteRepository.findAll().size();

        // Update the participante
        Participante updatedParticipante = participanteRepository.findById(participante.getId()).get();
        // Disconnect from session so that the updates on updatedParticipante are not directly saved in db
        em.detach(updatedParticipante);
        updatedParticipante
            .email(UPDATED_EMAIL)
            .nome(UPDATED_NOME)
            .ddd(UPDATED_DDD)
            .telefone(UPDATED_TELEFONE)
            .notificar(UPDATED_NOTIFICAR)
            .aceito(UPDATED_ACEITO)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .dataAtualizacao(UPDATED_DATA_ATUALIZACAO);

        restParticipanteMockMvc.perform(put("/api/participantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParticipante)))
            .andExpect(status().isOk());

        // Validate the Participante in the database
        List<Participante> participanteList = participanteRepository.findAll();
        assertThat(participanteList).hasSize(databaseSizeBeforeUpdate);
        Participante testParticipante = participanteList.get(participanteList.size() - 1);
        assertThat(testParticipante.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testParticipante.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testParticipante.getDdd()).isEqualTo(UPDATED_DDD);
        assertThat(testParticipante.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testParticipante.isNotificar()).isEqualTo(UPDATED_NOTIFICAR);
        assertThat(testParticipante.isAceito()).isEqualTo(UPDATED_ACEITO);
        assertThat(testParticipante.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testParticipante.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testParticipante.getDataAtualizacao()).isEqualTo(UPDATED_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingParticipante() throws Exception {
        int databaseSizeBeforeUpdate = participanteRepository.findAll().size();

        // Create the Participante

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParticipanteMockMvc.perform(put("/api/participantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(participante)))
            .andExpect(status().isBadRequest());

        // Validate the Participante in the database
        List<Participante> participanteList = participanteRepository.findAll();
        assertThat(participanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParticipante() throws Exception {
        // Initialize the database
        participanteService.save(participante);

        int databaseSizeBeforeDelete = participanteRepository.findAll().size();

        // Delete the participante
        restParticipanteMockMvc.perform(delete("/api/participantes/{id}", participante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Participante> participanteList = participanteRepository.findAll();
        assertThat(participanteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Participante.class);
        Participante participante1 = new Participante();
        participante1.setId(1L);
        Participante participante2 = new Participante();
        participante2.setId(participante1.getId());
        assertThat(participante1).isEqualTo(participante2);
        participante2.setId(2L);
        assertThat(participante1).isNotEqualTo(participante2);
        participante1.setId(null);
        assertThat(participante1).isNotEqualTo(participante2);
    }
}
