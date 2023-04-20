package br.org.adopet.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.org.adopet.api.domain.model.Adocao;

public interface AdocaoRepository extends JpaRepository<Adocao, Long>{

}
