package tn.soretras.contestmanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.contestmanager.domain.Educationlevel;
import tn.soretras.contestmanager.service.dto.EducationlevelDTO;

/**
 * Mapper for the entity {@link Educationlevel} and its DTO {@link EducationlevelDTO}.
 */
@Mapper(componentModel = "spring")
public interface EducationlevelMapper extends EntityMapper<EducationlevelDTO, Educationlevel> {}
