package tn.soretras.contestmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.soretras.contestmanager.domain.Generalrules;

/**
 * Spring Data MongoDB repository for the Generalrules entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeneralrulesRepository extends MongoRepository<Generalrules, String> {}
