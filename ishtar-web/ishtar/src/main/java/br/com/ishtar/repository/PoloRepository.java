package br.com.ishtar.repository;

import br.com.ishtar.domain.Polo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Polo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PoloRepository extends JpaRepository<Polo, Long> {

}
