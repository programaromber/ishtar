package br.com.ishtar.service.impl;

import br.com.ishtar.service.AgendaService;
import br.com.ishtar.domain.Agenda;
import br.com.ishtar.repository.AgendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Agenda}.
 */
@Service
@Transactional
public class AgendaServiceImpl implements AgendaService {

    private final Logger log = LoggerFactory.getLogger(AgendaServiceImpl.class);

    private final AgendaRepository agendaRepository;

    public AgendaServiceImpl(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    /**
     * Save a agenda.
     *
     * @param agenda the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Agenda save(Agenda agenda) {
        log.debug("Request to save Agenda : {}", agenda);
        return agendaRepository.save(agenda);
    }

    /**
     * Get all the agenda.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Agenda> findAll(Pageable pageable) {
        log.debug("Request to get all Agenda");
        return agendaRepository.findAll(pageable);
    }


    /**
     * Get one agenda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Agenda> findOne(Long id) {
        log.debug("Request to get Agenda : {}", id);
        return agendaRepository.findById(id);
    }

    /**
     * Delete the agenda by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Agenda : {}", id);
        agendaRepository.deleteById(id);
    }
}
