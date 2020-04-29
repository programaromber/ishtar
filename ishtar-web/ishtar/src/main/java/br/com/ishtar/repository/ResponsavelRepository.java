package br.com.ishtar.repository;

import br.com.ishtar.domain.Responsavel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Responsavel entity.
 */
@Repository
public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {

    @Query(value = "select distinct responsavel from Responsavel responsavel left join fetch responsavel.polos",
        countQuery = "select count(distinct responsavel) from Responsavel responsavel")
    Page<Responsavel> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct responsavel from Responsavel responsavel left join fetch responsavel.polos")
    List<Responsavel> findAllWithEagerRelationships();

    @Query("select responsavel from Responsavel responsavel left join fetch responsavel.polos where responsavel.id =:id")
    Optional<Responsavel> findOneWithEagerRelationships(@Param("id") Long id);

}
