package tn.soretras.contestmanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.soretras.contestmanager.domain.Contestform;
import tn.soretras.contestmanager.repository.ContestformRepository;
import tn.soretras.contestmanager.service.dto.ContestformDTO;
import tn.soretras.contestmanager.service.mapper.ContestformMapper;

/**
 * Service Implementation for managing {@link Contestform}.
 */
@Service
public class ContestformService {

    private final Logger log = LoggerFactory.getLogger(ContestformService.class);

    private final ContestformRepository contestformRepository;

    private final ContestformMapper contestformMapper;

    public ContestformService(ContestformRepository contestformRepository, ContestformMapper contestformMapper) {
        this.contestformRepository = contestformRepository;
        this.contestformMapper = contestformMapper;
    }

    /**
     * Save a contestform.
     *
     * @param contestformDTO the entity to save.
     * @return the persisted entity.
     */
    public ContestformDTO save(ContestformDTO contestformDTO) {
        log.debug("Request to save Contestform : {}", contestformDTO);
        Contestform contestform = contestformMapper.toEntity(contestformDTO);
        contestform = contestformRepository.save(contestform);
        return contestformMapper.toDto(contestform);
    }

    /**
     * Update a contestform.
     *
     * @param contestformDTO the entity to save.
     * @return the persisted entity.
     */
    public ContestformDTO update(ContestformDTO contestformDTO) {
        log.debug("Request to update Contestform : {}", contestformDTO);
        Contestform contestform = contestformMapper.toEntity(contestformDTO);
        contestform = contestformRepository.save(contestform);
        return contestformMapper.toDto(contestform);
    }

    /**
     * Partially update a contestform.
     *
     * @param contestformDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ContestformDTO> partialUpdate(ContestformDTO contestformDTO) {
        log.debug("Request to partially update Contestform : {}", contestformDTO);

        return contestformRepository
            .findById(contestformDTO.getId())
            .map(existingContestform -> {
                contestformMapper.partialUpdate(existingContestform, contestformDTO);

                return existingContestform;
            })
            .map(contestformRepository::save)
            .map(contestformMapper::toDto);
    }

    /**
     * Get all the contestforms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<ContestformDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contestforms");
        return contestformRepository.findAll(pageable).map(contestformMapper::toDto);
    }

    /**
     * Get all the contestforms with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ContestformDTO> findAllWithEagerRelationships(Pageable pageable) {
        return contestformRepository.findAllWithEagerRelationships(pageable).map(contestformMapper::toDto);
    }

    /**
     * Get one contestform by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<ContestformDTO> findOne(String id) {
        log.debug("Request to get Contestform : {}", id);
        return contestformRepository.findOneWithEagerRelationships(id).map(contestformMapper::toDto);
    }

    /**
     * Delete the contestform by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Contestform : {}", id);
        contestformRepository.deleteById(id);
    }
}
