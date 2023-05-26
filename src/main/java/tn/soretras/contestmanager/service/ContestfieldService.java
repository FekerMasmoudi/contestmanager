package tn.soretras.contestmanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.soretras.contestmanager.domain.Contestfield;
import tn.soretras.contestmanager.repository.ContestfieldRepository;
import tn.soretras.contestmanager.service.dto.ContestfieldDTO;
import tn.soretras.contestmanager.service.mapper.ContestfieldMapper;

/**
 * Service Implementation for managing {@link Contestfield}.
 */
@Service
public class ContestfieldService {

    private final Logger log = LoggerFactory.getLogger(ContestfieldService.class);

    private final ContestfieldRepository contestfieldRepository;

    private final ContestfieldMapper contestfieldMapper;

    public ContestfieldService(ContestfieldRepository contestfieldRepository, ContestfieldMapper contestfieldMapper) {
        this.contestfieldRepository = contestfieldRepository;
        this.contestfieldMapper = contestfieldMapper;
    }

    /**
     * Save a contestfield.
     *
     * @param contestfieldDTO the entity to save.
     * @return the persisted entity.
     */
    public ContestfieldDTO save(ContestfieldDTO contestfieldDTO) {
        log.debug("Request to save Contestfield : {}", contestfieldDTO);
        Contestfield contestfield = contestfieldMapper.toEntity(contestfieldDTO);
        contestfield = contestfieldRepository.save(contestfield);
        return contestfieldMapper.toDto(contestfield);
    }

    /**
     * Update a contestfield.
     *
     * @param contestfieldDTO the entity to save.
     * @return the persisted entity.
     */
    public ContestfieldDTO update(ContestfieldDTO contestfieldDTO) {
        log.debug("Request to update Contestfield : {}", contestfieldDTO);
        Contestfield contestfield = contestfieldMapper.toEntity(contestfieldDTO);
        contestfield = contestfieldRepository.save(contestfield);
        return contestfieldMapper.toDto(contestfield);
    }

    /**
     * Partially update a contestfield.
     *
     * @param contestfieldDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContestfieldDTO> partialUpdate(ContestfieldDTO contestfieldDTO) {
        log.debug("Request to partially update Contestfield : {}", contestfieldDTO);

        return contestfieldRepository
            .findById(contestfieldDTO.getId())
            .map(existingContestfield -> {
                contestfieldMapper.partialUpdate(existingContestfield, contestfieldDTO);

                return existingContestfield;
            })
            .map(contestfieldRepository::save)
            .map(contestfieldMapper::toDto);
    }

    /**
     * Get all the contestfields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ContestfieldDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contestfields");
        return contestfieldRepository.findAll(pageable).map(contestfieldMapper::toDto);
    }

    /**
     * Get one contestfield by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ContestfieldDTO> findOne(String id) {
        log.debug("Request to get Contestfield : {}", id);
        return contestfieldRepository.findById(id).map(contestfieldMapper::toDto);
    }

    /**
     * Delete the contestfield by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Contestfield : {}", id);
        contestfieldRepository.deleteById(id);
    }
}
