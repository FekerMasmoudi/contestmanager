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
import tn.soretras.contestmanager.domain.Sector;
import tn.soretras.contestmanager.repository.SectorRepository;
import tn.soretras.contestmanager.service.dto.SectorDTO;
import tn.soretras.contestmanager.service.mapper.SectorMapper;

/**
 * Integration tests for the {@link SectorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SectorResourceIT {

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sectors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private SectorMapper sectorMapper;

    @Autowired
    private MockMvc restSectorMockMvc;

    private Sector sector;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sector createEntity() {
        Sector sector = new Sector().designation(DEFAULT_DESIGNATION);
        return sector;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sector createUpdatedEntity() {
        Sector sector = new Sector().designation(UPDATED_DESIGNATION);
        return sector;
    }

    @BeforeEach
    public void initTest() {
        sectorRepository.deleteAll();
        sector = createEntity();
    }

    @Test
    void createSector() throws Exception {
        int databaseSizeBeforeCreate = sectorRepository.findAll().size();
        // Create the Sector
        SectorDTO sectorDTO = sectorMapper.toDto(sector);
        restSectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sectorDTO)))
            .andExpect(status().isCreated());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeCreate + 1);
        Sector testSector = sectorList.get(sectorList.size() - 1);
        assertThat(testSector.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
    }

    @Test
    void createSectorWithExistingId() throws Exception {
        // Create the Sector with an existing ID
        sector.setId("existing_id");
        SectorDTO sectorDTO = sectorMapper.toDto(sector);

        int databaseSizeBeforeCreate = sectorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sectorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = sectorRepository.findAll().size();
        // set the field null
        sector.setDesignation(null);

        // Create the Sector, which fails.
        SectorDTO sectorDTO = sectorMapper.toDto(sector);

        restSectorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sectorDTO)))
            .andExpect(status().isBadRequest());

        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSectors() throws Exception {
        // Initialize the database
        sectorRepository.save(sector);

        // Get all the sectorList
        restSectorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sector.getId())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)));
    }

    @Test
    void getSector() throws Exception {
        // Initialize the database
        sectorRepository.save(sector);

        // Get the sector
        restSectorMockMvc
            .perform(get(ENTITY_API_URL_ID, sector.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sector.getId()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION));
    }

    @Test
    void getNonExistingSector() throws Exception {
        // Get the sector
        restSectorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSector() throws Exception {
        // Initialize the database
        sectorRepository.save(sector);

        int databaseSizeBeforeUpdate = sectorRepository.findAll().size();

        // Update the sector
        Sector updatedSector = sectorRepository.findById(sector.getId()).get();
        updatedSector.designation(UPDATED_DESIGNATION);
        SectorDTO sectorDTO = sectorMapper.toDto(updatedSector);

        restSectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sectorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sectorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeUpdate);
        Sector testSector = sectorList.get(sectorList.size() - 1);
        assertThat(testSector.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    void putNonExistingSector() throws Exception {
        int databaseSizeBeforeUpdate = sectorRepository.findAll().size();
        sector.setId(UUID.randomUUID().toString());

        // Create the Sector
        SectorDTO sectorDTO = sectorMapper.toDto(sector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sectorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSector() throws Exception {
        int databaseSizeBeforeUpdate = sectorRepository.findAll().size();
        sector.setId(UUID.randomUUID().toString());

        // Create the Sector
        SectorDTO sectorDTO = sectorMapper.toDto(sector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSectorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSector() throws Exception {
        int databaseSizeBeforeUpdate = sectorRepository.findAll().size();
        sector.setId(UUID.randomUUID().toString());

        // Create the Sector
        SectorDTO sectorDTO = sectorMapper.toDto(sector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSectorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sectorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSectorWithPatch() throws Exception {
        // Initialize the database
        sectorRepository.save(sector);

        int databaseSizeBeforeUpdate = sectorRepository.findAll().size();

        // Update the sector using partial update
        Sector partialUpdatedSector = new Sector();
        partialUpdatedSector.setId(sector.getId());

        partialUpdatedSector.designation(UPDATED_DESIGNATION);

        restSectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSector.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSector))
            )
            .andExpect(status().isOk());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeUpdate);
        Sector testSector = sectorList.get(sectorList.size() - 1);
        assertThat(testSector.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    void fullUpdateSectorWithPatch() throws Exception {
        // Initialize the database
        sectorRepository.save(sector);

        int databaseSizeBeforeUpdate = sectorRepository.findAll().size();

        // Update the sector using partial update
        Sector partialUpdatedSector = new Sector();
        partialUpdatedSector.setId(sector.getId());

        partialUpdatedSector.designation(UPDATED_DESIGNATION);

        restSectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSector.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSector))
            )
            .andExpect(status().isOk());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeUpdate);
        Sector testSector = sectorList.get(sectorList.size() - 1);
        assertThat(testSector.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
    }

    @Test
    void patchNonExistingSector() throws Exception {
        int databaseSizeBeforeUpdate = sectorRepository.findAll().size();
        sector.setId(UUID.randomUUID().toString());

        // Create the Sector
        SectorDTO sectorDTO = sectorMapper.toDto(sector);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sectorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSector() throws Exception {
        int databaseSizeBeforeUpdate = sectorRepository.findAll().size();
        sector.setId(UUID.randomUUID().toString());

        // Create the Sector
        SectorDTO sectorDTO = sectorMapper.toDto(sector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSectorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sectorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSector() throws Exception {
        int databaseSizeBeforeUpdate = sectorRepository.findAll().size();
        sector.setId(UUID.randomUUID().toString());

        // Create the Sector
        SectorDTO sectorDTO = sectorMapper.toDto(sector);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSectorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sectorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sector in the database
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSector() throws Exception {
        // Initialize the database
        sectorRepository.save(sector);

        int databaseSizeBeforeDelete = sectorRepository.findAll().size();

        // Delete the sector
        restSectorMockMvc
            .perform(delete(ENTITY_API_URL_ID, sector.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sector> sectorList = sectorRepository.findAll();
        assertThat(sectorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
