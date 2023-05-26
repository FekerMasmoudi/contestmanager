package tn.soretras.contestmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class ContestfieldDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContestfieldDTO.class);
        ContestfieldDTO contestfieldDTO1 = new ContestfieldDTO();
        contestfieldDTO1.setId("id1");
        ContestfieldDTO contestfieldDTO2 = new ContestfieldDTO();
        assertThat(contestfieldDTO1).isNotEqualTo(contestfieldDTO2);
        contestfieldDTO2.setId(contestfieldDTO1.getId());
        assertThat(contestfieldDTO1).isEqualTo(contestfieldDTO2);
        contestfieldDTO2.setId("id2");
        assertThat(contestfieldDTO1).isNotEqualTo(contestfieldDTO2);
        contestfieldDTO1.setId(null);
        assertThat(contestfieldDTO1).isNotEqualTo(contestfieldDTO2);
    }
}
