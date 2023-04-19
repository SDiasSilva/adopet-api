package br.org.adopet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.org.adopet.api.domain.dto.PetCadastroDTO;
import br.org.adopet.api.domain.dto.PetListagemDTO;
import br.org.adopet.api.domain.model.Abrigo;
import br.org.adopet.api.domain.model.Pet;
import br.org.adopet.api.domain.model.Porte;
import br.org.adopet.api.domain.repository.AbrigoRepository;
import br.org.adopet.api.domain.repository.PetRepository;
import br.org.adopet.api.domain.repository.PorteRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "pets")
public class PetController {
	
	@Autowired
	PetRepository petRepository;
	
	@Autowired
	AbrigoRepository abrigoRepository;
	
	@Autowired
	PorteRepository porteRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<PetListagemDTO> post(@RequestBody @Valid PetCadastroDTO dadosPet) {
		Abrigo abrigo = abrigoRepository.getReferenceById(dadosPet.abrigoId());
		Porte porte = porteRepository.getReferenceById(dadosPet.porteId());
		Pet pet = petRepository.save(new Pet(dadosPet, abrigo, porte));
		return ResponseEntity.ok(new PetListagemDTO(pet));
	}
}
