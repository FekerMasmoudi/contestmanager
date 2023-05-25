package tn.soretras.contestmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class SpecialityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Speciality.class);
        Speciality speciality1 = new Speciality();
        speciality1.setId("id1");
        Speciality speciality2 = new Speciality();
        speciality2.setId(speciality1.getId());
        assertThat(speciality1).isEqualTo(speciality2);
        speciality2.setId("id2");
        assertThat(speciality1).isNotEqualTo(speciality2);
        speciality1.setId(null);
        assertThat(speciality1).isNotEqualTo(speciality2);
    }
}
