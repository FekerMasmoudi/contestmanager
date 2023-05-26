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
import tn.soretras.contestmanager.repository.SectorRepository;
import tn.soretras.contestmanager.service.SectorService;
import tn.soretras.contestmanager.service.dto.SectorDTO;
import tn.soretras.contestmanager.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tn.soretras.contestmanager.domain.Sector}.
 */
@RestController
@RequestMapping("/api")
public class SectorResource {

    private final Logger log = LoggerFactory.getLogger(SectorResource.class);

    private static final String ENTITY_NAME = "sector";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SectorService sectorService;

    private final SectorRepository sectorRepository;

    public SectorResource(SectorService sectorService, SectorRepository sectorRepository) {
        this.sectorService = sectorService;
        this.sectorRepository = sectorRepository;
    }

    /**
     * {@code POST  /sectors} : Create a new sector.
     *
     * @param sectorDTO the sectorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sectorDTO, or with status {@code 400 (Bad Request)} if the sector has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sectors")
    public ResponseEntity<SectorDTO> createSector(@Valid @RequestBody SectorDTO sectorDTO) throws URISyntaxException {
        log.debug("REST request to save Sector : {}", sectorDTO);
        if (sectorDTO.getId() != null) {
            throw new BadRequestAlertException("A new sector cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SectorDTO result = sectorService.save(sectorDTO);
        return ResponseEntity
            .created(new URI("/api/sectors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /sectors/:id} : Updates an existing sector.
     *
     * @param id the id of the sectorDTO to save.
     * @param sectorDTO the sectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sectorDTO,
     * or with status {@code 400 (Bad Request)} if the sectorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sectors/{id}")
    public ResponseEntity<SectorDTO> updateSector(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody SectorDTO sectorDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Sector : {}, {}", id, sectorDTO);
        if (sectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sectorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SectorDTO result = sectorService.update(sectorDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sectorDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /sectors/:id} : Partial updates given fields of an existing sector, field will ignore if it is null
     *
     * @param id the id of the sectorDTO to save.
     * @param sectorDTO the sectorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sectorDTO,
     * or with status {@code 400 (Bad Request)} if the sectorDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sectorDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sectorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sectors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SectorDTO> partialUpdateSector(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody SectorDTO sectorDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Sector partially : {}, {}", id, sectorDTO);
        if (sectorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sectorDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sectorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SectorDTO> result = sectorService.partialUpdate(sectorDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sectorDTO.getId())
        );
    }

    /**
     * {@code GET  /sectors} : get all the sectors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sectors in body.
     */
    @GetMapping("/sectors")
    public ResponseEntity<List<SectorDTO>> getAllSectors(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Sectors");
        Page<SectorDTO> page = sectorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sectors/:id} : get the "id" sector.
     *
     * @param id the id of the sectorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sectorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sectors/{id}")
    public ResponseEntity<SectorDTO> getSector(@PathVariable String id) {
        log.debug("REST request to get Sector : {}", id);
        Optional<SectorDTO> sectorDTO = sectorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sectorDTO);
    }

    /**
     * {@code DELETE  /sectors/:id} : delete the "id" sector.
     *
     * @param id the id of the sectorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sectors/{id}")
    public ResponseEntity<Void> deleteSector(@PathVariable String id) {
        log.debug("REST request to delete Sector : {}", id);
        sectorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
