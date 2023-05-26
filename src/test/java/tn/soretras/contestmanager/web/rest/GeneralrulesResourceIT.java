package tn.soretras.contestmanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
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
import tn.soretras.contestmanager.domain.Generalrules;
import tn.soretras.contestmanager.repository.GeneralrulesRepository;
import tn.soretras.contestmanager.service.dto.GeneralrulesDTO;
import tn.soretras.contestmanager.service.mapper.GeneralrulesMapper;

/**
 * Integration tests for the {@link GeneralrulesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GeneralrulesResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_EFFECTDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EFFECTDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/generalrules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GeneralrulesRepository generalrulesRepository;

    @Autowired
    private GeneralrulesMapper generalrulesMapper;

    @Autowired
    private MockMvc restGeneralrulesMockMvc;

    private Generalrules generalrules;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Generalrules createEntity() {
        Generalrules generalrules = new Generalrules().designation(DEFAULT_DESIGNATION).effectdate(DEFAULT_EFFECTDATE);
        return generalrules;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Generalrules createUpdatedEntity() {
        Generalrules generalrules = new Generalrules().designation(UPDATED_DESIGNATION).effectdate(UPDATED_EFFECTDATE);
        return generalrules;
    }

    @BeforeEach
    public void initTest() {
        generalrulesRepository.deleteAll();
        generalrules = createEntity();
    }

    @Test
    void createGeneralrules() throws Exception {
        int databaseSizeBeforeCreate = generalrulesRepository.findAll().size();
        // Create the Generalrules
        GeneralrulesDTO generalrulesDTO = generalrulesMapper.toDto(generalrules);
        restGeneralrulesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generalrulesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Generalrules in the database
        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeCreate + 1);
        Generalrules testGeneralrules = generalrulesList.get(generalrulesList.size() - 1);
        assertThat(testGeneralrules.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testGeneralrules.getEffectdate()).isEqualTo(DEFAULT_EFFECTDATE);
    }

    @Test
    void createGeneralrulesWithExistingId() throws Exception {
        // Create the Generalrules with an existing ID
        generalrules.setId("existing_id");
        GeneralrulesDTO generalrulesDTO = generalrulesMapper.toDto(generalrules);

        int databaseSizeBeforeCreate = generalrulesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeneralrulesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generalrulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Generalrules in the database
        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = generalrulesRepository.findAll().size();
        // set the field null
        generalrules.setDesignation(null);

        // Create the Generalrules, which fails.
        GeneralrulesDTO generalrulesDTO = generalrulesMapper.toDto(generalrules);

        restGeneralrulesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generalrulesDTO))
            )
            .andExpect(status().isBadRequest());

        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkEffectdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = generalrulesRepository.findAll().size();
        // set the field null
        generalrules.setEffectdate(null);

        // Create the Generalrules, which fails.
        GeneralrulesDTO generalrulesDTO = generalrulesMapper.toDto(generalrules);

        restGeneralrulesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generalrulesDTO))
            )
            .andExpect(status().isBadRequest());

        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllGeneralrules() throws Exception {
        // Initialize the database
        generalrulesRepository.save(generalrules);

        // Get all the generalrulesList
        restGeneralrulesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(generalrules.getId())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].effectdate").value(hasItem(DEFAULT_EFFECTDATE.toString())));
    }

    @Test
    void getGeneralrules() throws Exception {
        // Initialize the database
        generalrulesRepository.save(generalrules);

        // Get the generalrules
        restGeneralrulesMockMvc
            .perform(get(ENTITY_API_URL_ID, generalrules.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(generalrules.getId()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.effectdate").value(DEFAULT_EFFECTDATE.toString()));
    }

    @Test
    void getNonExistingGeneralrules() throws Exception {
        // Get the generalrules
        restGeneralrulesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingGeneralrules() throws Exception {
        // Initialize the database
        generalrulesRepository.save(generalrules);

        int databaseSizeBeforeUpdate = generalrulesRepository.findAll().size();

        // Update the generalrules
        Generalrules updatedGeneralrules = generalrulesRepository.findById(generalrules.getId()).get();
        updatedGeneralrules.designation(UPDATED_DESIGNATION).effectdate(UPDATED_EFFECTDATE);
        GeneralrulesDTO generalrulesDTO = generalrulesMapper.toDto(updatedGeneralrules);

        restGeneralrulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, generalrulesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(generalrulesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Generalrules in the database
        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeUpdate);
        Generalrules testGeneralrules = generalrulesList.get(generalrulesList.size() - 1);
        assertThat(testGeneralrules.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testGeneralrules.getEffectdate()).isEqualTo(UPDATED_EFFECTDATE);
    }

    @Test
    void putNonExistingGeneralrules() throws Exception {
        int databaseSizeBeforeUpdate = generalrulesRepository.findAll().size();
        generalrules.setId(UUID.randomUUID().toString());

        // Create the Generalrules
        GeneralrulesDTO generalrulesDTO = generalrulesMapper.toDto(generalrules);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeneralrulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, generalrulesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(generalrulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Generalrules in the database
        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGeneralrules() throws Exception {
        int databaseSizeBeforeUpdate = generalrulesRepository.findAll().size();
        generalrules.setId(UUID.randomUUID().toString());

        // Create the Generalrules
        GeneralrulesDTO generalrulesDTO = generalrulesMapper.toDto(generalrules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralrulesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(generalrulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Generalrules in the database
        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGeneralrules() throws Exception {
        int databaseSizeBeforeUpdate = generalrulesRepository.findAll().size();
        generalrules.setId(UUID.randomUUID().toString());

        // Create the Generalrules
        GeneralrulesDTO generalrulesDTO = generalrulesMapper.toDto(generalrules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralrulesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(generalrulesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Generalrules in the database
        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGeneralrulesWithPatch() throws Exception {
        // Initialize the database
        generalrulesRepository.save(generalrules);

        int databaseSizeBeforeUpdate = generalrulesRepository.findAll().size();

        // Update the generalrules using partial update
        Generalrules partialUpdatedGeneralrules = new Generalrules();
        partialUpdatedGeneralrules.setId(generalrules.getId());

        partialUpdatedGeneralrules.effectdate(UPDATED_EFFECTDATE);

        restGeneralrulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeneralrules.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGeneralrules))
            )
            .andExpect(status().isOk());

        // Validate the Generalrules in the database
        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeUpdate);
        Generalrules testGeneralrules = generalrulesList.get(generalrulesList.size() - 1);
        assertThat(testGeneralrules.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testGeneralrules.getEffectdate()).isEqualTo(UPDATED_EFFECTDATE);
    }

    @Test
    void fullUpdateGeneralrulesWithPatch() throws Exception {
        // Initialize the database
        generalrulesRepository.save(generalrules);

        int databaseSizeBeforeUpdate = generalrulesRepository.findAll().size();

        // Update the generalrules using partial update
        Generalrules partialUpdatedGeneralrules = new Generalrules();
        partialUpdatedGeneralrules.setId(generalrules.getId());

        partialUpdatedGeneralrules.designation(UPDATED_DESIGNATION).effectdate(UPDATED_EFFECTDATE);

        restGeneralrulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGeneralrules.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGeneralrules))
            )
            .andExpect(status().isOk());

        // Validate the Generalrules in the database
        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeUpdate);
        Generalrules testGeneralrules = generalrulesList.get(generalrulesList.size() - 1);
        assertThat(testGeneralrules.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testGeneralrules.getEffectdate()).isEqualTo(UPDATED_EFFECTDATE);
    }

    @Test
    void patchNonExistingGeneralrules() throws Exception {
        int databaseSizeBeforeUpdate = generalrulesRepository.findAll().size();
        generalrules.setId(UUID.randomUUID().toString());

        // Create the Generalrules
        GeneralrulesDTO generalrulesDTO = generalrulesMapper.toDto(generalrules);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeneralrulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, generalrulesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(generalrulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Generalrules in the database
        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGeneralrules() throws Exception {
        int databaseSizeBeforeUpdate = generalrulesRepository.findAll().size();
        generalrules.setId(UUID.randomUUID().toString());

        // Create the Generalrules
        GeneralrulesDTO generalrulesDTO = generalrulesMapper.toDto(generalrules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralrulesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(generalrulesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Generalrules in the database
        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGeneralrules() throws Exception {
        int databaseSizeBeforeUpdate = generalrulesRepository.findAll().size();
        generalrules.setId(UUID.randomUUID().toString());

        // Create the Generalrules
        GeneralrulesDTO generalrulesDTO = generalrulesMapper.toDto(generalrules);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGeneralrulesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(generalrulesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Generalrules in the database
        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGeneralrules() throws Exception {
        // Initialize the database
        generalrulesRepository.save(generalrules);

        int databaseSizeBeforeDelete = generalrulesRepository.findAll().size();

        // Delete the generalrules
        restGeneralrulesMockMvc
            .perform(delete(ENTITY_API_URL_ID, generalrules.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Generalrules> generalrulesList = generalrulesRepository.findAll();
        assertThat(generalrulesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
