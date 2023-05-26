package tn.soretras.contestmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class EducationlevelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationlevelDTO.class);
        EducationlevelDTO educationlevelDTO1 = new EducationlevelDTO();
        educationlevelDTO1.setId("id1");
        EducationlevelDTO educationlevelDTO2 = new EducationlevelDTO();
        assertThat(educationlevelDTO1).isNotEqualTo(educationlevelDTO2);
        educationlevelDTO2.setId(educationlevelDTO1.getId());
        assertThat(educationlevelDTO1).isEqualTo(educationlevelDTO2);
        educationlevelDTO2.setId("id2");
        assertThat(educationlevelDTO1).isNotEqualTo(educationlevelDTO2);
        educationlevelDTO1.setId(null);
        assertThat(educationlevelDTO1).isNotEqualTo(educationlevelDTO2);
    }
}
