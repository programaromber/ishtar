package br.com.ishtar.web.rest;

import br.com.ishtar.domain.Contato;
import br.com.ishtar.service.ContatoService;
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
 * REST controller for managing {@link br.com.ishtar.domain.Contato}.
 */
@RestController
@RequestMapping("/api")
public class ContatoResource {

    private final Logger log = LoggerFactory.getLogger(ContatoResource.class);

    private static final String ENTITY_NAME = "contato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContatoService contatoService;

    public ContatoResource(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    /**
     * {@code POST  /contatoes} : Create a new contato.
     *
     * @param contato the contato to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contato, or with status {@code 400 (Bad Request)} if the contato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contatoes")
    public ResponseEntity<Contato> createContato(@Valid @RequestBody Contato contato) throws URISyntaxException {
        log.debug("REST request to save Contato : {}", contato);
        if (contato.getId() != null) {
            throw new BadRequestAlertException("A new contato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contato result = contatoService.save(contato);
        return ResponseEntity.created(new URI("/api/contatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contatoes} : Updates an existing contato.
     *
     * @param contato the contato to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contato,
     * or with status {@code 400 (Bad Request)} if the contato is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contato couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contatoes")
    public ResponseEntity<Contato> updateContato(@Valid @RequestBody Contato contato) throws URISyntaxException {
        log.debug("REST request to update Contato : {}", contato);
        if (contato.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contato result = contatoService.save(contato);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contato.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contatoes} : get all the contatoes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contatoes in body.
     */
    @GetMapping("/contatoes")
    public ResponseEntity<List<Contato>> getAllContatoes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Contatoes");
        Page<Contato> page = contatoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contatoes/:id} : get the "id" contato.
     *
     * @param id the id of the contato to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contato, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contatoes/{id}")
    public ResponseEntity<Contato> getContato(@PathVariable Long id) {
        log.debug("REST request to get Contato : {}", id);
        Optional<Contato> contato = contatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contato);
    }

    /**
     * {@code DELETE  /contatoes/:id} : delete the "id" contato.
     *
     * @param id the id of the contato to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contatoes/{id}")
    public ResponseEntity<Void> deleteContato(@PathVariable Long id) {
        log.debug("REST request to delete Contato : {}", id);
        contatoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
