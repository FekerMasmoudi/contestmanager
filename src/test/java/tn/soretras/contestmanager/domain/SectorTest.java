package tn.soretras.contestmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class SectorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sector.class);
        Sector sector1 = new Sector();
        sector1.setId("id1");
        Sector sector2 = new Sector();
        sector2.setId(sector1.getId());
        assertThat(sector1).isEqualTo(sector2);
        sector2.setId("id2");
        assertThat(sector1).isNotEqualTo(sector2);
        sector1.setId(null);
        assertThat(sector1).isNotEqualTo(sector2);
    }
}
