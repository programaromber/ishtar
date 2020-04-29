package br.com.ishtar.service.impl;

import br.com.ishtar.service.ParticipacaoService;
import br.com.ishtar.domain.Participacao;
import br.com.ishtar.repository.ParticipacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Participacao}.
 */
@Service
@Transactional
public class ParticipacaoServiceImpl implements ParticipacaoService {

    private final Logger log = LoggerFactory.getLogger(ParticipacaoServiceImpl.class);

    private final ParticipacaoRepository participacaoRepository;

    public ParticipacaoServiceImpl(ParticipacaoRepository participacaoRepository) {
        this.participacaoRepository = participacaoRepository;
    }

    /**
     * Save a participacao.
     *
     * @param participacao the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Participacao save(Participacao participacao) {
        log.debug("Request to save Participacao : {}", participacao);
        return participacaoRepository.save(participacao);
    }

    /**
     * Get all the participacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Participacao> findAll(Pageable pageable) {
        log.debug("Request to get all Participacaos");
        return participacaoRepository.findAll(pageable);
    }


    /**
     * Get one participacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Participacao> findOne(Long id) {
        log.debug("Request to get Participacao : {}", id);
        return participacaoRepository.findById(id);
    }

    /**
     * Delete the participacao by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Participacao : {}", id);
        participacaoRepository.deleteById(id);
    }
}
