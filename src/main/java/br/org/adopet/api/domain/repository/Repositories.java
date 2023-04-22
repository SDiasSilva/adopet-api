package br.org.adopet.api.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Component
@Getter
public class Repositories {
	@Autowired
	private TutorRepository tutorRepository;
	
	@Autowired
	private AbrigoRepository abrigoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private PetRepository petRepository;

	@Autowired
	private PorteRepository porteRepository;

	@Autowired
	private AdocaoRepository adocaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FuncaoRepository funcaoRepository;
	
}
