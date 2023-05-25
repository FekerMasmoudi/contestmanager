package tn.soretras.contestmanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.soretras.contestmanager.domain.Sector;
import tn.soretras.contestmanager.repository.SectorRepository;
import tn.soretras.contestmanager.service.dto.SectorDTO;
import tn.soretras.contestmanager.service.mapper.SectorMapper;

/**
 * Service Implementation for managing {@link Sector}.
 */
@Service
public class SectorService {

    private final Logger log = LoggerFactory.getLogger(SectorService.class);

    private final SectorRepository sectorRepository;

    private final SectorMapper sectorMapper;

    public SectorService(SectorRepository sectorRepository, SectorMapper sectorMapper) {
        this.sectorRepository = sectorRepository;
        this.sectorMapper = sectorMapper;
    }

    /**
     * Save a sector.
     *
     * @param sectorDTO the entity to save.
     * @return the persisted entity.
     */
    public SectorDTO save(SectorDTO sectorDTO) {
        log.debug("Request to save Sector : {}", sectorDTO);
        Sector sector = sectorMapper.toEntity(sectorDTO);
        sector = sectorRepository.save(sector);
        return sectorMapper.toDto(sector);
    }

    /**
     * Update a sector.
     *
     * @param sectorDTO the entity to save.
     * @return the persisted entity.
     */
    public SectorDTO update(SectorDTO sectorDTO) {
        log.debug("Request to update Sector : {}", sectorDTO);
        Sector sector = sectorMapper.toEntity(sectorDTO);
        sector = sectorRepository.save(sector);
        return sectorMapper.toDto(sector);
    }

    /**
     * Partially update a sector.
     *
     * @param sectorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SectorDTO> partialUpdate(SectorDTO sectorDTO) {
        log.debug("Request to partially update Sector : {}", sectorDTO);

        return sectorRepository
            .findById(sectorDTO.getId())
            .map(existingSector -> {
                sectorMapper.partialUpdate(existingSector, sectorDTO);

                return existingSector;
            })
            .map(sectorRepository::save)
            .map(sectorMapper::toDto);
    }

    /**
     * Get all the sectors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<SectorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sectors");
        return sectorRepository.findAll(pageable).map(sectorMapper::toDto);
    }

    /**
     * Get one sector by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<SectorDTO> findOne(String id) {
        log.debug("Request to get Sector : {}", id);
        return sectorRepository.findById(id).map(sectorMapper::toDto);
    }

    /**
     * Delete the sector by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Sector : {}", id);
        sectorRepository.deleteById(id);
    }
}
