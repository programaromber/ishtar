package br.com.ishtar.web.rest;

import br.com.ishtar.domain.Polo;
import br.com.ishtar.service.PoloService;
import br.com.ishtar.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.ishtar.domain.Polo}.
 */
@RestController
@RequestMapping("/api")
public class PoloResource {

    private final Logger log = LoggerFactory.getLogger(PoloResource.class);

    private static final String ENTITY_NAME = "polo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PoloService poloService;

    public PoloResource(PoloService poloService) {
        this.poloService = poloService;
    }

    /**
     * {@code POST  /polos} : Create a new polo.
     *
     * @param polo the polo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new polo, or with status {@code 400 (Bad Request)} if the polo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/polos")
    public ResponseEntity<Polo> createPolo(@Valid @RequestBody Polo polo) throws URISyntaxException {
        log.debug("REST request to save Polo : {}", polo);
        if (polo.getId() != null) {
            throw new BadRequestAlertException("A new polo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Polo result = poloService.save(polo);
        return ResponseEntity.created(new URI("/api/polos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /polos} : Updates an existing polo.
     *
     * @param polo the polo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated polo,
     * or with status {@code 400 (Bad Request)} if the polo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the polo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/polos")
    public ResponseEntity<Polo> updatePolo(@Valid @RequestBody Polo polo) throws URISyntaxException {
        log.debug("REST request to update Polo : {}", polo);
        if (polo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Polo result = poloService.save(polo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, polo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /polos} : get all the polos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of polos in body.
     */
    @GetMapping("/polos")
    public ResponseEntity<List<Polo>> getAllPolos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Polos");
        Page<Polo> page = poloService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /polos/:id} : get the "id" polo.
     *
     * @param id the id of the polo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the polo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/polos/{id}")
    public ResponseEntity<Polo> getPolo(@PathVariable Long id) {
        log.debug("REST request to get Polo : {}", id);
        Optional<Polo> polo = poloService.findOne(id);
        return ResponseUtil.wrapOrNotFound(polo);
    }

    /**
     * {@code DELETE  /polos/:id} : delete the "id" polo.
     *
     * @param id the id of the polo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/polos/{id}")
    public ResponseEntity<Void> deletePolo(@PathVariable Long id) {
        log.debug("REST request to delete Polo : {}", id);
        poloService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
