package tn.soretras.contestmanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.contestmanager.domain.Generalrules;
import tn.soretras.contestmanager.service.dto.GeneralrulesDTO;

/**
 * Mapper for the entity {@link Generalrules} and its DTO {@link GeneralrulesDTO}.
 */
@Mapper(componentModel = "spring")
public interface GeneralrulesMapper extends EntityMapper<GeneralrulesDTO, Generalrules> {}
