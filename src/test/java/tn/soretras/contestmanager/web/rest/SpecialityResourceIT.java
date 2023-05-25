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
import tn.soretras.contestmanager.domain.Speciality;
import tn.soretras.contestmanager.repository.SpecialityRepository;
import tn.soretras.contestmanager.service.dto.SpecialityDTO;
import tn.soretras.contestmanager.service.mapper.SpecialityMapper;

/**
 * Integration tests for the {@link SpecialityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SpecialityResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/specialities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private SpecialityMapper specialityMapper;

    @Autowired
    private MockMvc restSpecialityMockMvc;

    private Speciality speciality;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Speciality createEntity() {
        Speciality speciality = new Speciality().designation(DEFAULT_DESIGNATION);
        return speciality;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Speciality createUpdatedEntity() {
        Speciality speciality = new Speciality().designation(UPDATED_DESIGNATION);
        return speciality;
    }

    @BeforeEach
    public void initTest() {
        specialityRepository.deleteAll();
        speciality = createEntity();
    }

    @Test
    void createSpeciality() throws Exception {
        int databaseSizeBeforeCreate = specialityRepository.findAll().size();
        // Create the Speciality
        SpecialityDTO specialityDTO = specialityMapper.toDto(speciality);
        restSpecialityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialityDTO)))
            .andExpect(status().isCreated());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeCreate + 1);
        Speciality testSpeciality = specialityList.get(specialityList.size() - 1);
        assertThat(testSpeciality.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    void createSpecialityWithExistingId() throws Exception {
        // Create the Speciality with an existing ID
        speciality.setId("existing_id");
        SpecialityDTO specialityDTO = specialityMapper.toDto(speciality);

        int databaseSizeBeforeCreate = specialityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialityRepository.findAll().size();
        // set the field null
        speciality.setDesignation(null);

        // Create the Speciality, which fails.
        SpecialityDTO specialityDTO = specialityMapper.toDto(speciality);

        restSpecialityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialityDTO)))
            .andExpect(status().isBadRequest());

        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSpecialities() throws Exception {
        // Initialize the database
        specialityRepository.save(speciality);

        // Get all the specialityList
        restSpecialityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(speciality.getId())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }

    @Test
    void getSpeciality() throws Exception {
        // Initialize the database
        specialityRepository.save(speciality);

        // Get the speciality
        restSpecialityMockMvc
            .perform(get(ENTITY_API_URL_ID, speciality.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(speciality.getId()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }

    @Test
    void getNonExistingSpeciality() throws Exception {
        // Get the speciality
        restSpecialityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSpeciality() throws Exception {
        // Initialize the database
        specialityRepository.save(speciality);

        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();

        // Update the speciality
        Speciality updatedSpeciality = specialityRepository.findById(speciality.getId()).get();
        updatedSpeciality.designation(UPDATED_DESIGNATION);
        SpecialityDTO specialityDTO = specialityMapper.toDto(updatedSpeciality);

        restSpecialityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, specialityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);
        Speciality testSpeciality = specialityList.get(specialityList.size() - 1);
        assertThat(testSpeciality.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    void putNonExistingSpeciality() throws Exception {
        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();
        speciality.setId(UUID.randomUUID().toString());

        // Create the Speciality
        SpecialityDTO specialityDTO = specialityMapper.toDto(speciality);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, specialityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSpeciality() throws Exception {
        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();
        speciality.setId(UUID.randomUUID().toString());

        // Create the Speciality
        SpecialityDTO specialityDTO = specialityMapper.toDto(speciality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(specialityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSpeciality() throws Exception {
        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();
        speciality.setId(UUID.randomUUID().toString());

        // Create the Speciality
        SpecialityDTO specialityDTO = specialityMapper.toDto(speciality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(specialityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSpecialityWithPatch() throws Exception {
        // Initialize the database
        specialityRepository.save(speciality);

        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();

        // Update the speciality using partial update
        Speciality partialUpdatedSpeciality = new Speciality();
        partialUpdatedSpeciality.setId(speciality.getId());

        restSpecialityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpeciality.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpeciality))
            )
            .andExpect(status().isOk());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);
        Speciality testSpeciality = specialityList.get(specialityList.size() - 1);
        assertThat(testSpeciality.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    void fullUpdateSpecialityWithPatch() throws Exception {
        // Initialize the database
        specialityRepository.save(speciality);

        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();

        // Update the speciality using partial update
        Speciality partialUpdatedSpeciality = new Speciality();
        partialUpdatedSpeciality.setId(speciality.getId());

        partialUpdatedSpeciality.designation(UPDATED_DESIGNATION);

        restSpecialityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSpeciality.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSpeciality))
            )
            .andExpect(status().isOk());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);
        Speciality testSpeciality = specialityList.get(specialityList.size() - 1);
        assertThat(testSpeciality.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    void patchNonExistingSpeciality() throws Exception {
        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();
        speciality.setId(UUID.randomUUID().toString());

        // Create the Speciality
        SpecialityDTO specialityDTO = specialityMapper.toDto(speciality);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, specialityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSpeciality() throws Exception {
        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();
        speciality.setId(UUID.randomUUID().toString());

        // Create the Speciality
        SpecialityDTO specialityDTO = specialityMapper.toDto(speciality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(specialityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSpeciality() throws Exception {
        int databaseSizeBeforeUpdate = specialityRepository.findAll().size();
        speciality.setId(UUID.randomUUID().toString());

        // Create the Speciality
        SpecialityDTO specialityDTO = specialityMapper.toDto(speciality);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSpecialityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(specialityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Speciality in the database
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSpeciality() throws Exception {
        // Initialize the database
        specialityRepository.save(speciality);

        int databaseSizeBeforeDelete = specialityRepository.findAll().size();

        // Delete the speciality
        restSpecialityMockMvc
            .perform(delete(ENTITY_API_URL_ID, speciality.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Speciality> specialityList = specialityRepository.findAll();
        assertThat(specialityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
