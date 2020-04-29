package br.com.ishtar.service;

import br.com.ishtar.domain.Responsavel;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Responsavel}.
 */
public interface ResponsavelService {

    /**
     * Save a responsavel.
     *
     * @param responsavel the entity to save.
     * @return the persisted entity.
     */
    Responsavel save(Responsavel responsavel);

    /**
     * Get all the responsavels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Responsavel> findAll(Pageable pageable);

    /**
     * Get all the responsavels with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Responsavel> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" responsavel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Responsavel> findOne(Long id);

    /**
     * Delete the "id" responsavel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
