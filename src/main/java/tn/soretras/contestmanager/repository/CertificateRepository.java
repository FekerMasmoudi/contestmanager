package tn.soretras.contestmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.soretras.contestmanager.domain.Certificate;

/**
 * Spring Data MongoDB repository for the Certificate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CertificateRepository extends MongoRepository<Certificate, String> {}
