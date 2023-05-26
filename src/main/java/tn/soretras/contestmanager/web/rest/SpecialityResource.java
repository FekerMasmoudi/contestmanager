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
import tn.soretras.contestmanager.repository.SpecialityRepository;
import tn.soretras.contestmanager.service.SpecialityService;
import tn.soretras.contestmanager.service.dto.SpecialityDTO;
import tn.soretras.contestmanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.soretras.contestmanager.domain.Speciality}.
 */
@RestController
@RequestMapping("/api")
public class SpecialityResource {

    private final Logger log = LoggerFactory.getLogger(SpecialityResource.class);

    private static final String ENTITY_NAME = "speciality";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpecialityService specialityService;

    private final SpecialityRepository specialityRepository;

    public SpecialityResource(SpecialityService specialityService, SpecialityRepository specialityRepository) {
        this.specialityService = specialityService;
        this.specialityRepository = specialityRepository;
    }

    /**
     * {@code POST  /specialities} : Create a new speciality.
     *
     * @param specialityDTO the specialityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new specialityDTO, or with status {@code 400 (Bad Request)} if the speciality has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/specialities")
    public ResponseEntity<SpecialityDTO> createSpeciality(@Valid @RequestBody SpecialityDTO specialityDTO) throws URISyntaxException {
        log.debug("REST request to save Speciality : {}", specialityDTO);
        if (specialityDTO.getId() != null) {
            throw new BadRequestAlertException("A new speciality cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpecialityDTO result = specialityService.save(specialityDTO);
        return ResponseEntity
            .created(new URI("/api/specialities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /specialities/:id} : Updates an existing speciality.
     *
     * @param id the id of the specialityDTO to save.
     * @param specialityDTO the specialityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialityDTO,
     * or with status {@code 400 (Bad Request)} if the specialityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the specialityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/specialities/{id}")
    public ResponseEntity<SpecialityDTO> updateSpeciality(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody SpecialityDTO specialityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Speciality : {}, {}", id, specialityDTO);
        if (specialityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specialityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specialityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SpecialityDTO result = specialityService.update(specialityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, specialityDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /specialities/:id} : Partial updates given fields of an existing speciality, field will ignore if it is null
     *
     * @param id the id of the specialityDTO to save.
     * @param specialityDTO the specialityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated specialityDTO,
     * or with status {@code 400 (Bad Request)} if the specialityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the specialityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the specialityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/specialities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SpecialityDTO> partialUpdateSpeciality(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody SpecialityDTO specialityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Speciality partially : {}, {}", id, specialityDTO);
        if (specialityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, specialityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!specialityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SpecialityDTO> result = specialityService.partialUpdate(specialityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, specialityDTO.getId())
        );
    }

    /**
     * {@code GET  /specialities} : get all the specialities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of specialities in body.
     */
    @GetMapping("/specialities")
    public ResponseEntity<List<SpecialityDTO>> getAllSpecialities(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Specialities");
        Page<SpecialityDTO> page = specialityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /specialities/:id} : get the "id" speciality.
     *
     * @param id the id of the specialityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the specialityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/specialities/{id}")
    public ResponseEntity<SpecialityDTO> getSpeciality(@PathVariable String id) {
        log.debug("REST request to get Speciality : {}", id);
        Optional<SpecialityDTO> specialityDTO = specialityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(specialityDTO);
    }

    /**
     * {@code DELETE  /specialities/:id} : delete the "id" speciality.
     *
     * @param id the id of the specialityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/specialities/{id}")
    public ResponseEntity<Void> deleteSpeciality(@PathVariable String id) {
        log.debug("REST request to delete Speciality : {}", id);
        specialityService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
