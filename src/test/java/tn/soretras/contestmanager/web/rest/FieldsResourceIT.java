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
import tn.soretras.contestmanager.domain.Contestform;
import tn.soretras.contestmanager.domain.Fields;
import tn.soretras.contestmanager.domain.enumeration.etype;
import tn.soretras.contestmanager.repository.FieldsRepository;
import tn.soretras.contestmanager.service.dto.FieldsDTO;
import tn.soretras.contestmanager.service.mapper.FieldsMapper;

/**
 * Integration tests for the {@link FieldsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final etype DEFAULT_FTYPE = etype.STRING;
    private static final etype UPDATED_FTYPE = etype.INT;

    private static final String DEFAULT_FVALUE = "AAAAAAAAAA";
    private static final String UPDATED_FVALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FieldsRepository fieldsRepository;

    @Autowired
    private FieldsMapper fieldsMapper;

    @Autowired
    private MockMvc restFieldsMockMvc;

    private Fields fields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fields createEntity() {
        Fields fields = new Fields().name(DEFAULT_NAME).ftype(DEFAULT_FTYPE).fvalue(DEFAULT_FVALUE);
        // Add required entity
        Contestform contestform;
        contestform = ContestformResourceIT.createEntity();
        contestform.setId("fixed-id-for-tests");
        fields.setContestform(contestform);
        return fields;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fields createUpdatedEntity() {
        Fields fields = new Fields().name(UPDATED_NAME).ftype(UPDATED_FTYPE).fvalue(UPDATED_FVALUE);
        // Add required entity
        Contestform contestform;
        contestform = ContestformResourceIT.createUpdatedEntity();
        contestform.setId("fixed-id-for-tests");
        fields.setContestform(contestform);
        return fields;
    }

    @BeforeEach
    public void initTest() {
        fieldsRepository.deleteAll();
        fields = createEntity();
    }

    @Test
    void createFields() throws Exception {
        int databaseSizeBeforeCreate = fieldsRepository.findAll().size();
        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);
        restFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isCreated());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeCreate + 1);
        Fields testFields = fieldsList.get(fieldsList.size() - 1);
        assertThat(testFields.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFields.getFtype()).isEqualTo(DEFAULT_FTYPE);
        assertThat(testFields.getFvalue()).isEqualTo(DEFAULT_FVALUE);
    }

    @Test
    void createFieldsWithExistingId() throws Exception {
        // Create the Fields with an existing ID
        fields.setId("existing_id");
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        int databaseSizeBeforeCreate = fieldsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setName(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkFtypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setFtype(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllFields() throws Exception {
        // Initialize the database
        fieldsRepository.save(fields);

        // Get all the fieldsList
        restFieldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fields.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ftype").value(hasItem(DEFAULT_FTYPE.toString())))
            .andExpect(jsonPath("$.[*].fvalue").value(hasItem(DEFAULT_FVALUE)));
    }

    @Test
    void getFields() throws Exception {
        // Initialize the database
        fieldsRepository.save(fields);

        // Get the fields
        restFieldsMockMvc
            .perform(get(ENTITY_API_URL_ID, fields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fields.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.ftype").value(DEFAULT_FTYPE.toString()))
            .andExpect(jsonPath("$.fvalue").value(DEFAULT_FVALUE));
    }

    @Test
    void getNonExistingFields() throws Exception {
        // Get the fields
        restFieldsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingFields() throws Exception {
        // Initialize the database
        fieldsRepository.save(fields);

        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();

        // Update the fields
        Fields updatedFields = fieldsRepository.findById(fields.getId()).get();
        updatedFields.name(UPDATED_NAME).ftype(UPDATED_FTYPE).fvalue(UPDATED_FVALUE);
        FieldsDTO fieldsDTO = fieldsMapper.toDto(updatedFields);

        restFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
        Fields testFields = fieldsList.get(fieldsList.size() - 1);
        assertThat(testFields.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFields.getFtype()).isEqualTo(UPDATED_FTYPE);
        assertThat(testFields.getFvalue()).isEqualTo(UPDATED_FVALUE);
    }

    @Test
    void putNonExistingFields() throws Exception {
        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();
        fields.setId(UUID.randomUUID().toString());

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFields() throws Exception {
        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();
        fields.setId(UUID.randomUUID().toString());

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFields() throws Exception {
        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();
        fields.setId(UUID.randomUUID().toString());

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFieldsWithPatch() throws Exception {
        // Initialize the database
        fieldsRepository.save(fields);

        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();

        // Update the fields using partial update
        Fields partialUpdatedFields = new Fields();
        partialUpdatedFields.setId(fields.getId());

        partialUpdatedFields.name(UPDATED_NAME);

        restFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFields))
            )
            .andExpect(status().isOk());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
        Fields testFields = fieldsList.get(fieldsList.size() - 1);
        assertThat(testFields.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFields.getFtype()).isEqualTo(DEFAULT_FTYPE);
        assertThat(testFields.getFvalue()).isEqualTo(DEFAULT_FVALUE);
    }

    @Test
    void fullUpdateFieldsWithPatch() throws Exception {
        // Initialize the database
        fieldsRepository.save(fields);

        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();

        // Update the fields using partial update
        Fields partialUpdatedFields = new Fields();
        partialUpdatedFields.setId(fields.getId());

        partialUpdatedFields.name(UPDATED_NAME).ftype(UPDATED_FTYPE).fvalue(UPDATED_FVALUE);

        restFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFields))
            )
            .andExpect(status().isOk());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
        Fields testFields = fieldsList.get(fieldsList.size() - 1);
        assertThat(testFields.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFields.getFtype()).isEqualTo(UPDATED_FTYPE);
        assertThat(testFields.getFvalue()).isEqualTo(UPDATED_FVALUE);
    }

    @Test
    void patchNonExistingFields() throws Exception {
        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();
        fields.setId(UUID.randomUUID().toString());

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFields() throws Exception {
        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();
        fields.setId(UUID.randomUUID().toString());

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFields() throws Exception {
        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();
        fields.setId(UUID.randomUUID().toString());

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fieldsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFields() throws Exception {
        // Initialize the database
        fieldsRepository.save(fields);

        int databaseSizeBeforeDelete = fieldsRepository.findAll().size();

        // Delete the fields
        restFieldsMockMvc
            .perform(delete(ENTITY_API_URL_ID, fields.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
