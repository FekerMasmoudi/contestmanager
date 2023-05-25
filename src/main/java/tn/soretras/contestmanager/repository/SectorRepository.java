package tn.soretras.contestmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.soretras.contestmanager.domain.Sector;

/**
 * Spring Data MongoDB repository for the Sector entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SectorRepository extends MongoRepository<Sector, String> {}
