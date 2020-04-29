package br.com.ishtar.web.rest;

import br.com.ishtar.domain.Participacao;
import br.com.ishtar.service.ParticipacaoService;
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
 * REST controller for managing {@link br.com.ishtar.domain.Participacao}.
 */
@RestController
@RequestMapping("/api")
public class ParticipacaoResource {

    private final Logger log = LoggerFactory.getLogger(ParticipacaoResource.class);

    private static final String ENTITY_NAME = "participacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParticipacaoService participacaoService;

    public ParticipacaoResource(ParticipacaoService participacaoService) {
        this.participacaoService = participacaoService;
    }

    /**
     * {@code POST  /participacaos} : Create a new participacao.
     *
     * @param participacao the participacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new participacao, or with status {@code 400 (Bad Request)} if the participacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/participacaos")
    public ResponseEntity<Participacao> createParticipacao(@Valid @RequestBody Participacao participacao) throws URISyntaxException {
        log.debug("REST request to save Participacao : {}", participacao);
        if (participacao.getId() != null) {
            throw new BadRequestAlertException("A new participacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Participacao result = participacaoService.save(participacao);
        return ResponseEntity.created(new URI("/api/participacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /participacaos} : Updates an existing participacao.
     *
     * @param participacao the participacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participacao,
     * or with status {@code 400 (Bad Request)} if the participacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the participacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/participacaos")
    public ResponseEntity<Participacao> updateParticipacao(@Valid @RequestBody Participacao participacao) throws URISyntaxException {
        log.debug("REST request to update Participacao : {}", participacao);
        if (participacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Participacao result = participacaoService.save(participacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, participacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /participacaos} : get all the participacaos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of participacaos in body.
     */
    @GetMapping("/participacaos")
    public ResponseEntity<List<Participacao>> getAllParticipacaos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Participacaos");
        Page<Participacao> page = participacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /participacaos/:id} : get the "id" participacao.
     *
     * @param id the id of the participacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the participacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/participacaos/{id}")
    public ResponseEntity<Participacao> getParticipacao(@PathVariable Long id) {
        log.debug("REST request to get Participacao : {}", id);
        Optional<Participacao> participacao = participacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(participacao);
    }

    /**
     * {@code DELETE  /participacaos/:id} : delete the "id" participacao.
     *
     * @param id the id of the participacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/participacaos/{id}")
    public ResponseEntity<Void> deleteParticipacao(@PathVariable Long id) {
        log.debug("REST request to delete Participacao : {}", id);
        participacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
