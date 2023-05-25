package tn.soretras.contestmanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.contestmanager.domain.Speciality;
import tn.soretras.contestmanager.service.dto.SpecialityDTO;

/**
 * Mapper for the entity {@link Speciality} and its DTO {@link SpecialityDTO}.
 */
@Mapper(componentModel = "spring")
public interface SpecialityMapper extends EntityMapper<SpecialityDTO, Speciality> {}
