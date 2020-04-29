package br.com.ishtar.repository;

import br.com.ishtar.domain.Contato;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Contato entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

}
