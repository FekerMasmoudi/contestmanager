package tn.soretras.contestmanager.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import tn.soretras.contestmanager.domain.Contestannounce;

/**
 * Spring Data MongoDB repository for the Contestannounce entity.
 */
@Repository
public interface ContestannounceRepository extends MongoRepository<Contestannounce, String> {
    @Query("{}")
    Page<Contestannounce> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Contestannounce> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Contestannounce> findOneWithEagerRelationships(String id);
}
