package tn.soretras.contestmanager.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GeneralrulesMapperTest {

    private GeneralrulesMapper generalrulesMapper;

    @BeforeEach
    public void setUp() {
        generalrulesMapper = new GeneralrulesMapperImpl();
    }
}
