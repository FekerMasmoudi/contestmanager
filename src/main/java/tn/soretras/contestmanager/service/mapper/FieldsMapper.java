package tn.soretras.contestmanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.contestmanager.domain.Fields;
import tn.soretras.contestmanager.service.dto.FieldsDTO;

/**
 * Mapper for the entity {@link Fields} and its DTO {@link FieldsDTO}.
 */
@Mapper(componentModel = "spring")
public interface FieldsMapper extends EntityMapper<FieldsDTO, Fields> {}
