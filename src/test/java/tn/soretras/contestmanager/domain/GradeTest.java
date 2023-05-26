package tn.soretras.contestmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class GradeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grade.class);
        Grade grade1 = new Grade();
        grade1.setId("id1");
        Grade grade2 = new Grade();
        grade2.setId(grade1.getId());
        assertThat(grade1).isEqualTo(grade2);
        grade2.setId("id2");
        assertThat(grade1).isNotEqualTo(grade2);
        grade1.setId(null);
        assertThat(grade1).isNotEqualTo(grade2);
    }
}
