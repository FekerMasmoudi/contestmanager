package tn.soretras.contestmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class CertificateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificateDTO.class);
        CertificateDTO certificateDTO1 = new CertificateDTO();
        certificateDTO1.setId("id1");
        CertificateDTO certificateDTO2 = new CertificateDTO();
        assertThat(certificateDTO1).isNotEqualTo(certificateDTO2);
        certificateDTO2.setId(certificateDTO1.getId());
        assertThat(certificateDTO1).isEqualTo(certificateDTO2);
        certificateDTO2.setId("id2");
        assertThat(certificateDTO1).isNotEqualTo(certificateDTO2);
        certificateDTO1.setId(null);
        assertThat(certificateDTO1).isNotEqualTo(certificateDTO2);
    }
}
