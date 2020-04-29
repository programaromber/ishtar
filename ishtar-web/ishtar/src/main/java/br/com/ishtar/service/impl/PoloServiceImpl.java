package br.com.ishtar.service.impl;

import br.com.ishtar.service.PoloService;
import br.com.ishtar.domain.Polo;
import br.com.ishtar.repository.PoloRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Polo}.
 */
@Service
@Transactional
public class PoloServiceImpl implements PoloService {

    private final Logger log = LoggerFactory.getLogger(PoloServiceImpl.class);

    private final PoloRepository poloRepository;

    public PoloServiceImpl(PoloRepository poloRepository) {
        this.poloRepository = poloRepository;
    }

    /**
     * Save a polo.
     *
     * @param polo the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Polo save(Polo polo) {
        log.debug("Request to save Polo : {}", polo);
        return poloRepository.save(polo);
    }

    /**
     * Get all the polos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Polo> findAll(Pageable pageable) {
        log.debug("Request to get all Polos");
        return poloRepository.findAll(pageable);
    }


    /**
     * Get one polo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Polo> findOne(Long id) {
        log.debug("Request to get Polo : {}", id);
        return poloRepository.findById(id);
    }

    /**
     * Delete the polo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Polo : {}", id);
        poloRepository.deleteById(id);
    }
}
