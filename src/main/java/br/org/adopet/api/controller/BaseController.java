package br.org.adopet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.org.adopet.api.domain.repository.AbrigoRepository;
import br.org.adopet.api.domain.repository.AdocaoRepository;
import br.org.adopet.api.domain.repository.CidadeRepository;
import br.org.adopet.api.domain.repository.FuncaoRepository;
import br.org.adopet.api.domain.repository.PetRepository;
import br.org.adopet.api.domain.repository.PorteRepository;
import br.org.adopet.api.domain.repository.Repositories;
import br.org.adopet.api.domain.repository.TutorRepository;
import br.org.adopet.api.domain.repository.UsuarioRepository;
import br.org.adopet.api.infra.security.SecurityConfigurations;
import jakarta.persistence.EntityNotFoundException;

public abstract class BaseController {
	@Autowired
	private Repositories repositories;

	@Autowired
	private SecurityConfigurations configuration;
	
	protected PasswordEncoder encoder() {
		return configuration.passwordEncoder();
	}
	
	protected AbrigoRepository abrigoRepository() {
		return this.repositories.getAbrigoRepository();
	}

	protected AdocaoRepository adocaoRepository() {
		return this.repositories.getAdocaoRepository();
	}

	protected CidadeRepository cidadeRepository() {
		return this.repositories.getCidadeRepository();
	}

	protected PetRepository petRepository() {
		return this.repositories.getPetRepository();
	}

	protected PorteRepository porteRepository() {
		return this.repositories.getPorteRepository();
	}
	
	protected TutorRepository tutorRepository() {
		return this.repositories.getTutorRepository();
	}
	
	protected UsuarioRepository usuarioRepository() {
		return this.repositories.getUsuarioRepository();
	}
	
	protected FuncaoRepository funcaoRepository() {
		return this.repositories.getFuncaoRepository();
	}

	protected <T> T buscarEntidade(Long id, JpaRepository<T, Long> repository, String nomeEntidade) {
		if (id == null) {
			return null;
		}
		return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
				String.format("%s com ID %d não encontrado no banco de dados.", nomeEntidade, id)));
	}
}
