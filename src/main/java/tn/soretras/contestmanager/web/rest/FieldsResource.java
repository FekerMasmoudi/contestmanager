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
import tn.soretras.contestmanager.repository.FieldsRepository;
import tn.soretras.contestmanager.service.FieldsService;
import tn.soretras.contestmanager.service.dto.FieldsDTO;
import tn.soretras.contestmanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.soretras.contestmanager.domain.Fields}.
 */
@RestController
@RequestMapping("/api")
public class FieldsResource {

    private final Logger log = LoggerFactory.getLogger(FieldsResource.class);

    private static final String ENTITY_NAME = "fields";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldsService fieldsService;

    private final FieldsRepository fieldsRepository;

    public FieldsResource(FieldsService fieldsService, FieldsRepository fieldsRepository) {
        this.fieldsService = fieldsService;
        this.fieldsRepository = fieldsRepository;
    }

    /**
     * {@code POST  /fields} : Create a new fields.
     *
     * @param fieldsDTO the fieldsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldsDTO, or with status {@code 400 (Bad Request)} if the fields has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fields")
    public ResponseEntity<FieldsDTO> createFields(@Valid @RequestBody FieldsDTO fieldsDTO) throws URISyntaxException {
        log.debug("REST request to save Fields : {}", fieldsDTO);
        if (fieldsDTO.getId() != null) {
            throw new BadRequestAlertException("A new fields cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldsDTO result = fieldsService.save(fieldsDTO);
        return ResponseEntity
            .created(new URI("/api/fields/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /fields/:id} : Updates an existing fields.
     *
     * @param id the id of the fieldsDTO to save.
     * @param fieldsDTO the fieldsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldsDTO,
     * or with status {@code 400 (Bad Request)} if the fieldsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fields/{id}")
    public ResponseEntity<FieldsDTO> updateFields(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody FieldsDTO fieldsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Fields : {}, {}", id, fieldsDTO);
        if (fieldsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FieldsDTO result = fieldsService.update(fieldsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fieldsDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /fields/:id} : Partial updates given fields of an existing fields, field will ignore if it is null
     *
     * @param id the id of the fieldsDTO to save.
     * @param fieldsDTO the fieldsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldsDTO,
     * or with status {@code 400 (Bad Request)} if the fieldsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fieldsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fields/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldsDTO> partialUpdateFields(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody FieldsDTO fieldsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Fields partially : {}, {}", id, fieldsDTO);
        if (fieldsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldsDTO> result = fieldsService.partialUpdate(fieldsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fieldsDTO.getId())
        );
    }

    /**
     * {@code GET  /fields} : get all the fields.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fields in body.
     */
    @GetMapping("/fields")
    public ResponseEntity<List<FieldsDTO>> getAllFields(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Fields");
        Page<FieldsDTO> page = fieldsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fields/:id} : get the "id" fields.
     *
     * @param id the id of the fieldsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fields/{id}")
    public ResponseEntity<FieldsDTO> getFields(@PathVariable String id) {
        log.debug("REST request to get Fields : {}", id);
        Optional<FieldsDTO> fieldsDTO = fieldsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldsDTO);
    }

    /**
     * {@code DELETE  /fields/:id} : delete the "id" fields.
     *
     * @param id the id of the fieldsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fields/{id}")
    public ResponseEntity<Void> deleteFields(@PathVariable String id) {
        log.debug("REST request to delete Fields : {}", id);
        fieldsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
