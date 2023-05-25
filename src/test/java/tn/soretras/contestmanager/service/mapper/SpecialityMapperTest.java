package tn.soretras.contestmanager.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpecialityMapperTest {

    private SpecialityMapper specialityMapper;

    @BeforeEach
    public void setUp() {
        specialityMapper = new SpecialityMapperImpl();
    }
}
