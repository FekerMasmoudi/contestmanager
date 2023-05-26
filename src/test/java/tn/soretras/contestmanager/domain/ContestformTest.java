package tn.soretras.contestmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class ContestformTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contestform.class);
        Contestform contestform1 = new Contestform();
        contestform1.setId("id1");
        Contestform contestform2 = new Contestform();
        contestform2.setId(contestform1.getId());
        assertThat(contestform1).isEqualTo(contestform2);
        contestform2.setId("id2");
        assertThat(contestform1).isNotEqualTo(contestform2);
        contestform1.setId(null);
        assertThat(contestform1).isNotEqualTo(contestform2);
    }
}
