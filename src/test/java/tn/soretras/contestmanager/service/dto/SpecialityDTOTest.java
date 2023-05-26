package tn.soretras.contestmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class SpecialityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialityDTO.class);
        SpecialityDTO specialityDTO1 = new SpecialityDTO();
        specialityDTO1.setId("id1");
        SpecialityDTO specialityDTO2 = new SpecialityDTO();
        assertThat(specialityDTO1).isNotEqualTo(specialityDTO2);
        specialityDTO2.setId(specialityDTO1.getId());
        assertThat(specialityDTO1).isEqualTo(specialityDTO2);
        specialityDTO2.setId("id2");
        assertThat(specialityDTO1).isNotEqualTo(specialityDTO2);
        specialityDTO1.setId(null);
        assertThat(specialityDTO1).isNotEqualTo(specialityDTO2);
    }
}
