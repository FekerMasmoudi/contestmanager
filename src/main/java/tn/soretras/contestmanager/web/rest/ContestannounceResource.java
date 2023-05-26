package tn.soretras.contestmanager.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import tn.soretras.contestmanager.repository.ContestannounceRepository;
import tn.soretras.contestmanager.service.ContestannounceService;
import tn.soretras.contestmanager.service.dto.ContestannounceDTO;
import tn.soretras.contestmanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.soretras.contestmanager.domain.Contestannounce}.
 */
@RestController
@RequestMapping("/api")
public class ContestannounceResource {

    private final Logger log = LoggerFactory.getLogger(ContestannounceResource.class);

    private static final String ENTITY_NAME = "contestannounce";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContestannounceService contestannounceService;

    private final ContestannounceRepository contestannounceRepository;

    public ContestannounceResource(ContestannounceService contestannounceService, ContestannounceRepository contestannounceRepository) {
        this.contestannounceService = contestannounceService;
        this.contestannounceRepository = contestannounceRepository;
    }

    /**
     * {@code POST  /contestannounces} : Create a new contestannounce.
     *
     * @param contestannounceDTO the contestannounceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contestannounceDTO, or with status {@code 400 (Bad Request)} if the contestannounce has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contestannounces")
    public ResponseEntity<ContestannounceDTO> createContestannounce(@Valid @RequestBody ContestannounceDTO contestannounceDTO)
        throws URISyntaxException {
        log.debug("REST request to save Contestannounce : {}", contestannounceDTO);
        if (contestannounceDTO.getId() != null) {
            throw new BadRequestAlertException("A new contestannounce cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContestannounceDTO result = contestannounceService.save(contestannounceDTO);
        return ResponseEntity
            .created(new URI("/api/contestannounces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /contestannounces/:id} : Updates an existing contestannounce.
     *
     * @param id the id of the contestannounceDTO to save.
     * @param contestannounceDTO the contestannounceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contestannounceDTO,
     * or with status {@code 400 (Bad Request)} if the contestannounceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contestannounceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contestannounces/{id}")
    public ResponseEntity<ContestannounceDTO> updateContestannounce(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ContestannounceDTO contestannounceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Contestannounce : {}, {}", id, contestannounceDTO);
        if (contestannounceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contestannounceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contestannounceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContestannounceDTO result = contestannounceService.update(contestannounceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contestannounceDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /contestannounces/:id} : Partial updates given fields of an existing contestannounce, field will ignore if it is null
     *
     * @param id the id of the contestannounceDTO to save.
     * @param contestannounceDTO the contestannounceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contestannounceDTO,
     * or with status {@code 400 (Bad Request)} if the contestannounceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contestannounceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contestannounceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contestannounces/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContestannounceDTO> partialUpdateContestannounce(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ContestannounceDTO contestannounceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Contestannounce partially : {}, {}", id, contestannounceDTO);
        if (contestannounceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contestannounceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contestannounceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContestannounceDTO> result = contestannounceService.partialUpdate(contestannounceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contestannounceDTO.getId())
        );
    }

    /**
     * {@code GET  /contestannounces} : get all the contestannounces.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contestannounces in body.
     */
    @GetMapping("/contestannounces")
    public ResponseEntity<List<ContestannounceDTO>> getAllContestannounces(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Contestannounces");
        Page<ContestannounceDTO> page;
        if (eagerload) {
            page = contestannounceService.findAllWithEagerRelationships(pageable);
        } else {
            page = contestannounceService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contestannounces/:id} : get the "id" contestannounce.
     *
     * @param id the id of the contestannounceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contestannounceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contestannounces/{id}")
    public ResponseEntity<ContestannounceDTO> getContestannounce(@PathVariable String id) {
        log.debug("REST request to get Contestannounce : {}", id);
        Optional<ContestannounceDTO> contestannounceDTO = contestannounceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contestannounceDTO);
    }

    /**
     * {@code DELETE  /contestannounces/:id} : delete the "id" contestannounce.
     *
     * @param id the id of the contestannounceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contestannounces/{id}")
    public ResponseEntity<Void> deleteContestannounce(@PathVariable String id) {
        log.debug("REST request to delete Contestannounce : {}", id);
        contestannounceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
