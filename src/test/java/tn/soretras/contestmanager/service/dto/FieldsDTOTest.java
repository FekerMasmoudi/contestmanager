package tn.soretras.contestmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class FieldsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldsDTO.class);
        FieldsDTO fieldsDTO1 = new FieldsDTO();
        fieldsDTO1.setId("id1");
        FieldsDTO fieldsDTO2 = new FieldsDTO();
        assertThat(fieldsDTO1).isNotEqualTo(fieldsDTO2);
        fieldsDTO2.setId(fieldsDTO1.getId());
        assertThat(fieldsDTO1).isEqualTo(fieldsDTO2);
        fieldsDTO2.setId("id2");
        assertThat(fieldsDTO1).isNotEqualTo(fieldsDTO2);
        fieldsDTO1.setId(null);
        assertThat(fieldsDTO1).isNotEqualTo(fieldsDTO2);
    }
}
