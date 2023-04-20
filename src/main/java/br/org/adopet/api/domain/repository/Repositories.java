package br.org.adopet.api.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Component
@Getter
public class Repositories {
	@Autowired
	protected TutorRepository tutorRepository;
	
	@Autowired
	protected AbrigoRepository abrigoRepository;

	@Autowired
	protected CidadeRepository cidadeRepository;
	
	@Autowired
	protected PetRepository petRepository;

	@Autowired
	protected PorteRepository porteRepository;

	@Autowired
	protected AdocaoRepository adocaoRepository;
}
