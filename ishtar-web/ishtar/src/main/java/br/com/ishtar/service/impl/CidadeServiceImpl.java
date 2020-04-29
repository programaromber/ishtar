package br.com.ishtar.service.impl;

import br.com.ishtar.service.CidadeService;
import br.com.ishtar.domain.Cidade;
import br.com.ishtar.repository.CidadeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cidade}.
 */
@Service
@Transactional
public class CidadeServiceImpl implements CidadeService {

    private final Logger log = LoggerFactory.getLogger(CidadeServiceImpl.class);

    private final CidadeRepository cidadeRepository;

    public CidadeServiceImpl(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    /**
     * Save a cidade.
     *
     * @param cidade the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Cidade save(Cidade cidade) {
        log.debug("Request to save Cidade : {}", cidade);
        return cidadeRepository.save(cidade);
    }

    /**
     * Get all the cidades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Cidade> findAll(Pageable pageable) {
        log.debug("Request to get all Cidades");
        return cidadeRepository.findAll(pageable);
    }


    /**
     * Get one cidade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Cidade> findOne(Long id) {
        log.debug("Request to get Cidade : {}", id);
        return cidadeRepository.findById(id);
    }

    /**
     * Delete the cidade by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cidade : {}", id);
        cidadeRepository.deleteById(id);
    }
}
