package br.com.ishtar.service.impl;

import br.com.ishtar.service.ParticipanteService;
import br.com.ishtar.domain.Participante;
import br.com.ishtar.repository.ParticipanteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Participante}.
 */
@Service
@Transactional
public class ParticipanteServiceImpl implements ParticipanteService {

    private final Logger log = LoggerFactory.getLogger(ParticipanteServiceImpl.class);

    private final ParticipanteRepository participanteRepository;

    public ParticipanteServiceImpl(ParticipanteRepository participanteRepository) {
        this.participanteRepository = participanteRepository;
    }

    /**
     * Save a participante.
     *
     * @param participante the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Participante save(Participante participante) {
        log.debug("Request to save Participante : {}", participante);
        return participanteRepository.save(participante);
    }

    /**
     * Get all the participantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Participante> findAll(Pageable pageable) {
        log.debug("Request to get all Participantes");
        return participanteRepository.findAll(pageable);
    }


    /**
     * Get one participante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Participante> findOne(Long id) {
        log.debug("Request to get Participante : {}", id);
        return participanteRepository.findById(id);
    }

    /**
     * Delete the participante by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Participante : {}", id);
        participanteRepository.deleteById(id);
    }
}
