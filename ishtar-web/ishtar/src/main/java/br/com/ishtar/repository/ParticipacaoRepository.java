package br.com.ishtar.repository;

import br.com.ishtar.domain.Participacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Participacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParticipacaoRepository extends JpaRepository<Participacao, Long> {

}
