package br.com.ishtar.service;

import br.com.ishtar.domain.Cidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Cidade}.
 */
public interface CidadeService {

    /**
     * Save a cidade.
     *
     * @param cidade the entity to save.
     * @return the persisted entity.
     */
    Cidade save(Cidade cidade);

    /**
     * Get all the cidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cidade> findAll(Pageable pageable);


    /**
     * Get the "id" cidade.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cidade> findOne(Long id);

    /**
     * Delete the "id" cidade.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
