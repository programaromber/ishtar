package br.com.ishtar.service.impl;

import br.com.ishtar.service.ResponsavelService;
import br.com.ishtar.domain.Responsavel;
import br.com.ishtar.repository.ResponsavelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Responsavel}.
 */
@Service
@Transactional
public class ResponsavelServiceImpl implements ResponsavelService {

    private final Logger log = LoggerFactory.getLogger(ResponsavelServiceImpl.class);

    private final ResponsavelRepository responsavelRepository;

    public ResponsavelServiceImpl(ResponsavelRepository responsavelRepository) {
        this.responsavelRepository = responsavelRepository;
    }

    /**
     * Save a responsavel.
     *
     * @param responsavel the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Responsavel save(Responsavel responsavel) {
        log.debug("Request to save Responsavel : {}", responsavel);
        return responsavelRepository.save(responsavel);
    }

    /**
     * Get all the responsavels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Responsavel> findAll(Pageable pageable) {
        log.debug("Request to get all Responsavels");
        return responsavelRepository.findAll(pageable);
    }

    /**
     * Get all the responsavels with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Responsavel> findAllWithEagerRelationships(Pageable pageable) {
        return responsavelRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one responsavel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Responsavel> findOne(Long id) {
        log.debug("Request to get Responsavel : {}", id);
        return responsavelRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the responsavel by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Responsavel : {}", id);
        responsavelRepository.deleteById(id);
    }
}
