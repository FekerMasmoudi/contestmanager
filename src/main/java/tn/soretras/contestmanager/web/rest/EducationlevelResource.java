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
import tn.soretras.contestmanager.repository.EducationlevelRepository;
import tn.soretras.contestmanager.service.EducationlevelService;
import tn.soretras.contestmanager.service.dto.EducationlevelDTO;
import tn.soretras.contestmanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.soretras.contestmanager.domain.Educationlevel}.
 */
@RestController
@RequestMapping("/api")
public class EducationlevelResource {

    private final Logger log = LoggerFactory.getLogger(EducationlevelResource.class);

    private static final String ENTITY_NAME = "educationlevel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EducationlevelService educationlevelService;

    private final EducationlevelRepository educationlevelRepository;

    public EducationlevelResource(EducationlevelService educationlevelService, EducationlevelRepository educationlevelRepository) {
        this.educationlevelService = educationlevelService;
        this.educationlevelRepository = educationlevelRepository;
    }

    /**
     * {@code POST  /educationlevels} : Create a new educationlevel.
     *
     * @param educationlevelDTO the educationlevelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new educationlevelDTO, or with status {@code 400 (Bad Request)} if the educationlevel has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/educationlevels")
    public ResponseEntity<EducationlevelDTO> createEducationlevel(@Valid @RequestBody EducationlevelDTO educationlevelDTO)
        throws URISyntaxException {
        log.debug("REST request to save Educationlevel : {}", educationlevelDTO);
        if (educationlevelDTO.getId() != null) {
            throw new BadRequestAlertException("A new educationlevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EducationlevelDTO result = educationlevelService.save(educationlevelDTO);
        return ResponseEntity
            .created(new URI("/api/educationlevels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /educationlevels/:id} : Updates an existing educationlevel.
     *
     * @param id the id of the educationlevelDTO to save.
     * @param educationlevelDTO the educationlevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educationlevelDTO,
     * or with status {@code 400 (Bad Request)} if the educationlevelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the educationlevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/educationlevels/{id}")
    public ResponseEntity<EducationlevelDTO> updateEducationlevel(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody EducationlevelDTO educationlevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Educationlevel : {}, {}", id, educationlevelDTO);
        if (educationlevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, educationlevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educationlevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EducationlevelDTO result = educationlevelService.update(educationlevelDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, educationlevelDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /educationlevels/:id} : Partial updates given fields of an existing educationlevel, field will ignore if it is null
     *
     * @param id the id of the educationlevelDTO to save.
     * @param educationlevelDTO the educationlevelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated educationlevelDTO,
     * or with status {@code 400 (Bad Request)} if the educationlevelDTO is not valid,
     * or with status {@code 404 (Not Found)} if the educationlevelDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the educationlevelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/educationlevels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EducationlevelDTO> partialUpdateEducationlevel(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody EducationlevelDTO educationlevelDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Educationlevel partially : {}, {}", id, educationlevelDTO);
        if (educationlevelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, educationlevelDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!educationlevelRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EducationlevelDTO> result = educationlevelService.partialUpdate(educationlevelDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, educationlevelDTO.getId())
        );
    }

    /**
     * {@code GET  /educationlevels} : get all the educationlevels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of educationlevels in body.
     */
    @GetMapping("/educationlevels")
    public ResponseEntity<List<EducationlevelDTO>> getAllEducationlevels(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Educationlevels");
        Page<EducationlevelDTO> page = educationlevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /educationlevels/:id} : get the "id" educationlevel.
     *
     * @param id the id of the educationlevelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the educationlevelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/educationlevels/{id}")
    public ResponseEntity<EducationlevelDTO> getEducationlevel(@PathVariable String id) {
        log.debug("REST request to get Educationlevel : {}", id);
        Optional<EducationlevelDTO> educationlevelDTO = educationlevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(educationlevelDTO);
    }

    /**
     * {@code DELETE  /educationlevels/:id} : delete the "id" educationlevel.
     *
     * @param id the id of the educationlevelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/educationlevels/{id}")
    public ResponseEntity<Void> deleteEducationlevel(@PathVariable String id) {
        log.debug("REST request to delete Educationlevel : {}", id);
        educationlevelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
