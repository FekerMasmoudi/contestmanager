package tn.soretras.contestmanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import tn.soretras.contestmanager.IntegrationTest;
import tn.soretras.contestmanager.domain.Grade;
import tn.soretras.contestmanager.repository.GradeRepository;
import tn.soretras.contestmanager.service.dto.GradeDTO;
import tn.soretras.contestmanager.service.mapper.GradeMapper;

/**
 * Integration tests for the {@link GradeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GradeResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/grades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private GradeMapper gradeMapper;

    @Autowired
    private MockMvc restGradeMockMvc;

    private Grade grade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grade createEntity() {
        Grade grade = new Grade().designation(DEFAULT_DESIGNATION);
        return grade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Grade createUpdatedEntity() {
        Grade grade = new Grade().designation(UPDATED_DESIGNATION);
        return grade;
    }

    @BeforeEach
    public void initTest() {
        gradeRepository.deleteAll();
        grade = createEntity();
    }

    @Test
    void createGrade() throws Exception {
        int databaseSizeBeforeCreate = gradeRepository.findAll().size();
        // Create the Grade
        GradeDTO gradeDTO = gradeMapper.toDto(grade);
        restGradeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gradeDTO)))
            .andExpect(status().isCreated());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeCreate + 1);
        Grade testGrade = gradeList.get(gradeList.size() - 1);
        assertThat(testGrade.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    void createGradeWithExistingId() throws Exception {
        // Create the Grade with an existing ID
        grade.setId("existing_id");
        GradeDTO gradeDTO = gradeMapper.toDto(grade);

        int databaseSizeBeforeCreate = gradeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGradeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gradeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = gradeRepository.findAll().size();
        // set the field null
        grade.setDesignation(null);

        // Create the Grade, which fails.
        GradeDTO gradeDTO = gradeMapper.toDto(grade);

        restGradeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gradeDTO)))
            .andExpect(status().isBadRequest());

        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllGrades() throws Exception {
        // Initialize the database
        gradeRepository.save(grade);

        // Get all the gradeList
        restGradeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(grade.getId())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }

    @Test
    void getGrade() throws Exception {
        // Initialize the database
        gradeRepository.save(grade);

        // Get the grade
        restGradeMockMvc
            .perform(get(ENTITY_API_URL_ID, grade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(grade.getId()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }

    @Test
    void getNonExistingGrade() throws Exception {
        // Get the grade
        restGradeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingGrade() throws Exception {
        // Initialize the database
        gradeRepository.save(grade);

        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();

        // Update the grade
        Grade updatedGrade = gradeRepository.findById(grade.getId()).get();
        updatedGrade.designation(UPDATED_DESIGNATION);
        GradeDTO gradeDTO = gradeMapper.toDto(updatedGrade);

        restGradeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gradeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gradeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
        Grade testGrade = gradeList.get(gradeList.size() - 1);
        assertThat(testGrade.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    void putNonExistingGrade() throws Exception {
        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();
        grade.setId(UUID.randomUUID().toString());

        // Create the Grade
        GradeDTO gradeDTO = gradeMapper.toDto(grade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGradeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gradeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gradeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGrade() throws Exception {
        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();
        grade.setId(UUID.randomUUID().toString());

        // Create the Grade
        GradeDTO gradeDTO = gradeMapper.toDto(grade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGradeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gradeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGrade() throws Exception {
        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();
        grade.setId(UUID.randomUUID().toString());

        // Create the Grade
        GradeDTO gradeDTO = gradeMapper.toDto(grade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGradeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gradeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGradeWithPatch() throws Exception {
        // Initialize the database
        gradeRepository.save(grade);

        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();

        // Update the grade using partial update
        Grade partialUpdatedGrade = new Grade();
        partialUpdatedGrade.setId(grade.getId());

        partialUpdatedGrade.designation(UPDATED_DESIGNATION);

        restGradeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrade))
            )
            .andExpect(status().isOk());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
        Grade testGrade = gradeList.get(gradeList.size() - 1);
        assertThat(testGrade.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    void fullUpdateGradeWithPatch() throws Exception {
        // Initialize the database
        gradeRepository.save(grade);

        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();

        // Update the grade using partial update
        Grade partialUpdatedGrade = new Grade();
        partialUpdatedGrade.setId(grade.getId());

        partialUpdatedGrade.designation(UPDATED_DESIGNATION);

        restGradeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGrade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGrade))
            )
            .andExpect(status().isOk());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
        Grade testGrade = gradeList.get(gradeList.size() - 1);
        assertThat(testGrade.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    void patchNonExistingGrade() throws Exception {
        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();
        grade.setId(UUID.randomUUID().toString());

        // Create the Grade
        GradeDTO gradeDTO = gradeMapper.toDto(grade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGradeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gradeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gradeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGrade() throws Exception {
        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();
        grade.setId(UUID.randomUUID().toString());

        // Create the Grade
        GradeDTO gradeDTO = gradeMapper.toDto(grade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGradeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gradeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGrade() throws Exception {
        int databaseSizeBeforeUpdate = gradeRepository.findAll().size();
        grade.setId(UUID.randomUUID().toString());

        // Create the Grade
        GradeDTO gradeDTO = gradeMapper.toDto(grade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGradeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gradeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Grade in the database
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGrade() throws Exception {
        // Initialize the database
        gradeRepository.save(grade);

        int databaseSizeBeforeDelete = gradeRepository.findAll().size();

        // Delete the grade
        restGradeMockMvc
            .perform(delete(ENTITY_API_URL_ID, grade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Grade> gradeList = gradeRepository.findAll();
        assertThat(gradeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
