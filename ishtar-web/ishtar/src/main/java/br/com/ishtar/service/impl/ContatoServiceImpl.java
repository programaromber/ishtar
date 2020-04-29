package br.com.ishtar.service.impl;

import br.com.ishtar.service.ContatoService;
import br.com.ishtar.domain.Contato;
import br.com.ishtar.repository.ContatoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Contato}.
 */
@Service
@Transactional
public class ContatoServiceImpl implements ContatoService {

    private final Logger log = LoggerFactory.getLogger(ContatoServiceImpl.class);

    private final ContatoRepository contatoRepository;

    public ContatoServiceImpl(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    /**
     * Save a contato.
     *
     * @param contato the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Contato save(Contato contato) {
        log.debug("Request to save Contato : {}", contato);
        return contatoRepository.save(contato);
    }

    /**
     * Get all the contatoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Contato> findAll(Pageable pageable) {
        log.debug("Request to get all Contatoes");
        return contatoRepository.findAll(pageable);
    }


    /**
     * Get one contato by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Contato> findOne(Long id) {
        log.debug("Request to get Contato : {}", id);
        return contatoRepository.findById(id);
    }

    /**
     * Delete the contato by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contato : {}", id);
        contatoRepository.deleteById(id);
    }
}
