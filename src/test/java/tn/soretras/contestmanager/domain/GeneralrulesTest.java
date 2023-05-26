package tn.soretras.contestmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class GeneralrulesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Generalrules.class);
        Generalrules generalrules1 = new Generalrules();
        generalrules1.setId("id1");
        Generalrules generalrules2 = new Generalrules();
        generalrules2.setId(generalrules1.getId());
        assertThat(generalrules1).isEqualTo(generalrules2);
        generalrules2.setId("id2");
        assertThat(generalrules1).isNotEqualTo(generalrules2);
        generalrules1.setId(null);
        assertThat(generalrules1).isNotEqualTo(generalrules2);
    }
}
