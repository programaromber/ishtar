package br.com.ishtar.web.rest;

import br.com.ishtar.domain.Agenda;
import br.com.ishtar.service.AgendaService;
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
 * REST controller for managing {@link br.com.ishtar.domain.Agenda}.
 */
@RestController
@RequestMapping("/api")
public class AgendaResource {

    private final Logger log = LoggerFactory.getLogger(AgendaResource.class);

    private static final String ENTITY_NAME = "agenda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgendaService agendaService;

    public AgendaResource(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    /**
     * {@code POST  /agenda} : Create a new agenda.
     *
     * @param agenda the agenda to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agenda, or with status {@code 400 (Bad Request)} if the agenda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agenda")
    public ResponseEntity<Agenda> createAgenda(@Valid @RequestBody Agenda agenda) throws URISyntaxException {
        log.debug("REST request to save Agenda : {}", agenda);
        if (agenda.getId() != null) {
            throw new BadRequestAlertException("A new agenda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Agenda result = agendaService.save(agenda);
        return ResponseEntity.created(new URI("/api/agenda/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agenda} : Updates an existing agenda.
     *
     * @param agenda the agenda to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agenda,
     * or with status {@code 400 (Bad Request)} if the agenda is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agenda couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agenda")
    public ResponseEntity<Agenda> updateAgenda(@Valid @RequestBody Agenda agenda) throws URISyntaxException {
        log.debug("REST request to update Agenda : {}", agenda);
        if (agenda.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Agenda result = agendaService.save(agenda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agenda.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agenda} : get all the agenda.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agenda in body.
     */
    @GetMapping("/agenda")
    public ResponseEntity<List<Agenda>> getAllAgenda(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Agenda");
        Page<Agenda> page = agendaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /agenda/:id} : get the "id" agenda.
     *
     * @param id the id of the agenda to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agenda, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agenda/{id}")
    public ResponseEntity<Agenda> getAgenda(@PathVariable Long id) {
        log.debug("REST request to get Agenda : {}", id);
        Optional<Agenda> agenda = agendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agenda);
    }

    /**
     * {@code DELETE  /agenda/:id} : delete the "id" agenda.
     *
     * @param id the id of the agenda to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agenda/{id}")
    public ResponseEntity<Void> deleteAgenda(@PathVariable Long id) {
        log.debug("REST request to delete Agenda : {}", id);
        agendaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
