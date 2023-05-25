package tn.soretras.contestmanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.contestmanager.domain.Contestannounce;
import tn.soretras.contestmanager.domain.Generalrules;
import tn.soretras.contestmanager.service.dto.ContestannounceDTO;
import tn.soretras.contestmanager.service.dto.GeneralrulesDTO;

/**
 * Mapper for the entity {@link Contestannounce} and its DTO {@link ContestannounceDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContestannounceMapper extends EntityMapper<ContestannounceDTO, Contestannounce> {
    @Mapping(target = "idsgeneralrules", source = "idsgeneralrules", qualifiedByName = "generalrulesId")
    ContestannounceDTO toDto(Contestannounce s);

    @Named("generalrulesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GeneralrulesDTO toDtoGeneralrulesId(Generalrules generalrules);
}
