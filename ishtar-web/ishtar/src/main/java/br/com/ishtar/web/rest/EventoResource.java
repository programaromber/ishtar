package br.com.ishtar.web.rest;

import br.com.ishtar.domain.Evento;
import br.com.ishtar.service.EventoService;
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
 * REST controller for managing {@link br.com.ishtar.domain.Evento}.
 */
@RestController
@RequestMapping("/api")
public class EventoResource {

    private final Logger log = LoggerFactory.getLogger(EventoResource.class);

    private static final String ENTITY_NAME = "evento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventoService eventoService;

    public EventoResource(EventoService eventoService) {
        this.eventoService = eventoService;
    }

    /**
     * {@code POST  /eventos} : Create a new evento.
     *
     * @param evento the evento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evento, or with status {@code 400 (Bad Request)} if the evento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eventos")
    public ResponseEntity<Evento> createEvento(@Valid @RequestBody Evento evento) throws URISyntaxException {
        log.debug("REST request to save Evento : {}", evento);
        if (evento.getId() != null) {
            throw new BadRequestAlertException("A new evento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Evento result = eventoService.save(evento);
        return ResponseEntity.created(new URI("/api/eventos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eventos} : Updates an existing evento.
     *
     * @param evento the evento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evento,
     * or with status {@code 400 (Bad Request)} if the evento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eventos")
    public ResponseEntity<Evento> updateEvento(@Valid @RequestBody Evento evento) throws URISyntaxException {
        log.debug("REST request to update Evento : {}", evento);
        if (evento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Evento result = eventoService.save(evento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /eventos} : get all the eventos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventos in body.
     */
    @GetMapping("/eventos")
    public ResponseEntity<List<Evento>> getAllEventos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Eventos");
        Page<Evento> page = eventoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eventos/:id} : get the "id" evento.
     *
     * @param id the id of the evento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eventos/{id}")
    public ResponseEntity<Evento> getEvento(@PathVariable Long id) {
        log.debug("REST request to get Evento : {}", id);
        Optional<Evento> evento = eventoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evento);
    }

    /**
     * {@code DELETE  /eventos/:id} : delete the "id" evento.
     *
     * @param id the id of the evento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eventos/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        log.debug("REST request to delete Evento : {}", id);
        eventoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
