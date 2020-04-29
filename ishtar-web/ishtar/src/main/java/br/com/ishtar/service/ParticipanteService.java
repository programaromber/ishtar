package br.com.ishtar.service;

import br.com.ishtar.domain.Participante;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Participante}.
 */
public interface ParticipanteService {

    /**
     * Save a participante.
     *
     * @param participante the entity to save.
     * @return the persisted entity.
     */
    Participante save(Participante participante);

    /**
     * Get all the participantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Participante> findAll(Pageable pageable);


    /**
     * Get the "id" participante.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Participante> findOne(Long id);

    /**
     * Delete the "id" participante.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
