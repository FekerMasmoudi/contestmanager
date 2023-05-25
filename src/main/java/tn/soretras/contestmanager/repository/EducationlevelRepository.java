package tn.soretras.contestmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.soretras.contestmanager.domain.Educationlevel;

/**
 * Spring Data MongoDB repository for the Educationlevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationlevelRepository extends MongoRepository<Educationlevel, String> {}
