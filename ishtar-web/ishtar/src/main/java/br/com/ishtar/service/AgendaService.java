package br.com.ishtar.service;

import br.com.ishtar.domain.Agenda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Agenda}.
 */
public interface AgendaService {

    /**
     * Save a agenda.
     *
     * @param agenda the entity to save.
     * @return the persisted entity.
     */
    Agenda save(Agenda agenda);

    /**
     * Get all the agenda.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Agenda> findAll(Pageable pageable);


    /**
     * Get the "id" agenda.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Agenda> findOne(Long id);

    /**
     * Delete the "id" agenda.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
