package tn.soretras.contestmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class GeneralrulesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeneralrulesDTO.class);
        GeneralrulesDTO generalrulesDTO1 = new GeneralrulesDTO();
        generalrulesDTO1.setId("id1");
        GeneralrulesDTO generalrulesDTO2 = new GeneralrulesDTO();
        assertThat(generalrulesDTO1).isNotEqualTo(generalrulesDTO2);
        generalrulesDTO2.setId(generalrulesDTO1.getId());
        assertThat(generalrulesDTO1).isEqualTo(generalrulesDTO2);
        generalrulesDTO2.setId("id2");
        assertThat(generalrulesDTO1).isNotEqualTo(generalrulesDTO2);
        generalrulesDTO1.setId(null);
        assertThat(generalrulesDTO1).isNotEqualTo(generalrulesDTO2);
    }
}
