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
import tn.soretras.contestmanager.domain.Contestannounce;
import tn.soretras.contestmanager.domain.Educationlevel;
import tn.soretras.contestmanager.domain.Grade;
import tn.soretras.contestmanager.domain.Sector;
import tn.soretras.contestmanager.domain.Speciality;
import tn.soretras.contestmanager.repository.ContestRepository;
import tn.soretras.contestmanager.service.dto.ContestDTO;
import tn.soretras.contestmanager.service.mapper.ContestMapper;

/**
 * Integration tests for the {@link ContestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContestResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PARENT = "AAAAAAAAAA";
    private static final String UPDATED_PARENT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private ContestMapper contestMapper;

    @Autowired
    private MockMvc restContestMockMvc;

    private Contest contest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contest createEntity() {
        Contest contest = new Contest().name(DEFAULT_NAME).parent(DEFAULT_PARENT).description(DEFAULT_DESCRIPTION);
        // Add required entity
        Contestannounce contestannounce;
        contestannounce = ContestannounceResourceIT.createEntity();
        contestannounce.setId("fixed-id-for-tests");
        contest.setIdcontestannounce(contestannounce);
        // Add required entity
        Grade grade;
        grade = GradeResourceIT.createEntity();
        grade.setId("fixed-id-for-tests");
        contest.setIdgrade(grade);
        // Add required entity
        Speciality speciality;
        speciality = SpecialityResourceIT.createEntity();
        speciality.setId("fixed-id-for-tests");
        contest.setIdspeciality(speciality);
        // Add required entity
        Sector sector;
        sector = SectorResourceIT.createEntity();
        sector.setId("fixed-id-for-tests");
        contest.setIdsector(sector);
        // Add required entity
        Educationlevel educationlevel;
        educationlevel = EducationlevelResourceIT.createEntity();
        educationlevel.setId("fixed-id-for-tests");
        contest.setIdeducationlevel(educationlevel);
        return contest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contest createUpdatedEntity() {
        Contest contest = new Contest().name(UPDATED_NAME).parent(UPDATED_PARENT).description(UPDATED_DESCRIPTION);
        // Add required entity
        Contestannounce contestannounce;
        contestannounce = ContestannounceResourceIT.createUpdatedEntity();
        contestannounce.setId("fixed-id-for-tests");
        contest.setIdcontestannounce(contestannounce);
        // Add required entity
        Grade grade;
        grade = GradeResourceIT.createUpdatedEntity();
        grade.setId("fixed-id-for-tests");
        contest.setIdgrade(grade);
        // Add required entity
        Speciality speciality;
        speciality = SpecialityResourceIT.createUpdatedEntity();
        speciality.setId("fixed-id-for-tests");
        contest.setIdspeciality(speciality);
        // Add required entity
        Sector sector;
        sector = SectorResourceIT.createUpdatedEntity();
        sector.setId("fixed-id-for-tests");
        contest.setIdsector(sector);
        // Add required entity
        Educationlevel educationlevel;
        educationlevel = EducationlevelResourceIT.createUpdatedEntity();
        educationlevel.setId("fixed-id-for-tests");
        contest.setIdeducationlevel(educationlevel);
        return contest;
    }

    @BeforeEach
    public void initTest() {
        contestRepository.deleteAll();
        contest = createEntity();
    }

    @Test
    void createContest() throws Exception {
        int databaseSizeBeforeCreate = contestRepository.findAll().size();
        // Create the Contest
        ContestDTO contestDTO = contestMapper.toDto(contest);
        restContestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestDTO)))
            .andExpect(status().isCreated());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeCreate + 1);
        Contest testContest = contestList.get(contestList.size() - 1);
        assertThat(testContest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContest.getParent()).isEqualTo(DEFAULT_PARENT);
        assertThat(testContest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void createContestWithExistingId() throws Exception {
        // Create the Contest with an existing ID
        contest.setId("existing_id");
        ContestDTO contestDTO = contestMapper.toDto(contest);

        int databaseSizeBeforeCreate = contestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contestRepository.findAll().size();
        // set the field null
        contest.setName(null);

        // Create the Contest, which fails.
        ContestDTO contestDTO = contestMapper.toDto(contest);

        restContestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestDTO)))
            .andExpect(status().isBadRequest());

        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkParentIsRequired() throws Exception {
        int databaseSizeBeforeTest = contestRepository.findAll().size();
        // set the field null
        contest.setParent(null);

        // Create the Contest, which fails.
        ContestDTO contestDTO = contestMapper.toDto(contest);

        restContestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestDTO)))
            .andExpect(status().isBadRequest());

        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = contestRepository.findAll().size();
        // set the field null
        contest.setDescription(null);

        // Create the Contest, which fails.
        ContestDTO contestDTO = contestMapper.toDto(contest);

        restContestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestDTO)))
            .andExpect(status().isBadRequest());

        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllContests() throws Exception {
        // Initialize the database
        contestRepository.save(contest);

        // Get all the contestList
        restContestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contest.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    void getContest() throws Exception {
        // Initialize the database
        contestRepository.save(contest);

        // Get the contest
        restContestMockMvc
            .perform(get(ENTITY_API_URL_ID, contest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contest.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.parent").value(DEFAULT_PARENT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    void getNonExistingContest() throws Exception {
        // Get the contest
        restContestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingContest() throws Exception {
        // Initialize the database
        contestRepository.save(contest);

        int databaseSizeBeforeUpdate = contestRepository.findAll().size();

        // Update the contest
        Contest updatedContest = contestRepository.findById(contest.getId()).get();
        updatedContest.name(UPDATED_NAME).parent(UPDATED_PARENT).description(UPDATED_DESCRIPTION);
        ContestDTO contestDTO = contestMapper.toDto(updatedContest);

        restContestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contestDTO))
            )
            .andExpect(status().isOk());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
        Contest testContest = contestList.get(contestList.size() - 1);
        assertThat(testContest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContest.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testContest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void putNonExistingContest() throws Exception {
        int databaseSizeBeforeUpdate = contestRepository.findAll().size();
        contest.setId(UUID.randomUUID().toString());

        // Create the Contest
        ContestDTO contestDTO = contestMapper.toDto(contest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchContest() throws Exception {
        int databaseSizeBeforeUpdate = contestRepository.findAll().size();
        contest.setId(UUID.randomUUID().toString());

        // Create the Contest
        ContestDTO contestDTO = contestMapper.toDto(contest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamContest() throws Exception {
        int databaseSizeBeforeUpdate = contestRepository.findAll().size();
        contest.setId(UUID.randomUUID().toString());

        // Create the Contest
        ContestDTO contestDTO = contestMapper.toDto(contest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateContestWithPatch() throws Exception {
        // Initialize the database
        contestRepository.save(contest);

        int databaseSizeBeforeUpdate = contestRepository.findAll().size();

        // Update the contest using partial update
        Contest partialUpdatedContest = new Contest();
        partialUpdatedContest.setId(contest.getId());

        partialUpdatedContest.name(UPDATED_NAME).parent(UPDATED_PARENT);

        restContestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContest))
            )
            .andExpect(status().isOk());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
        Contest testContest = contestList.get(contestList.size() - 1);
        assertThat(testContest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContest.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testContest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    void fullUpdateContestWithPatch() throws Exception {
        // Initialize the database
        contestRepository.save(contest);

        int databaseSizeBeforeUpdate = contestRepository.findAll().size();

        // Update the contest using partial update
        Contest partialUpdatedContest = new Contest();
        partialUpdatedContest.setId(contest.getId());

        partialUpdatedContest.name(UPDATED_NAME).parent(UPDATED_PARENT).description(UPDATED_DESCRIPTION);

        restContestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContest))
            )
            .andExpect(status().isOk());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
        Contest testContest = contestList.get(contestList.size() - 1);
        assertThat(testContest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContest.getParent()).isEqualTo(UPDATED_PARENT);
        assertThat(testContest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    void patchNonExistingContest() throws Exception {
        int databaseSizeBeforeUpdate = contestRepository.findAll().size();
        contest.setId(UUID.randomUUID().toString());

        // Create the Contest
        ContestDTO contestDTO = contestMapper.toDto(contest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchContest() throws Exception {
        int databaseSizeBeforeUpdate = contestRepository.findAll().size();
        contest.setId(UUID.randomUUID().toString());

        // Create the Contest
        ContestDTO contestDTO = contestMapper.toDto(contest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamContest() throws Exception {
        int databaseSizeBeforeUpdate = contestRepository.findAll().size();
        contest.setId(UUID.randomUUID().toString());

        // Create the Contest
        ContestDTO contestDTO = contestMapper.toDto(contest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contest in the database
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteContest() throws Exception {
        // Initialize the database
        contestRepository.save(contest);

        int databaseSizeBeforeDelete = contestRepository.findAll().size();

        // Delete the contest
        restContestMockMvc
            .perform(delete(ENTITY_API_URL_ID, contest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contest> contestList = contestRepository.findAll();
        assertThat(contestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
