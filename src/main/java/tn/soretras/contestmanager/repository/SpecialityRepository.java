package tn.soretras.contestmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.soretras.contestmanager.domain.Speciality;

/**
 * Spring Data MongoDB repository for the Speciality entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialityRepository extends MongoRepository<Speciality, String> {}
