package tn.soretras.contestmanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.soretras.contestmanager.domain.Generalrules;
import tn.soretras.contestmanager.repository.GeneralrulesRepository;
import tn.soretras.contestmanager.service.dto.GeneralrulesDTO;
import tn.soretras.contestmanager.service.mapper.GeneralrulesMapper;

/**
 * Service Implementation for managing {@link Generalrules}.
 */
@Service
public class GeneralrulesService {

    private final Logger log = LoggerFactory.getLogger(GeneralrulesService.class);

    private final GeneralrulesRepository generalrulesRepository;

    private final GeneralrulesMapper generalrulesMapper;

    public GeneralrulesService(GeneralrulesRepository generalrulesRepository, GeneralrulesMapper generalrulesMapper) {
        this.generalrulesRepository = generalrulesRepository;
        this.generalrulesMapper = generalrulesMapper;
    }

    /**
     * Save a generalrules.
     *
     * @param generalrulesDTO the entity to save.
     * @return the persisted entity.
     */
    public GeneralrulesDTO save(GeneralrulesDTO generalrulesDTO) {
        log.debug("Request to save Generalrules : {}", generalrulesDTO);
        Generalrules generalrules = generalrulesMapper.toEntity(generalrulesDTO);
        generalrules = generalrulesRepository.save(generalrules);
        return generalrulesMapper.toDto(generalrules);
    }

    /**
     * Update a generalrules.
     *
     * @param generalrulesDTO the entity to save.
     * @return the persisted entity.
     */
    public GeneralrulesDTO update(GeneralrulesDTO generalrulesDTO) {
        log.debug("Request to update Generalrules : {}", generalrulesDTO);
        Generalrules generalrules = generalrulesMapper.toEntity(generalrulesDTO);
        generalrules = generalrulesRepository.save(generalrules);
        return generalrulesMapper.toDto(generalrules);
    }

    /**
     * Partially update a generalrules.
     *
     * @param generalrulesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GeneralrulesDTO> partialUpdate(GeneralrulesDTO generalrulesDTO) {
        log.debug("Request to partially update Generalrules : {}", generalrulesDTO);

        return generalrulesRepository
            .findById(generalrulesDTO.getId())
            .map(existingGeneralrules -> {
                generalrulesMapper.partialUpdate(existingGeneralrules, generalrulesDTO);

                return existingGeneralrules;
            })
            .map(generalrulesRepository::save)
            .map(generalrulesMapper::toDto);
    }

    /**
     * Get all the generalrules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<GeneralrulesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Generalrules");
        return generalrulesRepository.findAll(pageable).map(generalrulesMapper::toDto);
    }

    /**
     * Get one generalrules by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<GeneralrulesDTO> findOne(String id) {
        log.debug("Request to get Generalrules : {}", id);
        return generalrulesRepository.findById(id).map(generalrulesMapper::toDto);
    }

    /**
     * Delete the generalrules by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Generalrules : {}", id);
        generalrulesRepository.deleteById(id);
    }
}
