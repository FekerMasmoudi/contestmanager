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
import tn.soretras.contestmanager.repository.ContestfieldRepository;
import tn.soretras.contestmanager.service.ContestfieldService;
import tn.soretras.contestmanager.service.dto.ContestfieldDTO;
import tn.soretras.contestmanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.soretras.contestmanager.domain.Contestfield}.
 */
@RestController
@RequestMapping("/api")
public class ContestfieldResource {

    private final Logger log = LoggerFactory.getLogger(ContestfieldResource.class);

    private static final String ENTITY_NAME = "contestfield";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContestfieldService contestfieldService;

    private final ContestfieldRepository contestfieldRepository;

    public ContestfieldResource(ContestfieldService contestfieldService, ContestfieldRepository contestfieldRepository) {
        this.contestfieldService = contestfieldService;
        this.contestfieldRepository = contestfieldRepository;
    }

    /**
     * {@code POST  /contestfields} : Create a new contestfield.
     *
     * @param contestfieldDTO the contestfieldDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contestfieldDTO, or with status {@code 400 (Bad Request)} if the contestfield has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contestfields")
    public ResponseEntity<ContestfieldDTO> createContestfield(@Valid @RequestBody ContestfieldDTO contestfieldDTO)
        throws URISyntaxException {
        log.debug("REST request to save Contestfield : {}", contestfieldDTO);
        if (contestfieldDTO.getId() != null) {
            throw new BadRequestAlertException("A new contestfield cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContestfieldDTO result = contestfieldService.save(contestfieldDTO);
        return ResponseEntity
            .created(new URI("/api/contestfields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /contestfields/:id} : Updates an existing contestfield.
     *
     * @param id the id of the contestfieldDTO to save.
     * @param contestfieldDTO the contestfieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contestfieldDTO,
     * or with status {@code 400 (Bad Request)} if the contestfieldDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contestfieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contestfields/{id}")
    public ResponseEntity<ContestfieldDTO> updateContestfield(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ContestfieldDTO contestfieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Contestfield : {}, {}", id, contestfieldDTO);
        if (contestfieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contestfieldDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contestfieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContestfieldDTO result = contestfieldService.update(contestfieldDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contestfieldDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /contestfields/:id} : Partial updates given fields of an existing contestfield, field will ignore if it is null
     *
     * @param id the id of the contestfieldDTO to save.
     * @param contestfieldDTO the contestfieldDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contestfieldDTO,
     * or with status {@code 400 (Bad Request)} if the contestfieldDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contestfieldDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contestfieldDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contestfields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContestfieldDTO> partialUpdateContestfield(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ContestfieldDTO contestfieldDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Contestfield partially : {}, {}", id, contestfieldDTO);
        if (contestfieldDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contestfieldDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contestfieldRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContestfieldDTO> result = contestfieldService.partialUpdate(contestfieldDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, contestfieldDTO.getId())
        );
    }

    /**
     * {@code GET  /contestfields} : get all the contestfields.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contestfields in body.
     */
    @GetMapping("/contestfields")
    public ResponseEntity<List<ContestfieldDTO>> getAllContestfields(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Contestfields");
        Page<ContestfieldDTO> page = contestfieldService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contestfields/:id} : get the "id" contestfield.
     *
     * @param id the id of the contestfieldDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contestfieldDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contestfields/{id}")
    public ResponseEntity<ContestfieldDTO> getContestfield(@PathVariable String id) {
        log.debug("REST request to get Contestfield : {}", id);
        Optional<ContestfieldDTO> contestfieldDTO = contestfieldService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contestfieldDTO);
    }

    /**
     * {@code DELETE  /contestfields/:id} : delete the "id" contestfield.
     *
     * @param id the id of the contestfieldDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contestfields/{id}")
    public ResponseEntity<Void> deleteContestfield(@PathVariable String id) {
        log.debug("REST request to delete Contestfield : {}", id);
        contestfieldService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
