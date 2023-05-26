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
import tn.soretras.contestmanager.domain.Contest;
import tn.soretras.contestmanager.domain.Contestfield;
import tn.soretras.contestmanager.repository.ContestfieldRepository;
import tn.soretras.contestmanager.service.dto.ContestfieldDTO;
import tn.soretras.contestmanager.service.mapper.ContestfieldMapper;

/**
 * Integration tests for the {@link ContestfieldResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContestfieldResourceIT {

    private static final Integer DEFAULT_REFERENCE = 1;
    private static final Integer UPDATED_REFERENCE = 2;

    private static final Boolean DEFAULT_MANDATORY = false;
    private static final Boolean UPDATED_MANDATORY = true;

    private static final String DEFAULT_FOPCONSTRAINT = "AAAAAAAAAA";
    private static final String UPDATED_FOPCONSTRAINT = "BBBBBBBBBB";

    private static final String DEFAULT_FVALUE = "AAAAAAAAAA";
    private static final String UPDATED_FVALUE = "BBBBBBBBBB";

    private static final String DEFAULT_SOPCONSTRAINT = "AAAAAAAAAA";
    private static final String UPDATED_SOPCONSTRAINT = "BBBBBBBBBB";

    private static final String DEFAULT_SVALUE = "AAAAAAAAAA";
    private static final String UPDATED_SVALUE = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIC = "AAAAAAAAAA";
    private static final String UPDATED_LOGIC = "BBBBBBBBBB";

    private static final String DEFAULT_CTYPE = "AAAAAAAAAA";
    private static final String UPDATED_CTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CNAME = "AAAAAAAAAA";
    private static final String UPDATED_CNAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contestfields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ContestfieldRepository contestfieldRepository;

    @Autowired
    private ContestfieldMapper contestfieldMapper;

    @Autowired
    private MockMvc restContestfieldMockMvc;

    private Contestfield contestfield;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contestfield createEntity() {
        Contestfield contestfield = new Contestfield()
            .reference(DEFAULT_REFERENCE)
            .mandatory(DEFAULT_MANDATORY)
            .fopconstraint(DEFAULT_FOPCONSTRAINT)
            .fvalue(DEFAULT_FVALUE)
            .sopconstraint(DEFAULT_SOPCONSTRAINT)
            .svalue(DEFAULT_SVALUE)
            .logic(DEFAULT_LOGIC)
            .ctype(DEFAULT_CTYPE)
            .cname(DEFAULT_CNAME);
        // Add required entity
        Contest contest;
        contest = ContestResourceIT.createEntity();
        contest.setId("fixed-id-for-tests");
        contestfield.setIdcontest(contest);
        return contestfield;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contestfield createUpdatedEntity() {
        Contestfield contestfield = new Contestfield()
            .reference(UPDATED_REFERENCE)
            .mandatory(UPDATED_MANDATORY)
            .fopconstraint(UPDATED_FOPCONSTRAINT)
            .fvalue(UPDATED_FVALUE)
            .sopconstraint(UPDATED_SOPCONSTRAINT)
            .svalue(UPDATED_SVALUE)
            .logic(UPDATED_LOGIC)
            .ctype(UPDATED_CTYPE)
            .cname(UPDATED_CNAME);
        // Add required entity
        Contest contest;
        contest = ContestResourceIT.createUpdatedEntity();
        contest.setId("fixed-id-for-tests");
        contestfield.setIdcontest(contest);
        return contestfield;
    }

    @BeforeEach
    public void initTest() {
        contestfieldRepository.deleteAll();
        contestfield = createEntity();
    }

    @Test
    void createContestfield() throws Exception {
        int databaseSizeBeforeCreate = contestfieldRepository.findAll().size();
        // Create the Contestfield
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(contestfield);
        restContestfieldMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Contestfield in the database
        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeCreate + 1);
        Contestfield testContestfield = contestfieldList.get(contestfieldList.size() - 1);
        assertThat(testContestfield.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testContestfield.getMandatory()).isEqualTo(DEFAULT_MANDATORY);
        assertThat(testContestfield.getFopconstraint()).isEqualTo(DEFAULT_FOPCONSTRAINT);
        assertThat(testContestfield.getFvalue()).isEqualTo(DEFAULT_FVALUE);
        assertThat(testContestfield.getSopconstraint()).isEqualTo(DEFAULT_SOPCONSTRAINT);
        assertThat(testContestfield.getSvalue()).isEqualTo(DEFAULT_SVALUE);
        assertThat(testContestfield.getLogic()).isEqualTo(DEFAULT_LOGIC);
        assertThat(testContestfield.getCtype()).isEqualTo(DEFAULT_CTYPE);
        assertThat(testContestfield.getCname()).isEqualTo(DEFAULT_CNAME);
    }

    @Test
    void createContestfieldWithExistingId() throws Exception {
        // Create the Contestfield with an existing ID
        contestfield.setId("existing_id");
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(contestfield);

        int databaseSizeBeforeCreate = contestfieldRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContestfieldMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestfield in the database
        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = contestfieldRepository.findAll().size();
        // set the field null
        contestfield.setReference(null);

        // Create the Contestfield, which fails.
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(contestfield);

        restContestfieldMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkFopconstraintIsRequired() throws Exception {
        int databaseSizeBeforeTest = contestfieldRepository.findAll().size();
        // set the field null
        contestfield.setFopconstraint(null);

        // Create the Contestfield, which fails.
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(contestfield);

        restContestfieldMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkFvalueIsRequired() throws Exception {
        int databaseSizeBeforeTest = contestfieldRepository.findAll().size();
        // set the field null
        contestfield.setFvalue(null);

        // Create the Contestfield, which fails.
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(contestfield);

        restContestfieldMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCtypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contestfieldRepository.findAll().size();
        // set the field null
        contestfield.setCtype(null);

        // Create the Contestfield, which fails.
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(contestfield);

        restContestfieldMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contestfieldRepository.findAll().size();
        // set the field null
        contestfield.setCname(null);

        // Create the Contestfield, which fails.
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(contestfield);

        restContestfieldMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllContestfields() throws Exception {
        // Initialize the database
        contestfieldRepository.save(contestfield);

        // Get all the contestfieldList
        restContestfieldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contestfield.getId())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].mandatory").value(hasItem(DEFAULT_MANDATORY.booleanValue())))
            .andExpect(jsonPath("$.[*].fopconstraint").value(hasItem(DEFAULT_FOPCONSTRAINT)))
            .andExpect(jsonPath("$.[*].fvalue").value(hasItem(DEFAULT_FVALUE)))
            .andExpect(jsonPath("$.[*].sopconstraint").value(hasItem(DEFAULT_SOPCONSTRAINT)))
            .andExpect(jsonPath("$.[*].svalue").value(hasItem(DEFAULT_SVALUE)))
            .andExpect(jsonPath("$.[*].logic").value(hasItem(DEFAULT_LOGIC)))
            .andExpect(jsonPath("$.[*].ctype").value(hasItem(DEFAULT_CTYPE)))
            .andExpect(jsonPath("$.[*].cname").value(hasItem(DEFAULT_CNAME)));
    }

    @Test
    void getContestfield() throws Exception {
        // Initialize the database
        contestfieldRepository.save(contestfield);

        // Get the contestfield
        restContestfieldMockMvc
            .perform(get(ENTITY_API_URL_ID, contestfield.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contestfield.getId()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.mandatory").value(DEFAULT_MANDATORY.booleanValue()))
            .andExpect(jsonPath("$.fopconstraint").value(DEFAULT_FOPCONSTRAINT))
            .andExpect(jsonPath("$.fvalue").value(DEFAULT_FVALUE))
            .andExpect(jsonPath("$.sopconstraint").value(DEFAULT_SOPCONSTRAINT))
            .andExpect(jsonPath("$.svalue").value(DEFAULT_SVALUE))
            .andExpect(jsonPath("$.logic").value(DEFAULT_LOGIC))
            .andExpect(jsonPath("$.ctype").value(DEFAULT_CTYPE))
            .andExpect(jsonPath("$.cname").value(DEFAULT_CNAME));
    }

    @Test
    void getNonExistingContestfield() throws Exception {
        // Get the contestfield
        restContestfieldMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingContestfield() throws Exception {
        // Initialize the database
        contestfieldRepository.save(contestfield);

        int databaseSizeBeforeUpdate = contestfieldRepository.findAll().size();

        // Update the contestfield
        Contestfield updatedContestfield = contestfieldRepository.findById(contestfield.getId()).get();
        updatedContestfield
            .reference(UPDATED_REFERENCE)
            .mandatory(UPDATED_MANDATORY)
            .fopconstraint(UPDATED_FOPCONSTRAINT)
            .fvalue(UPDATED_FVALUE)
            .sopconstraint(UPDATED_SOPCONSTRAINT)
            .svalue(UPDATED_SVALUE)
            .logic(UPDATED_LOGIC)
            .ctype(UPDATED_CTYPE)
            .cname(UPDATED_CNAME);
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(updatedContestfield);

        restContestfieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contestfieldDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isOk());

        // Validate the Contestfield in the database
        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeUpdate);
        Contestfield testContestfield = contestfieldList.get(contestfieldList.size() - 1);
        assertThat(testContestfield.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testContestfield.getMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testContestfield.getFopconstraint()).isEqualTo(UPDATED_FOPCONSTRAINT);
        assertThat(testContestfield.getFvalue()).isEqualTo(UPDATED_FVALUE);
        assertThat(testContestfield.getSopconstraint()).isEqualTo(UPDATED_SOPCONSTRAINT);
        assertThat(testContestfield.getSvalue()).isEqualTo(UPDATED_SVALUE);
        assertThat(testContestfield.getLogic()).isEqualTo(UPDATED_LOGIC);
        assertThat(testContestfield.getCtype()).isEqualTo(UPDATED_CTYPE);
        assertThat(testContestfield.getCname()).isEqualTo(UPDATED_CNAME);
    }

    @Test
    void putNonExistingContestfield() throws Exception {
        int databaseSizeBeforeUpdate = contestfieldRepository.findAll().size();
        contestfield.setId(UUID.randomUUID().toString());

        // Create the Contestfield
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(contestfield);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContestfieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contestfieldDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestfield in the database
        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchContestfield() throws Exception {
        int databaseSizeBeforeUpdate = contestfieldRepository.findAll().size();
        contestfield.setId(UUID.randomUUID().toString());

        // Create the Contestfield
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(contestfield);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestfieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestfield in the database
        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamContestfield() throws Exception {
        int databaseSizeBeforeUpdate = contestfieldRepository.findAll().size();
        contestfield.setId(UUID.randomUUID().toString());

        // Create the Contestfield
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(contestfield);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestfieldMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contestfield in the database
        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateContestfieldWithPatch() throws Exception {
        // Initialize the database
        contestfieldRepository.save(contestfield);

        int databaseSizeBeforeUpdate = contestfieldRepository.findAll().size();

        // Update the contestfield using partial update
        Contestfield partialUpdatedContestfield = new Contestfield();
        partialUpdatedContestfield.setId(contestfield.getId());

        partialUpdatedContestfield.mandatory(UPDATED_MANDATORY).fopconstraint(UPDATED_FOPCONSTRAINT).sopconstraint(UPDATED_SOPCONSTRAINT);

        restContestfieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContestfield.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContestfield))
            )
            .andExpect(status().isOk());

        // Validate the Contestfield in the database
        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeUpdate);
        Contestfield testContestfield = contestfieldList.get(contestfieldList.size() - 1);
        assertThat(testContestfield.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testContestfield.getMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testContestfield.getFopconstraint()).isEqualTo(UPDATED_FOPCONSTRAINT);
        assertThat(testContestfield.getFvalue()).isEqualTo(DEFAULT_FVALUE);
        assertThat(testContestfield.getSopconstraint()).isEqualTo(UPDATED_SOPCONSTRAINT);
        assertThat(testContestfield.getSvalue()).isEqualTo(DEFAULT_SVALUE);
        assertThat(testContestfield.getLogic()).isEqualTo(DEFAULT_LOGIC);
        assertThat(testContestfield.getCtype()).isEqualTo(DEFAULT_CTYPE);
        assertThat(testContestfield.getCname()).isEqualTo(DEFAULT_CNAME);
    }

    @Test
    void fullUpdateContestfieldWithPatch() throws Exception {
        // Initialize the database
        contestfieldRepository.save(contestfield);

        int databaseSizeBeforeUpdate = contestfieldRepository.findAll().size();

        // Update the contestfield using partial update
        Contestfield partialUpdatedContestfield = new Contestfield();
        partialUpdatedContestfield.setId(contestfield.getId());

        partialUpdatedContestfield
            .reference(UPDATED_REFERENCE)
            .mandatory(UPDATED_MANDATORY)
            .fopconstraint(UPDATED_FOPCONSTRAINT)
            .fvalue(UPDATED_FVALUE)
            .sopconstraint(UPDATED_SOPCONSTRAINT)
            .svalue(UPDATED_SVALUE)
            .logic(UPDATED_LOGIC)
            .ctype(UPDATED_CTYPE)
            .cname(UPDATED_CNAME);

        restContestfieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContestfield.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContestfield))
            )
            .andExpect(status().isOk());

        // Validate the Contestfield in the database
        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeUpdate);
        Contestfield testContestfield = contestfieldList.get(contestfieldList.size() - 1);
        assertThat(testContestfield.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testContestfield.getMandatory()).isEqualTo(UPDATED_MANDATORY);
        assertThat(testContestfield.getFopconstraint()).isEqualTo(UPDATED_FOPCONSTRAINT);
        assertThat(testContestfield.getFvalue()).isEqualTo(UPDATED_FVALUE);
        assertThat(testContestfield.getSopconstraint()).isEqualTo(UPDATED_SOPCONSTRAINT);
        assertThat(testContestfield.getSvalue()).isEqualTo(UPDATED_SVALUE);
        assertThat(testContestfield.getLogic()).isEqualTo(UPDATED_LOGIC);
        assertThat(testContestfield.getCtype()).isEqualTo(UPDATED_CTYPE);
        assertThat(testContestfield.getCname()).isEqualTo(UPDATED_CNAME);
    }

    @Test
    void patchNonExistingContestfield() throws Exception {
        int databaseSizeBeforeUpdate = contestfieldRepository.findAll().size();
        contestfield.setId(UUID.randomUUID().toString());

        // Create the Contestfield
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(contestfield);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContestfieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contestfieldDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestfield in the database
        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchContestfield() throws Exception {
        int databaseSizeBeforeUpdate = contestfieldRepository.findAll().size();
        contestfield.setId(UUID.randomUUID().toString());

        // Create the Contestfield
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(contestfield);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestfieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestfield in the database
        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamContestfield() throws Exception {
        int databaseSizeBeforeUpdate = contestfieldRepository.findAll().size();
        contestfield.setId(UUID.randomUUID().toString());

        // Create the Contestfield
        ContestfieldDTO contestfieldDTO = contestfieldMapper.toDto(contestfield);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestfieldMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contestfieldDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contestfield in the database
        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteContestfield() throws Exception {
        // Initialize the database
        contestfieldRepository.save(contestfield);

        int databaseSizeBeforeDelete = contestfieldRepository.findAll().size();

        // Delete the contestfield
        restContestfieldMockMvc
            .perform(delete(ENTITY_API_URL_ID, contestfield.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contestfield> contestfieldList = contestfieldRepository.findAll();
        assertThat(contestfieldList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
