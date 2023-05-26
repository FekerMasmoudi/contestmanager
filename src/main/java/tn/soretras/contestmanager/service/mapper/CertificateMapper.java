package tn.soretras.contestmanager.service.mapper;

import org.mapstruct.*;
import tn.soretras.contestmanager.domain.Certificate;
import tn.soretras.contestmanager.service.dto.CertificateDTO;

/**
 * Mapper for the entity {@link Certificate} and its DTO {@link CertificateDTO}.
 */
@Mapper(componentModel = "spring")
public interface CertificateMapper extends EntityMapper<CertificateDTO, Certificate> {}
