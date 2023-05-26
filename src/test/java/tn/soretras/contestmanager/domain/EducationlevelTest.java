package tn.soretras.contestmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class EducationlevelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Educationlevel.class);
        Educationlevel educationlevel1 = new Educationlevel();
        educationlevel1.setId("id1");
        Educationlevel educationlevel2 = new Educationlevel();
        educationlevel2.setId(educationlevel1.getId());
        assertThat(educationlevel1).isEqualTo(educationlevel2);
        educationlevel2.setId("id2");
        assertThat(educationlevel1).isNotEqualTo(educationlevel2);
        educationlevel1.setId(null);
        assertThat(educationlevel1).isNotEqualTo(educationlevel2);
    }
}
