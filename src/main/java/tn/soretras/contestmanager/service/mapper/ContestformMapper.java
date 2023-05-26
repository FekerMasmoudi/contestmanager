package tn.soretras.contestmanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.contestmanager.domain.Contest;
import tn.soretras.contestmanager.domain.Contestform;
import tn.soretras.contestmanager.domain.User;
import tn.soretras.contestmanager.service.dto.ContestDTO;
import tn.soretras.contestmanager.service.dto.ContestformDTO;
import tn.soretras.contestmanager.service.dto.UserDTO;

/**
 * Mapper for the entity {@link Contestform} and its DTO {@link ContestformDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContestformMapper extends EntityMapper<ContestformDTO, Contestform> {
    @Mapping(target = "contest", source = "contest", qualifiedByName = "contestId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    ContestformDTO toDto(Contestform s);

    @Named("contestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContestDTO toDtoContestId(Contest contest);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
