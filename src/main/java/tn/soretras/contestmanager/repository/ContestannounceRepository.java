package tn.soretras.contestmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.soretras.contestmanager.domain.Contestannounce;

/**
 * Spring Data MongoDB repository for the Contestannounce entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContestannounceRepository extends MongoRepository<Contestannounce, String> {}
