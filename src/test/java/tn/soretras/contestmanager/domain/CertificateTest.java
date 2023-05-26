package tn.soretras.contestmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class CertificateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Certificate.class);
        Certificate certificate1 = new Certificate();
        certificate1.setId("id1");
        Certificate certificate2 = new Certificate();
        certificate2.setId(certificate1.getId());
        assertThat(certificate1).isEqualTo(certificate2);
        certificate2.setId("id2");
        assertThat(certificate1).isNotEqualTo(certificate2);
        certificate1.setId(null);
        assertThat(certificate1).isNotEqualTo(certificate2);
    }
}
