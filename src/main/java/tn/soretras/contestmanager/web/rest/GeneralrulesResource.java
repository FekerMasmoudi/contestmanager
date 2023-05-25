package tn.soretras.contestmanager.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import tn.soretras.contestmanager.repository.GeneralrulesRepository;
import tn.soretras.contestmanager.service.GeneralrulesService;
import tn.soretras.contestmanager.service.dto.GeneralrulesDTO;
import tn.soretras.contestmanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.soretras.contestmanager.domain.Generalrules}.
 */
@RestController
@RequestMapping("/api")
public class GeneralrulesResource {

    private final Logger log = LoggerFactory.getLogger(GeneralrulesResource.class);

    private static final String ENTITY_NAME = "generalrules";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GeneralrulesService generalrulesService;

    private final GeneralrulesRepository generalrulesRepository;

    public GeneralrulesResource(GeneralrulesService generalrulesService, GeneralrulesRepository generalrulesRepository) {
        this.generalrulesService = generalrulesService;
        this.generalrulesRepository = generalrulesRepository;
    }

    /**
     * {@code POST  /generalrules} : Create a new generalrules.
     *
     * @param generalrulesDTO the generalrulesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new generalrulesDTO, or with status {@code 400 (Bad Request)} if the generalrules has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/generalrules")
    public ResponseEntity<GeneralrulesDTO> createGeneralrules(@RequestBody GeneralrulesDTO generalrulesDTO) throws URISyntaxException {
        log.debug("REST request to save Generalrules : {}", generalrulesDTO);
        if (generalrulesDTO.getId() != null) {
            throw new BadRequestAlertException("A new generalrules cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeneralrulesDTO result = generalrulesService.save(generalrulesDTO);
        return ResponseEntity
            .created(new URI("/api/generalrules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /generalrules/:id} : Updates an existing generalrules.
     *
     * @param id the id of the generalrulesDTO to save.
     * @param generalrulesDTO the generalrulesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated generalrulesDTO,
     * or with status {@code 400 (Bad Request)} if the generalrulesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the generalrulesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/generalrules/{id}")
    public ResponseEntity<GeneralrulesDTO> updateGeneralrules(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody GeneralrulesDTO generalrulesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Generalrules : {}, {}", id, generalrulesDTO);
        if (generalrulesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, generalrulesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!generalrulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GeneralrulesDTO result = generalrulesService.update(generalrulesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, generalrulesDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /generalrules/:id} : Partial updates given fields of an existing generalrules, field will ignore if it is null
     *
     * @param id the id of the generalrulesDTO to save.
     * @param generalrulesDTO the generalrulesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated generalrulesDTO,
     * or with status {@code 400 (Bad Request)} if the generalrulesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the generalrulesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the generalrulesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/generalrules/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GeneralrulesDTO> partialUpdateGeneralrules(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody GeneralrulesDTO generalrulesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Generalrules partially : {}, {}", id, generalrulesDTO);
        if (generalrulesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, generalrulesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!generalrulesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GeneralrulesDTO> result = generalrulesService.partialUpdate(generalrulesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, generalrulesDTO.getId())
        );
    }

    /**
     * {@code GET  /generalrules} : get all the generalrules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of generalrules in body.
     */
    @GetMapping("/generalrules")
    public ResponseEntity<List<GeneralrulesDTO>> getAllGeneralrules(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Generalrules");
        Page<GeneralrulesDTO> page = generalrulesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /generalrules/:id} : get the "id" generalrules.
     *
     * @param id the id of the generalrulesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the generalrulesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/generalrules/{id}")
    public ResponseEntity<GeneralrulesDTO> getGeneralrules(@PathVariable String id) {
        log.debug("REST request to get Generalrules : {}", id);
        Optional<GeneralrulesDTO> generalrulesDTO = generalrulesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(generalrulesDTO);
    }

    /**
     * {@code DELETE  /generalrules/:id} : delete the "id" generalrules.
     *
     * @param id the id of the generalrulesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/generalrules/{id}")
    public ResponseEntity<Void> deleteGeneralrules(@PathVariable String id) {
        log.debug("REST request to delete Generalrules : {}", id);
        generalrulesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
