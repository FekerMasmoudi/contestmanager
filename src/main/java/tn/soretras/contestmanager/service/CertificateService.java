package tn.soretras.contestmanager.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.soretras.contestmanager.domain.Certificate;
import tn.soretras.contestmanager.repository.CertificateRepository;
import tn.soretras.contestmanager.service.dto.CertificateDTO;
import tn.soretras.contestmanager.service.mapper.CertificateMapper;

/**
 * Service Implementation for managing {@link Certificate}.
 */
@Service
public class CertificateService {

    private final Logger log = LoggerFactory.getLogger(CertificateService.class);

    private final CertificateRepository certificateRepository;

    private final CertificateMapper certificateMapper;

    public CertificateService(CertificateRepository certificateRepository, CertificateMapper certificateMapper) {
        this.certificateRepository = certificateRepository;
        this.certificateMapper = certificateMapper;
    }

    /**
     * Save a certificate.
     *
     * @param certificateDTO the entity to save.
     * @return the persisted entity.
     */
    public CertificateDTO save(CertificateDTO certificateDTO) {
        log.debug("Request to save Certificate : {}", certificateDTO);
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificate = certificateRepository.save(certificate);
        return certificateMapper.toDto(certificate);
    }

    /**
     * Update a certificate.
     *
     * @param certificateDTO the entity to save.
     * @return the persisted entity.
     */
    public CertificateDTO update(CertificateDTO certificateDTO) {
        log.debug("Request to update Certificate : {}", certificateDTO);
        Certificate certificate = certificateMapper.toEntity(certificateDTO);
        certificate = certificateRepository.save(certificate);
        return certificateMapper.toDto(certificate);
    }

    /**
     * Partially update a certificate.
     *
     * @param certificateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CertificateDTO> partialUpdate(CertificateDTO certificateDTO) {
        log.debug("Request to partially update Certificate : {}", certificateDTO);

        return certificateRepository
            .findById(certificateDTO.getId())
            .map(existingCertificate -> {
                certificateMapper.partialUpdate(existingCertificate, certificateDTO);

                return existingCertificate;
            })
            .map(certificateRepository::save)
            .map(certificateMapper::toDto);
    }

    /**
     * Get all the certificates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<CertificateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Certificates");
        return certificateRepository.findAll(pageable).map(certificateMapper::toDto);
    }

    /**
     * Get one certificate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CertificateDTO> findOne(String id) {
        log.debug("Request to get Certificate : {}", id);
        return certificateRepository.findById(id).map(certificateMapper::toDto);
    }

    /**
     * Delete the certificate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Certificate : {}", id);
        certificateRepository.deleteById(id);
    }
}
