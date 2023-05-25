package tn.soretras.contestmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tn.soretras.contestmanager.web.rest.TestUtil;

class ContestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Contest.class);
        Contest contest1 = new Contest();
        contest1.setId("id1");
        Contest contest2 = new Contest();
        contest2.setId(contest1.getId());
        assertThat(contest1).isEqualTo(contest2);
        contest2.setId("id2");
        assertThat(contest1).isNotEqualTo(contest2);
        contest1.setId(null);
        assertThat(contest1).isNotEqualTo(contest2);
    }
}
