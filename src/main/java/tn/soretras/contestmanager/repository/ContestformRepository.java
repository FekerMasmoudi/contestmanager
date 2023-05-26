package tn.soretras.contestmanager.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.soretras.contestmanager.domain.Contestform;

/**
 * Spring Data MongoDB repository for the Contestform entity.
 */
@Repository
public interface ContestformRepository extends MongoRepository<Contestform, String> {
    @Query("{}")
    Page<Contestform> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Contestform> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Contestform> findOneWithEagerRelationships(String id);
}
