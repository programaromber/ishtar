package br.com.ishtar.repository;

import br.com.ishtar.domain.Local;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Local entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

}
