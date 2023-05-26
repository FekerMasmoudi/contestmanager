package tn.soretras.contestmanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import tn.soretras.contestmanager.domain.Contest;
import tn.soretras.contestmanager.domain.Contestform;
import tn.soretras.contestmanager.domain.User;
import tn.soretras.contestmanager.repository.ContestformRepository;
import tn.soretras.contestmanager.service.ContestformService;
import tn.soretras.contestmanager.service.dto.ContestformDTO;
import tn.soretras.contestmanager.service.mapper.ContestformMapper;

/**
 * Integration tests for the {@link ContestformResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContestformResourceIT {

    private static final String ENTITY_API_URL = "/api/contestforms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ContestformRepository contestformRepository;

    @Mock
    private ContestformRepository contestformRepositoryMock;

    @Autowired
    private ContestformMapper contestformMapper;

    @Mock
    private ContestformService contestformServiceMock;

    @Autowired
    private MockMvc restContestformMockMvc;

    private Contestform contestform;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contestform createEntity() {
        Contestform contestform = new Contestform();
        // Add required entity
        Contest contest;
        contest = ContestResourceIT.createEntity();
        contest.setId("fixed-id-for-tests");
        contestform.setContest(contest);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        contestform.setUser(user);
        return contestform;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contestform createUpdatedEntity() {
        Contestform contestform = new Contestform();
        // Add required entity
        Contest contest;
        contest = ContestResourceIT.createUpdatedEntity();
        contest.setId("fixed-id-for-tests");
        contestform.setContest(contest);
        // Add required entity
        User user = UserResourceIT.createEntity();
        user.setId("fixed-id-for-tests");
        contestform.setUser(user);
        return contestform;
    }

    @BeforeEach
    public void initTest() {
        contestformRepository.deleteAll();
        contestform = createEntity();
    }

    @Test
    void createContestform() throws Exception {
        int databaseSizeBeforeCreate = contestformRepository.findAll().size();
        // Create the Contestform
        ContestformDTO contestformDTO = contestformMapper.toDto(contestform);
        restContestformMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestformDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Contestform in the database
        List<Contestform> contestformList = contestformRepository.findAll();
        assertThat(contestformList).hasSize(databaseSizeBeforeCreate + 1);
        Contestform testContestform = contestformList.get(contestformList.size() - 1);
    }

    @Test
    void createContestformWithExistingId() throws Exception {
        // Create the Contestform with an existing ID
        contestform.setId("existing_id");
        ContestformDTO contestformDTO = contestformMapper.toDto(contestform);

        int databaseSizeBeforeCreate = contestformRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContestformMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestformDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestform in the database
        List<Contestform> contestformList = contestformRepository.findAll();
        assertThat(contestformList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllContestforms() throws Exception {
        // Initialize the database
        contestformRepository.save(contestform);

        // Get all the contestformList
        restContestformMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contestform.getId())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContestformsWithEagerRelationshipsIsEnabled() throws Exception {
        when(contestformServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContestformMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contestformServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContestformsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contestformServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContestformMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(contestformRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getContestform() throws Exception {
        // Initialize the database
        contestformRepository.save(contestform);

        // Get the contestform
        restContestformMockMvc
            .perform(get(ENTITY_API_URL_ID, contestform.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contestform.getId()));
    }

    @Test
    void getNonExistingContestform() throws Exception {
        // Get the contestform
        restContestformMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingContestform() throws Exception {
        // Initialize the database
        contestformRepository.save(contestform);

        int databaseSizeBeforeUpdate = contestformRepository.findAll().size();

        // Update the contestform
        Contestform updatedContestform = contestformRepository.findById(contestform.getId()).get();
        ContestformDTO contestformDTO = contestformMapper.toDto(updatedContestform);

        restContestformMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contestformDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contestformDTO))
            )
            .andExpect(status().isOk());

        // Validate the Contestform in the database
        List<Contestform> contestformList = contestformRepository.findAll();
        assertThat(contestformList).hasSize(databaseSizeBeforeUpdate);
        Contestform testContestform = contestformList.get(contestformList.size() - 1);
    }

    @Test
    void putNonExistingContestform() throws Exception {
        int databaseSizeBeforeUpdate = contestformRepository.findAll().size();
        contestform.setId(UUID.randomUUID().toString());

        // Create the Contestform
        ContestformDTO contestformDTO = contestformMapper.toDto(contestform);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContestformMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contestformDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contestformDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestform in the database
        List<Contestform> contestformList = contestformRepository.findAll();
        assertThat(contestformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchContestform() throws Exception {
        int databaseSizeBeforeUpdate = contestformRepository.findAll().size();
        contestform.setId(UUID.randomUUID().toString());

        // Create the Contestform
        ContestformDTO contestformDTO = contestformMapper.toDto(contestform);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestformMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contestformDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestform in the database
        List<Contestform> contestformList = contestformRepository.findAll();
        assertThat(contestformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamContestform() throws Exception {
        int databaseSizeBeforeUpdate = contestformRepository.findAll().size();
        contestform.setId(UUID.randomUUID().toString());

        // Create the Contestform
        ContestformDTO contestformDTO = contestformMapper.toDto(contestform);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestformMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contestformDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contestform in the database
        List<Contestform> contestformList = contestformRepository.findAll();
        assertThat(contestformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateContestformWithPatch() throws Exception {
        // Initialize the database
        contestformRepository.save(contestform);

        int databaseSizeBeforeUpdate = contestformRepository.findAll().size();

        // Update the contestform using partial update
        Contestform partialUpdatedContestform = new Contestform();
        partialUpdatedContestform.setId(contestform.getId());

        restContestformMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContestform.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContestform))
            )
            .andExpect(status().isOk());

        // Validate the Contestform in the database
        List<Contestform> contestformList = contestformRepository.findAll();
        assertThat(contestformList).hasSize(databaseSizeBeforeUpdate);
        Contestform testContestform = contestformList.get(contestformList.size() - 1);
    }

    @Test
    void fullUpdateContestformWithPatch() throws Exception {
        // Initialize the database
        contestformRepository.save(contestform);

        int databaseSizeBeforeUpdate = contestformRepository.findAll().size();

        // Update the contestform using partial update
        Contestform partialUpdatedContestform = new Contestform();
        partialUpdatedContestform.setId(contestform.getId());

        restContestformMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContestform.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContestform))
            )
            .andExpect(status().isOk());

        // Validate the Contestform in the database
        List<Contestform> contestformList = contestformRepository.findAll();
        assertThat(contestformList).hasSize(databaseSizeBeforeUpdate);
        Contestform testContestform = contestformList.get(contestformList.size() - 1);
    }

    @Test
    void patchNonExistingContestform() throws Exception {
        int databaseSizeBeforeUpdate = contestformRepository.findAll().size();
        contestform.setId(UUID.randomUUID().toString());

        // Create the Contestform
        ContestformDTO contestformDTO = contestformMapper.toDto(contestform);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContestformMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contestformDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contestformDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestform in the database
        List<Contestform> contestformList = contestformRepository.findAll();
        assertThat(contestformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchContestform() throws Exception {
        int databaseSizeBeforeUpdate = contestformRepository.findAll().size();
        contestform.setId(UUID.randomUUID().toString());

        // Create the Contestform
        ContestformDTO contestformDTO = contestformMapper.toDto(contestform);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestformMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contestformDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Contestform in the database
        List<Contestform> contestformList = contestformRepository.findAll();
        assertThat(contestformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamContestform() throws Exception {
        int databaseSizeBeforeUpdate = contestformRepository.findAll().size();
        contestform.setId(UUID.randomUUID().toString());

        // Create the Contestform
        ContestformDTO contestformDTO = contestformMapper.toDto(contestform);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContestformMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(contestformDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Contestform in the database
        List<Contestform> contestformList = contestformRepository.findAll();
        assertThat(contestformList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteContestform() throws Exception {
        // Initialize the database
        contestformRepository.save(contestform);

        int databaseSizeBeforeDelete = contestformRepository.findAll().size();

        // Delete the contestform
        restContestformMockMvc
            .perform(delete(ENTITY_API_URL_ID, contestform.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contestform> contestformList = contestformRepository.findAll();
        assertThat(contestformList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
