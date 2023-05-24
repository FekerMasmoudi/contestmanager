package tn.soretras.contestmanager.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldsMapperTest {

    private FieldsMapper fieldsMapper;

    @BeforeEach
    public void setUp() {
        fieldsMapper = new FieldsMapperImpl();
    }
}
