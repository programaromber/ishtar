package br.com.ishtar.web.rest;

import br.com.ishtar.IshtarApp;
import br.com.ishtar.domain.Agenda;
import br.com.ishtar.repository.AgendaRepository;
import br.com.ishtar.service.AgendaService;
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
 * Integration tests for the {@Link AgendaResource} REST controller.
 */
@SpringBootTest(classes = IshtarApp.class)
public class AgendaResourceIT {

    private static final Integer DEFAULT_ANO = 4;
    private static final Integer UPDATED_ANO = 5;

    @Autowired
    private AgendaRepository agendaRepository;

    @Autowired
    private AgendaService agendaService;

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

    private MockMvc restAgendaMockMvc;

    private Agenda agenda;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgendaResource agendaResource = new AgendaResource(agendaService);
        this.restAgendaMockMvc = MockMvcBuilders.standaloneSetup(agendaResource)
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
    public static Agenda createEntity(EntityManager em) {
        Agenda agenda = new Agenda()
            .ano(DEFAULT_ANO);
        return agenda;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agenda createUpdatedEntity(EntityManager em) {
        Agenda agenda = new Agenda()
            .ano(UPDATED_ANO);
        return agenda;
    }

    @BeforeEach
    public void initTest() {
        agenda = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgenda() throws Exception {
        int databaseSizeBeforeCreate = agendaRepository.findAll().size();

        // Create the Agenda
        restAgendaMockMvc.perform(post("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenda)))
            .andExpect(status().isCreated());

        // Validate the Agenda in the database
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeCreate + 1);
        Agenda testAgenda = agendaList.get(agendaList.size() - 1);
        assertThat(testAgenda.getAno()).isEqualTo(DEFAULT_ANO);
    }

    @Test
    @Transactional
    public void createAgendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agendaRepository.findAll().size();

        // Create the Agenda with an existing ID
        agenda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgendaMockMvc.perform(post("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenda)))
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = agendaRepository.findAll().size();
        // set the field null
        agenda.setAno(null);

        // Create the Agenda, which fails.

        restAgendaMockMvc.perform(post("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenda)))
            .andExpect(status().isBadRequest());

        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgenda() throws Exception {
        // Initialize the database
        agendaRepository.saveAndFlush(agenda);

        // Get all the agendaList
        restAgendaMockMvc.perform(get("/api/agenda?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agenda.getId().intValue())))
            .andExpect(jsonPath("$.[*].ano").value(hasItem(DEFAULT_ANO)));
    }
    
    @Test
    @Transactional
    public void getAgenda() throws Exception {
        // Initialize the database
        agendaRepository.saveAndFlush(agenda);

        // Get the agenda
        restAgendaMockMvc.perform(get("/api/agenda/{id}", agenda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agenda.getId().intValue()))
            .andExpect(jsonPath("$.ano").value(DEFAULT_ANO));
    }

    @Test
    @Transactional
    public void getNonExistingAgenda() throws Exception {
        // Get the agenda
        restAgendaMockMvc.perform(get("/api/agenda/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgenda() throws Exception {
        // Initialize the database
        agendaService.save(agenda);

        int databaseSizeBeforeUpdate = agendaRepository.findAll().size();

        // Update the agenda
        Agenda updatedAgenda = agendaRepository.findById(agenda.getId()).get();
        // Disconnect from session so that the updates on updatedAgenda are not directly saved in db
        em.detach(updatedAgenda);
        updatedAgenda
            .ano(UPDATED_ANO);

        restAgendaMockMvc.perform(put("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAgenda)))
            .andExpect(status().isOk());

        // Validate the Agenda in the database
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeUpdate);
        Agenda testAgenda = agendaList.get(agendaList.size() - 1);
        assertThat(testAgenda.getAno()).isEqualTo(UPDATED_ANO);
    }

    @Test
    @Transactional
    public void updateNonExistingAgenda() throws Exception {
        int databaseSizeBeforeUpdate = agendaRepository.findAll().size();

        // Create the Agenda

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgendaMockMvc.perform(put("/api/agenda")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenda)))
            .andExpect(status().isBadRequest());

        // Validate the Agenda in the database
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgenda() throws Exception {
        // Initialize the database
        agendaService.save(agenda);

        int databaseSizeBeforeDelete = agendaRepository.findAll().size();

        // Delete the agenda
        restAgendaMockMvc.perform(delete("/api/agenda/{id}", agenda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agenda> agendaList = agendaRepository.findAll();
        assertThat(agendaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agenda.class);
        Agenda agenda1 = new Agenda();
        agenda1.setId(1L);
        Agenda agenda2 = new Agenda();
        agenda2.setId(agenda1.getId());
        assertThat(agenda1).isEqualTo(agenda2);
        agenda2.setId(2L);
        assertThat(agenda1).isNotEqualTo(agenda2);
        agenda1.setId(null);
        assertThat(agenda1).isNotEqualTo(agenda2);
    }
}
