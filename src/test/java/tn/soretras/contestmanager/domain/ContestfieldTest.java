package tn.soretras.contestmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class ContestfieldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contestfield.class);
        Contestfield contestfield1 = new Contestfield();
        contestfield1.setId("id1");
        Contestfield contestfield2 = new Contestfield();
        contestfield2.setId(contestfield1.getId());
        assertThat(contestfield1).isEqualTo(contestfield2);
        contestfield2.setId("id2");
        assertThat(contestfield1).isNotEqualTo(contestfield2);
        contestfield1.setId(null);
        assertThat(contestfield1).isNotEqualTo(contestfield2);
    }
}
