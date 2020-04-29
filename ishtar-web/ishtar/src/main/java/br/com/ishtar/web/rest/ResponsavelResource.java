package br.com.ishtar.web.rest;

import br.com.ishtar.domain.Responsavel;
import br.com.ishtar.service.ResponsavelService;
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
 * REST controller for managing {@link br.com.ishtar.domain.Responsavel}.
 */
@RestController
@RequestMapping("/api")
public class ResponsavelResource {

    private final Logger log = LoggerFactory.getLogger(ResponsavelResource.class);

    private static final String ENTITY_NAME = "responsavel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResponsavelService responsavelService;

    public ResponsavelResource(ResponsavelService responsavelService) {
        this.responsavelService = responsavelService;
    }

    /**
     * {@code POST  /responsavels} : Create a new responsavel.
     *
     * @param responsavel the responsavel to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new responsavel, or with status {@code 400 (Bad Request)} if the responsavel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/responsavels")
    public ResponseEntity<Responsavel> createResponsavel(@Valid @RequestBody Responsavel responsavel) throws URISyntaxException {
        log.debug("REST request to save Responsavel : {}", responsavel);
        if (responsavel.getId() != null) {
            throw new BadRequestAlertException("A new responsavel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Responsavel result = responsavelService.save(responsavel);
        return ResponseEntity.created(new URI("/api/responsavels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /responsavels} : Updates an existing responsavel.
     *
     * @param responsavel the responsavel to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated responsavel,
     * or with status {@code 400 (Bad Request)} if the responsavel is not valid,
     * or with status {@code 500 (Internal Server Error)} if the responsavel couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/responsavels")
    public ResponseEntity<Responsavel> updateResponsavel(@Valid @RequestBody Responsavel responsavel) throws URISyntaxException {
        log.debug("REST request to update Responsavel : {}", responsavel);
        if (responsavel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Responsavel result = responsavelService.save(responsavel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, responsavel.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /responsavels} : get all the responsavels.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of responsavels in body.
     */
    @GetMapping("/responsavels")
    public ResponseEntity<List<Responsavel>> getAllResponsavels(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Responsavels");
        Page<Responsavel> page;
        if (eagerload) {
            page = responsavelService.findAllWithEagerRelationships(pageable);
        } else {
            page = responsavelService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /responsavels/:id} : get the "id" responsavel.
     *
     * @param id the id of the responsavel to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the responsavel, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/responsavels/{id}")
    public ResponseEntity<Responsavel> getResponsavel(@PathVariable Long id) {
        log.debug("REST request to get Responsavel : {}", id);
        Optional<Responsavel> responsavel = responsavelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsavel);
    }

    /**
     * {@code DELETE  /responsavels/:id} : delete the "id" responsavel.
     *
     * @param id the id of the responsavel to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/responsavels/{id}")
    public ResponseEntity<Void> deleteResponsavel(@PathVariable Long id) {
        log.debug("REST request to delete Responsavel : {}", id);
        responsavelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
