package tn.soretras.contestmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.soretras.contestmanager.domain.Contest;

/**
 * Spring Data MongoDB repository for the Contest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContestRepository extends MongoRepository<Contest, String> {}
