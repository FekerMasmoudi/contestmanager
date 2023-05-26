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
import tn.soretras.contestmanager.domain.Educationlevel;
import tn.soretras.contestmanager.repository.EducationlevelRepository;
import tn.soretras.contestmanager.service.dto.EducationlevelDTO;
import tn.soretras.contestmanager.service.mapper.EducationlevelMapper;

/**
 * Integration tests for the {@link EducationlevelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EducationlevelResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/educationlevels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EducationlevelRepository educationlevelRepository;

    @Autowired
    private EducationlevelMapper educationlevelMapper;

    @Autowired
    private MockMvc restEducationlevelMockMvc;

    private Educationlevel educationlevel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Educationlevel createEntity() {
        Educationlevel educationlevel = new Educationlevel().designation(DEFAULT_DESIGNATION);
        return educationlevel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Educationlevel createUpdatedEntity() {
        Educationlevel educationlevel = new Educationlevel().designation(UPDATED_DESIGNATION);
        return educationlevel;
    }

    @BeforeEach
    public void initTest() {
        educationlevelRepository.deleteAll();
        educationlevel = createEntity();
    }

    @Test
    void createEducationlevel() throws Exception {
        int databaseSizeBeforeCreate = educationlevelRepository.findAll().size();
        // Create the Educationlevel
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);
        restEducationlevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationlevelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeCreate + 1);
        Educationlevel testEducationlevel = educationlevelList.get(educationlevelList.size() - 1);
        assertThat(testEducationlevel.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    void createEducationlevelWithExistingId() throws Exception {
        // Create the Educationlevel with an existing ID
        educationlevel.setId("existing_id");
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);

        int databaseSizeBeforeCreate = educationlevelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationlevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationlevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = educationlevelRepository.findAll().size();
        // set the field null
        educationlevel.setDesignation(null);

        // Create the Educationlevel, which fails.
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);

        restEducationlevelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationlevelDTO))
            )
            .andExpect(status().isBadRequest());

        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllEducationlevels() throws Exception {
        // Initialize the database
        educationlevelRepository.save(educationlevel);

        // Get all the educationlevelList
        restEducationlevelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationlevel.getId())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }

    @Test
    void getEducationlevel() throws Exception {
        // Initialize the database
        educationlevelRepository.save(educationlevel);

        // Get the educationlevel
        restEducationlevelMockMvc
            .perform(get(ENTITY_API_URL_ID, educationlevel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(educationlevel.getId()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }

    @Test
    void getNonExistingEducationlevel() throws Exception {
        // Get the educationlevel
        restEducationlevelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingEducationlevel() throws Exception {
        // Initialize the database
        educationlevelRepository.save(educationlevel);

        int databaseSizeBeforeUpdate = educationlevelRepository.findAll().size();

        // Update the educationlevel
        Educationlevel updatedEducationlevel = educationlevelRepository.findById(educationlevel.getId()).get();
        updatedEducationlevel.designation(UPDATED_DESIGNATION);
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(updatedEducationlevel);

        restEducationlevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationlevelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationlevelDTO))
            )
            .andExpect(status().isOk());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeUpdate);
        Educationlevel testEducationlevel = educationlevelList.get(educationlevelList.size() - 1);
        assertThat(testEducationlevel.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    void putNonExistingEducationlevel() throws Exception {
        int databaseSizeBeforeUpdate = educationlevelRepository.findAll().size();
        educationlevel.setId(UUID.randomUUID().toString());

        // Create the Educationlevel
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationlevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationlevelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationlevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEducationlevel() throws Exception {
        int databaseSizeBeforeUpdate = educationlevelRepository.findAll().size();
        educationlevel.setId(UUID.randomUUID().toString());

        // Create the Educationlevel
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationlevelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationlevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEducationlevel() throws Exception {
        int databaseSizeBeforeUpdate = educationlevelRepository.findAll().size();
        educationlevel.setId(UUID.randomUUID().toString());

        // Create the Educationlevel
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationlevelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationlevelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEducationlevelWithPatch() throws Exception {
        // Initialize the database
        educationlevelRepository.save(educationlevel);

        int databaseSizeBeforeUpdate = educationlevelRepository.findAll().size();

        // Update the educationlevel using partial update
        Educationlevel partialUpdatedEducationlevel = new Educationlevel();
        partialUpdatedEducationlevel.setId(educationlevel.getId());

        restEducationlevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducationlevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducationlevel))
            )
            .andExpect(status().isOk());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeUpdate);
        Educationlevel testEducationlevel = educationlevelList.get(educationlevelList.size() - 1);
        assertThat(testEducationlevel.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    void fullUpdateEducationlevelWithPatch() throws Exception {
        // Initialize the database
        educationlevelRepository.save(educationlevel);

        int databaseSizeBeforeUpdate = educationlevelRepository.findAll().size();

        // Update the educationlevel using partial update
        Educationlevel partialUpdatedEducationlevel = new Educationlevel();
        partialUpdatedEducationlevel.setId(educationlevel.getId());

        partialUpdatedEducationlevel.designation(UPDATED_DESIGNATION);

        restEducationlevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducationlevel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducationlevel))
            )
            .andExpect(status().isOk());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeUpdate);
        Educationlevel testEducationlevel = educationlevelList.get(educationlevelList.size() - 1);
        assertThat(testEducationlevel.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    void patchNonExistingEducationlevel() throws Exception {
        int databaseSizeBeforeUpdate = educationlevelRepository.findAll().size();
        educationlevel.setId(UUID.randomUUID().toString());

        // Create the Educationlevel
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationlevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, educationlevelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationlevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEducationlevel() throws Exception {
        int databaseSizeBeforeUpdate = educationlevelRepository.findAll().size();
        educationlevel.setId(UUID.randomUUID().toString());

        // Create the Educationlevel
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationlevelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationlevelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEducationlevel() throws Exception {
        int databaseSizeBeforeUpdate = educationlevelRepository.findAll().size();
        educationlevel.setId(UUID.randomUUID().toString());

        // Create the Educationlevel
        EducationlevelDTO educationlevelDTO = educationlevelMapper.toDto(educationlevel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationlevelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationlevelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Educationlevel in the database
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEducationlevel() throws Exception {
        // Initialize the database
        educationlevelRepository.save(educationlevel);

        int databaseSizeBeforeDelete = educationlevelRepository.findAll().size();

        // Delete the educationlevel
        restEducationlevelMockMvc
            .perform(delete(ENTITY_API_URL_ID, educationlevel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Educationlevel> educationlevelList = educationlevelRepository.findAll();
        assertThat(educationlevelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
