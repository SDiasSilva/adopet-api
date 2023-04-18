package br.org.adopet.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.org.adopet.api.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{

}
