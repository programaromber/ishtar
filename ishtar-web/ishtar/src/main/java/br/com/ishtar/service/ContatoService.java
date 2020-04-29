package br.com.ishtar.service;

import br.com.ishtar.domain.Contato;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Contato}.
 */
public interface ContatoService {

    /**
     * Save a contato.
     *
     * @param contato the entity to save.
     * @return the persisted entity.
     */
    Contato save(Contato contato);

    /**
     * Get all the contatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Contato> findAll(Pageable pageable);


    /**
     * Get the "id" contato.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Contato> findOne(Long id);

    /**
     * Delete the "id" contato.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
