package tn.soretras.contestmanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import tn.soretras.contestmanager.IntegrationTest;
import tn.soretras.contestmanager.domain.Contestannounce;
import tn.soretras.contestmanager.domain.Generalrules;
import tn.soretras.contestmanager.repository.ContestannounceRepository;
import tn.soretras.contestmanager.service.ContestannounceService;
import tn.soretras.contestmanager.service.dto.ContestannounceDTO;
import tn.soretras.contestmanager.service.mapper.ContestannounceMapper;

/**
 * Integration tests for the {@link ContestannounceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContestannounceResourceIT {

    private static final String DEFAULT_ANNOUNCE_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_ANNOUNCE_TEXT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_STARTDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_STARTDATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LIMITDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LIMITDATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/contestannounces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ContestannounceRepository contestannounceRepository;

    @Mock
    private ContestannounceRepository contestannounceRepositoryMock;

    @Autowired
    private ContestannounceMapper contestannounceMapper;

    @Mock
    private ContestannounceService contestannounceServiceMock;

    @Autowired
    private MockMvc restContestannounceMockMvc;

    private Contestannounce contestannounce;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contestannounce createEntity() {
        Contestannounce contestannounce = new Contestannounce()
            .announceText(DEFAULT_ANNOUNCE_TEXT)
            .startdate(DEFAULT_STARTDATE)
            .limitdate(DEFAULT_LIMITDATE)
            .status(DEFAULT_STATUS);
        // Add required entity
        Generalrules generalrules;
        generalrules = GeneralrulesResourceIT.createEntity();
        generalrules.setId("fixed-id-for-tests");
        contestannounce.getIdsgeneralrules().add(generalrules);
        return contestannounce;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contestannounce createUpdatedEntity() {
        Contestannounce contestannounce = new Contestannounce()
            .announceText(UPDATED_ANNOUNCE_TEXT)
            .startdate(UPDATED_STARTDATE)
            .limitdate(UPDATED_LIMITDATE)
            .status(UPDATED_STATUS);
        // Add required entity
        Generalrules generalrules;
        generalrules = GeneralrulesResourceIT.createUpdatedEntity();
        generalrules.setId("fixed-id-for-tests");
        contestannounce.getIdsgeneralrules().add(generalrules);
        return contestannounce;
    }

    @BeforeEach
    public void initTest() {
        contestannounceRepository.deleteAll();
        contestannounce = createEntity();
    }

    @Test
    void createContestannounce() throws Exception {
        int databaseSizeBeforeCreate = contestannounceRepository.findAll().size();
        // Create the Contestannounce
        ContestannounceDTO contestannounceDTO = contestannounceMapper.toDto(contestannounce);
        restContestannounceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestannounceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Contestannounce in the database
        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeCreate + 1);
        Contestannounce testContestannounce = contestannounceList.get(contestannounceList.size() - 1);
        assertThat(testContestannounce.getAnnounceText()).isEqualTo(DEFAULT_ANNOUNCE_TEXT);
        assertThat(testContestannounce.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testContestannounce.getLimitdate()).isEqualTo(DEFAULT_LIMITDATE);
        assertThat(testContestannounce.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    void createContestannounceWithExistingId() throws Exception {
        // Create the Contestannounce with an existing ID
        contestannounce.setId("existing_id");
        ContestannounceDTO contestannounceDTO = contestannounceMapper.toDto(contestannounce);

        int databaseSizeBeforeCreate = contestannounceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContestannounceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestannounceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestannounce in the database
        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkAnnounceTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = contestannounceRepository.findAll().size();
        // set the field null
        contestannounce.setAnnounceText(null);

        // Create the Contestannounce, which fails.
        ContestannounceDTO contestannounceDTO = contestannounceMapper.toDto(contestannounce);

        restContestannounceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestannounceDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStartdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contestannounceRepository.findAll().size();
        // set the field null
        contestannounce.setStartdate(null);

        // Create the Contestannounce, which fails.
        ContestannounceDTO contestannounceDTO = contestannounceMapper.toDto(contestannounce);

        restContestannounceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestannounceDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkLimitdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = contestannounceRepository.findAll().size();
        // set the field null
        contestannounce.setLimitdate(null);

        // Create the Contestannounce, which fails.
        ContestannounceDTO contestannounceDTO = contestannounceMapper.toDto(contestannounce);

        restContestannounceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestannounceDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = contestannounceRepository.findAll().size();
        // set the field null
        contestannounce.setStatus(null);

        // Create the Contestannounce, which fails.
        ContestannounceDTO contestannounceDTO = contestannounceMapper.toDto(contestannounce);

        restContestannounceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestannounceDTO))
            )
            .andExpect(status().isBadRequest());

        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllContestannounces() throws Exception {
        // Initialize the database
        contestannounceRepository.save(contestannounce);

        // Get all the contestannounceList
        restContestannounceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contestannounce.getId())))
            .andExpect(jsonPath("$.[*].announceText").value(hasItem(DEFAULT_ANNOUNCE_TEXT)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].limitdate").value(hasItem(DEFAULT_LIMITDATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContestannouncesWithEagerRelationshipsIsEnabled() throws Exception {
        when(contestannounceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContestannounceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contestannounceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContestannouncesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contestannounceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContestannounceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(contestannounceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getContestannounce() throws Exception {
        // Initialize the database
        contestannounceRepository.save(contestannounce);

        // Get the contestannounce
        restContestannounceMockMvc
            .perform(get(ENTITY_API_URL_ID, contestannounce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contestannounce.getId()))
            .andExpect(jsonPath("$.announceText").value(DEFAULT_ANNOUNCE_TEXT))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.limitdate").value(DEFAULT_LIMITDATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    void getNonExistingContestannounce() throws Exception {
        // Get the contestannounce
        restContestannounceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingContestannounce() throws Exception {
        // Initialize the database
        contestannounceRepository.save(contestannounce);

        int databaseSizeBeforeUpdate = contestannounceRepository.findAll().size();

        // Update the contestannounce
        Contestannounce updatedContestannounce = contestannounceRepository.findById(contestannounce.getId()).get();
        updatedContestannounce
            .announceText(UPDATED_ANNOUNCE_TEXT)
            .startdate(UPDATED_STARTDATE)
            .limitdate(UPDATED_LIMITDATE)
            .status(UPDATED_STATUS);
        ContestannounceDTO contestannounceDTO = contestannounceMapper.toDto(updatedContestannounce);

        restContestannounceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contestannounceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contestannounceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Contestannounce in the database
        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeUpdate);
        Contestannounce testContestannounce = contestannounceList.get(contestannounceList.size() - 1);
        assertThat(testContestannounce.getAnnounceText()).isEqualTo(UPDATED_ANNOUNCE_TEXT);
        assertThat(testContestannounce.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testContestannounce.getLimitdate()).isEqualTo(UPDATED_LIMITDATE);
        assertThat(testContestannounce.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    void putNonExistingContestannounce() throws Exception {
        int databaseSizeBeforeUpdate = contestannounceRepository.findAll().size();
        contestannounce.setId(UUID.randomUUID().toString());

        // Create the Contestannounce
        ContestannounceDTO contestannounceDTO = contestannounceMapper.toDto(contestannounce);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContestannounceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contestannounceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contestannounceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestannounce in the database
        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchContestannounce() throws Exception {
        int databaseSizeBeforeUpdate = contestannounceRepository.findAll().size();
        contestannounce.setId(UUID.randomUUID().toString());

        // Create the Contestannounce
        ContestannounceDTO contestannounceDTO = contestannounceMapper.toDto(contestannounce);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestannounceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contestannounceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestannounce in the database
        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamContestannounce() throws Exception {
        int databaseSizeBeforeUpdate = contestannounceRepository.findAll().size();
        contestannounce.setId(UUID.randomUUID().toString());

        // Create the Contestannounce
        ContestannounceDTO contestannounceDTO = contestannounceMapper.toDto(contestannounce);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestannounceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestannounceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contestannounce in the database
        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateContestannounceWithPatch() throws Exception {
        // Initialize the database
        contestannounceRepository.save(contestannounce);

        int databaseSizeBeforeUpdate = contestannounceRepository.findAll().size();

        // Update the contestannounce using partial update
        Contestannounce partialUpdatedContestannounce = new Contestannounce();
        partialUpdatedContestannounce.setId(contestannounce.getId());

        partialUpdatedContestannounce.startdate(UPDATED_STARTDATE);

        restContestannounceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContestannounce.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContestannounce))
            )
            .andExpect(status().isOk());

        // Validate the Contestannounce in the database
        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeUpdate);
        Contestannounce testContestannounce = contestannounceList.get(contestannounceList.size() - 1);
        assertThat(testContestannounce.getAnnounceText()).isEqualTo(DEFAULT_ANNOUNCE_TEXT);
        assertThat(testContestannounce.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testContestannounce.getLimitdate()).isEqualTo(DEFAULT_LIMITDATE);
        assertThat(testContestannounce.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    void fullUpdateContestannounceWithPatch() throws Exception {
        // Initialize the database
        contestannounceRepository.save(contestannounce);

        int databaseSizeBeforeUpdate = contestannounceRepository.findAll().size();

        // Update the contestannounce using partial update
        Contestannounce partialUpdatedContestannounce = new Contestannounce();
        partialUpdatedContestannounce.setId(contestannounce.getId());

        partialUpdatedContestannounce
            .announceText(UPDATED_ANNOUNCE_TEXT)
            .startdate(UPDATED_STARTDATE)
            .limitdate(UPDATED_LIMITDATE)
            .status(UPDATED_STATUS);

        restContestannounceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContestannounce.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContestannounce))
            )
            .andExpect(status().isOk());

        // Validate the Contestannounce in the database
        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeUpdate);
        Contestannounce testContestannounce = contestannounceList.get(contestannounceList.size() - 1);
        assertThat(testContestannounce.getAnnounceText()).isEqualTo(UPDATED_ANNOUNCE_TEXT);
        assertThat(testContestannounce.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testContestannounce.getLimitdate()).isEqualTo(UPDATED_LIMITDATE);
        assertThat(testContestannounce.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    void patchNonExistingContestannounce() throws Exception {
        int databaseSizeBeforeUpdate = contestannounceRepository.findAll().size();
        contestannounce.setId(UUID.randomUUID().toString());

        // Create the Contestannounce
        ContestannounceDTO contestannounceDTO = contestannounceMapper.toDto(contestannounce);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContestannounceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contestannounceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contestannounceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestannounce in the database
        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchContestannounce() throws Exception {
        int databaseSizeBeforeUpdate = contestannounceRepository.findAll().size();
        contestannounce.setId(UUID.randomUUID().toString());

        // Create the Contestannounce
        ContestannounceDTO contestannounceDTO = contestannounceMapper.toDto(contestannounce);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestannounceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contestannounceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestannounce in the database
        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamContestannounce() throws Exception {
        int databaseSizeBeforeUpdate = contestannounceRepository.findAll().size();
        contestannounce.setId(UUID.randomUUID().toString());

        // Create the Contestannounce
        ContestannounceDTO contestannounceDTO = contestannounceMapper.toDto(contestannounce);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestannounceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contestannounceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contestannounce in the database
        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteContestannounce() throws Exception {
        // Initialize the database
        contestannounceRepository.save(contestannounce);

        int databaseSizeBeforeDelete = contestannounceRepository.findAll().size();

        // Delete the contestannounce
        restContestannounceMockMvc
            .perform(delete(ENTITY_API_URL_ID, contestannounce.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contestannounce> contestannounceList = contestannounceRepository.findAll();
        assertThat(contestannounceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
