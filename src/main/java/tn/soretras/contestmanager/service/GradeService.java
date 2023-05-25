package tn.soretras.contestmanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.soretras.contestmanager.domain.Grade;
import tn.soretras.contestmanager.repository.GradeRepository;
import tn.soretras.contestmanager.service.dto.GradeDTO;
import tn.soretras.contestmanager.service.mapper.GradeMapper;

/**
 * Service Implementation for managing {@link Grade}.
 */
@Service
public class GradeService {

    private final Logger log = LoggerFactory.getLogger(GradeService.class);

    private final GradeRepository gradeRepository;

    private final GradeMapper gradeMapper;

    public GradeService(GradeRepository gradeRepository, GradeMapper gradeMapper) {
        this.gradeRepository = gradeRepository;
        this.gradeMapper = gradeMapper;
    }

    /**
     * Save a grade.
     *
     * @param gradeDTO the entity to save.
     * @return the persisted entity.
     */
    public GradeDTO save(GradeDTO gradeDTO) {
        log.debug("Request to save Grade : {}", gradeDTO);
        Grade grade = gradeMapper.toEntity(gradeDTO);
        grade = gradeRepository.save(grade);
        return gradeMapper.toDto(grade);
    }

    /**
     * Update a grade.
     *
     * @param gradeDTO the entity to save.
     * @return the persisted entity.
     */
    public GradeDTO update(GradeDTO gradeDTO) {
        log.debug("Request to update Grade : {}", gradeDTO);
        Grade grade = gradeMapper.toEntity(gradeDTO);
        grade = gradeRepository.save(grade);
        return gradeMapper.toDto(grade);
    }

    /**
     * Partially update a grade.
     *
     * @param gradeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GradeDTO> partialUpdate(GradeDTO gradeDTO) {
        log.debug("Request to partially update Grade : {}", gradeDTO);

        return gradeRepository
            .findById(gradeDTO.getId())
            .map(existingGrade -> {
                gradeMapper.partialUpdate(existingGrade, gradeDTO);

                return existingGrade;
            })
            .map(gradeRepository::save)
            .map(gradeMapper::toDto);
    }

    /**
     * Get all the grades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<GradeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Grades");
        return gradeRepository.findAll(pageable).map(gradeMapper::toDto);
    }

    /**
     * Get one grade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<GradeDTO> findOne(String id) {
        log.debug("Request to get Grade : {}", id);
        return gradeRepository.findById(id).map(gradeMapper::toDto);
    }

    /**
     * Delete the grade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Grade : {}", id);
        gradeRepository.deleteById(id);
    }
}
