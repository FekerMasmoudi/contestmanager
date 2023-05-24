package tn.soretras.contestmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class FieldsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fields.class);
        Fields fields1 = new Fields();
        fields1.setId("id1");
        Fields fields2 = new Fields();
        fields2.setId(fields1.getId());
        assertThat(fields1).isEqualTo(fields2);
        fields2.setId("id2");
        assertThat(fields1).isNotEqualTo(fields2);
        fields1.setId(null);
        assertThat(fields1).isNotEqualTo(fields2);
    }
}
