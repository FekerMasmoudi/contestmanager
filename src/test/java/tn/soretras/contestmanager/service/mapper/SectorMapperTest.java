package tn.soretras.contestmanager.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SectorMapperTest {

    private SectorMapper sectorMapper;

    @BeforeEach
    public void setUp() {
        sectorMapper = new SectorMapperImpl();
    }
}
