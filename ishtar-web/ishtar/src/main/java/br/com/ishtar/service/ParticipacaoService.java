package br.com.ishtar.service;

import br.com.ishtar.domain.Participacao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Participacao}.
 */
public interface ParticipacaoService {

    /**
     * Save a participacao.
     *
     * @param participacao the entity to save.
     * @return the persisted entity.
     */
    Participacao save(Participacao participacao);

    /**
     * Get all the participacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Participacao> findAll(Pageable pageable);


    /**
     * Get the "id" participacao.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Participacao> findOne(Long id);

    /**
     * Delete the "id" participacao.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
