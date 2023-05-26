package tn.soretras.contestmanager.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContestannounceMapperTest {

    private ContestannounceMapper contestannounceMapper;

    @BeforeEach
    public void setUp() {
        contestannounceMapper = new ContestannounceMapperImpl();
    }
}
