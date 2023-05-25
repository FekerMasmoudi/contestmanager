package tn.soretras.contestmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class ContestannounceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contestannounce.class);
        Contestannounce contestannounce1 = new Contestannounce();
        contestannounce1.setId("id1");
        Contestannounce contestannounce2 = new Contestannounce();
        contestannounce2.setId(contestannounce1.getId());
        assertThat(contestannounce1).isEqualTo(contestannounce2);
        contestannounce2.setId("id2");
        assertThat(contestannounce1).isNotEqualTo(contestannounce2);
        contestannounce1.setId(null);
        assertThat(contestannounce1).isNotEqualTo(contestannounce2);
    }
}
