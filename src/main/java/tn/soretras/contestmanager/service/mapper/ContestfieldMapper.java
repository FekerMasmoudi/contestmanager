package tn.soretras.contestmanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.contestmanager.domain.Contest;
import tn.soretras.contestmanager.domain.Contestfield;
import tn.soretras.contestmanager.service.dto.ContestDTO;
import tn.soretras.contestmanager.service.dto.ContestfieldDTO;

/**
 * Mapper for the entity {@link Contestfield} and its DTO {@link ContestfieldDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContestfieldMapper extends EntityMapper<ContestfieldDTO, Contestfield> {
    @Mapping(target = "idcontest", source = "idcontest", qualifiedByName = "contestId")
    ContestfieldDTO toDto(Contestfield s);

    @Named("contestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContestDTO toDtoContestId(Contest contest);
}
