package tn.soretras.contestmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class ContestannounceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContestannounceDTO.class);
        ContestannounceDTO contestannounceDTO1 = new ContestannounceDTO();
        contestannounceDTO1.setId("id1");
        ContestannounceDTO contestannounceDTO2 = new ContestannounceDTO();
        assertThat(contestannounceDTO1).isNotEqualTo(contestannounceDTO2);
        contestannounceDTO2.setId(contestannounceDTO1.getId());
        assertThat(contestannounceDTO1).isEqualTo(contestannounceDTO2);
        contestannounceDTO2.setId("id2");
        assertThat(contestannounceDTO1).isNotEqualTo(contestannounceDTO2);
        contestannounceDTO1.setId(null);
        assertThat(contestannounceDTO1).isNotEqualTo(contestannounceDTO2);
    }
}
