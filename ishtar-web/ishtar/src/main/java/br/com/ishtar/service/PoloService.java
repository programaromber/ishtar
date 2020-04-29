package br.com.ishtar.service;

import br.com.ishtar.domain.Polo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Polo}.
 */
public interface PoloService {

    /**
     * Save a polo.
     *
     * @param polo the entity to save.
     * @return the persisted entity.
     */
    Polo save(Polo polo);

    /**
     * Get all the polos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Polo> findAll(Pageable pageable);


    /**
     * Get the "id" polo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Polo> findOne(Long id);

    /**
     * Delete the "id" polo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
