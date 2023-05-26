package tn.soretras.contestmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class ContestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContestDTO.class);
        ContestDTO contestDTO1 = new ContestDTO();
        contestDTO1.setId("id1");
        ContestDTO contestDTO2 = new ContestDTO();
        assertThat(contestDTO1).isNotEqualTo(contestDTO2);
        contestDTO2.setId(contestDTO1.getId());
        assertThat(contestDTO1).isEqualTo(contestDTO2);
        contestDTO2.setId("id2");
        assertThat(contestDTO1).isNotEqualTo(contestDTO2);
        contestDTO1.setId(null);
        assertThat(contestDTO1).isNotEqualTo(contestDTO2);
    }
}
