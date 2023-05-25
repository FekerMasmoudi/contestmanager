package tn.soretras.contestmanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.contestmanager.domain.Sector;
import tn.soretras.contestmanager.service.dto.SectorDTO;

/**
 * Mapper for the entity {@link Sector} and its DTO {@link SectorDTO}.
 */
@Mapper(componentModel = "spring")
public interface SectorMapper extends EntityMapper<SectorDTO, Sector> {}
