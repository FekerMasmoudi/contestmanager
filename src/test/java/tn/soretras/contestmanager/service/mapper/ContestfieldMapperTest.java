package tn.soretras.contestmanager.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContestfieldMapperTest {

    private ContestfieldMapper contestfieldMapper;

    @BeforeEach
    public void setUp() {
        contestfieldMapper = new ContestfieldMapperImpl();
    }
}
