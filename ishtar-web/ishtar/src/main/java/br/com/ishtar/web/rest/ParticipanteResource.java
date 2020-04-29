package br.com.ishtar.web.rest;

import br.com.ishtar.domain.Participante;
import br.com.ishtar.service.ParticipanteService;
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
 * REST controller for managing {@link br.com.ishtar.domain.Participante}.
 */
@RestController
@RequestMapping("/api")
public class ParticipanteResource {

    private final Logger log = LoggerFactory.getLogger(ParticipanteResource.class);

    private static final String ENTITY_NAME = "participante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParticipanteService participanteService;

    public ParticipanteResource(ParticipanteService participanteService) {
        this.participanteService = participanteService;
    }

    /**
     * {@code POST  /participantes} : Create a new participante.
     *
     * @param participante the participante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new participante, or with status {@code 400 (Bad Request)} if the participante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/participantes")
    public ResponseEntity<Participante> createParticipante(@Valid @RequestBody Participante participante) throws URISyntaxException {
        log.debug("REST request to save Participante : {}", participante);
        if (participante.getId() != null) {
            throw new BadRequestAlertException("A new participante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Participante result = participanteService.save(participante);
        return ResponseEntity.created(new URI("/api/participantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /participantes} : Updates an existing participante.
     *
     * @param participante the participante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated participante,
     * or with status {@code 400 (Bad Request)} if the participante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the participante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/participantes")
    public ResponseEntity<Participante> updateParticipante(@Valid @RequestBody Participante participante) throws URISyntaxException {
        log.debug("REST request to update Participante : {}", participante);
        if (participante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Participante result = participanteService.save(participante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, participante.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /participantes} : get all the participantes.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of participantes in body.
     */
    @GetMapping("/participantes")
    public ResponseEntity<List<Participante>> getAllParticipantes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Participantes");
        Page<Participante> page = participanteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /participantes/:id} : get the "id" participante.
     *
     * @param id the id of the participante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the participante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/participantes/{id}")
    public ResponseEntity<Participante> getParticipante(@PathVariable Long id) {
        log.debug("REST request to get Participante : {}", id);
        Optional<Participante> participante = participanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(participante);
    }

    /**
     * {@code DELETE  /participantes/:id} : delete the "id" participante.
     *
     * @param id the id of the participante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/participantes/{id}")
    public ResponseEntity<Void> deleteParticipante(@PathVariable Long id) {
        log.debug("REST request to delete Participante : {}", id);
        participanteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
