package tn.soretras.contestmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.soretras.contestmanager.domain.Fields;

/**
 * Spring Data MongoDB repository for the Fields entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldsRepository extends MongoRepository<Fields, String> {}
