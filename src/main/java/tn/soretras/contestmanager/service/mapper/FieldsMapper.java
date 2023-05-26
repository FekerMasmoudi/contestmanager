package tn.soretras.contestmanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.contestmanager.domain.Contestform;
import tn.soretras.contestmanager.domain.Fields;
import tn.soretras.contestmanager.service.dto.ContestformDTO;
import tn.soretras.contestmanager.service.dto.FieldsDTO;

/**
 * Mapper for the entity {@link Fields} and its DTO {@link FieldsDTO}.
 */
@Mapper(componentModel = "spring")
public interface FieldsMapper extends EntityMapper<FieldsDTO, Fields> {
    @Mapping(target = "contestform", source = "contestform", qualifiedByName = "contestformId")
    FieldsDTO toDto(Fields s);

    @Named("contestformId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContestformDTO toDtoContestformId(Contestform contestform);
}
