package tn.soretras.contestmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.soretras.contestmanager.domain.Grade;

/**
 * Spring Data MongoDB repository for the Grade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GradeRepository extends MongoRepository<Grade, String> {}
