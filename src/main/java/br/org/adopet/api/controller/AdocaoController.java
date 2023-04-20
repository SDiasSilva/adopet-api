package br.org.adopet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.org.adopet.api.domain.dto.AdocaoCadastro;
import br.org.adopet.api.domain.dto.AdocaoDetalhamentoDTO;
import br.org.adopet.api.domain.model.Adocao;
import br.org.adopet.api.domain.model.Pet;
import br.org.adopet.api.domain.model.Tutor;
import br.org.adopet.api.domain.repository.AdocaoRepository;
import br.org.adopet.api.domain.repository.PetRepository;
import br.org.adopet.api.domain.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("adocao")
public class AdocaoController {
	
	@Autowired
	AdocaoRepository adocaoRepository;
	
	@Autowired
	PetRepository petRepository;
	
	@Autowired
	TutorRepository tutorRepository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<AdocaoDetalhamentoDTO> post(@RequestBody @Valid AdocaoCadastro dadosAdocao){
		Pet pet = buscarPetPeloId(dadosAdocao.petId());
		Tutor tutor = buscarTutorPeloId(dadosAdocao.tutorId());
		Adocao adocao = adocaoRepository.save(new Adocao(pet, tutor));
		return ResponseEntity.ok(new AdocaoDetalhamentoDTO(adocao));
	}
	
	private Tutor buscarTutorPeloId(Long id) {
		return tutorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
				String.format("O tutor com ID %d não foi encontrado no banco de dados.", id)));
	}
	
	private Pet buscarPetPeloId(Long id) {
		return petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
				String.format("O pet com ID %d não foi encontrado no banco de dados.", id)));
	}
}
