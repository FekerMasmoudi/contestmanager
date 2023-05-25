package tn.soretras.contestmanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.contestmanager.domain.Grade;
import tn.soretras.contestmanager.service.dto.GradeDTO;

/**
 * Mapper for the entity {@link Grade} and its DTO {@link GradeDTO}.
 */
@Mapper(componentModel = "spring")
public interface GradeMapper extends EntityMapper<GradeDTO, Grade> {}
