package br.org.adopet.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.org.adopet.api.domain.model.Funcao;

public interface FuncaoRepository extends JpaRepository<Funcao, Long>{

	Funcao findByNome(String nome);

}
