package tn.soretras.contestmanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.soretras.contestmanager.domain.Educationlevel;
import tn.soretras.contestmanager.repository.EducationlevelRepository;
import tn.soretras.contestmanager.service.dto.EducationlevelDTO;
import tn.soretras.contestmanager.service.mapper.EducationlevelMapper;

/**
 * Service Implementation for managing {@link Educationlevel}.
 */
@Service
public class EducationlevelService {

    private final Logger log = LoggerFactory.getLogger(EducationlevelService.class);

    private final EducationlevelRepository educationlevelRepository;

    private final EducationlevelMapper educationlevelMapper;

    public EducationlevelService(EducationlevelRepository educationlevelRepository, EducationlevelMapper educationlevelMapper) {
        this.educationlevelRepository = educationlevelRepository;
        this.educationlevelMapper = educationlevelMapper;
    }

    /**
     * Save a educationlevel.
     *
     * @param educationlevelDTO the entity to save.
     * @return the persisted entity.
     */
    public EducationlevelDTO save(EducationlevelDTO educationlevelDTO) {
        log.debug("Request to save Educationlevel : {}", educationlevelDTO);
        Educationlevel educationlevel = educationlevelMapper.toEntity(educationlevelDTO);
        educationlevel = educationlevelRepository.save(educationlevel);
        return educationlevelMapper.toDto(educationlevel);
    }

    /**
     * Update a educationlevel.
     *
     * @param educationlevelDTO the entity to save.
     * @return the persisted entity.
     */
    public EducationlevelDTO update(EducationlevelDTO educationlevelDTO) {
        log.debug("Request to update Educationlevel : {}", educationlevelDTO);
        Educationlevel educationlevel = educationlevelMapper.toEntity(educationlevelDTO);
        educationlevel = educationlevelRepository.save(educationlevel);
        return educationlevelMapper.toDto(educationlevel);
    }

    /**
     * Partially update a educationlevel.
     *
     * @param educationlevelDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EducationlevelDTO> partialUpdate(EducationlevelDTO educationlevelDTO) {
        log.debug("Request to partially update Educationlevel : {}", educationlevelDTO);

        return educationlevelRepository
            .findById(educationlevelDTO.getId())
            .map(existingEducationlevel -> {
                educationlevelMapper.partialUpdate(existingEducationlevel, educationlevelDTO);

                return existingEducationlevel;
            })
            .map(educationlevelRepository::save)
            .map(educationlevelMapper::toDto);
    }

    /**
     * Get all the educationlevels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<EducationlevelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Educationlevels");
        return educationlevelRepository.findAll(pageable).map(educationlevelMapper::toDto);
    }

    /**
     * Get one educationlevel by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<EducationlevelDTO> findOne(String id) {
        log.debug("Request to get Educationlevel : {}", id);
        return educationlevelRepository.findById(id).map(educationlevelMapper::toDto);
    }

    /**
     * Delete the educationlevel by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Educationlevel : {}", id);
        educationlevelRepository.deleteById(id);
    }
}
