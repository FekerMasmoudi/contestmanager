package tn.soretras.contestmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class SectorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SectorDTO.class);
        SectorDTO sectorDTO1 = new SectorDTO();
        sectorDTO1.setId("id1");
        SectorDTO sectorDTO2 = new SectorDTO();
        assertThat(sectorDTO1).isNotEqualTo(sectorDTO2);
        sectorDTO2.setId(sectorDTO1.getId());
        assertThat(sectorDTO1).isEqualTo(sectorDTO2);
        sectorDTO2.setId("id2");
        assertThat(sectorDTO1).isNotEqualTo(sectorDTO2);
        sectorDTO1.setId(null);
        assertThat(sectorDTO1).isNotEqualTo(sectorDTO2);
    }
}
