package br.org.adopet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.org.adopet.api.domain.dto.MensagemDTO;
import br.org.adopet.api.domain.dto.PetAlteracaoDTO;
import br.org.adopet.api.domain.dto.PetCadastroDTO;
import br.org.adopet.api.domain.dto.PetDetalhamentoDTO;
import br.org.adopet.api.domain.model.Abrigo;
import br.org.adopet.api.domain.model.Pet;
import br.org.adopet.api.domain.model.Porte;
import br.org.adopet.api.domain.repository.AbrigoRepository;
import br.org.adopet.api.domain.repository.PetRepository;
import br.org.adopet.api.domain.repository.PorteRepository;
import br.org.adopet.api.exception.AdocaoException;
import jakarta.persistence.EntityNotFoundException;
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

	@GetMapping
	public ResponseEntity<List<PetDetalhamentoDTO>> get() {
		List<PetDetalhamentoDTO> pets = petRepository.findAllByAdotadoIsFalse().stream().map(PetDetalhamentoDTO::new).toList();
		if (pets.size() == 0) {
			throw new EntityNotFoundException();
		}
		return ResponseEntity.ok(pets);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PetDetalhamentoDTO> get(@PathVariable Long id) {
		Pet pet = buscarPetPeloId(id);
		return ResponseEntity.ok(new PetDetalhamentoDTO(pet));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<PetDetalhamentoDTO> post(@RequestBody @Valid PetCadastroDTO dadosPet) {
		Abrigo abrigo = abrigoRepository.getReferenceById(dadosPet.abrigoId());
		Porte porte = porteRepository.getReferenceById(dadosPet.porteId());
		Pet pet = petRepository.save(new Pet(dadosPet, abrigo, porte));
		return ResponseEntity.ok(new PetDetalhamentoDTO(pet));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<PetDetalhamentoDTO> put(@RequestBody @Valid PetAlteracaoDTO dadosPet) {
		return atualizarInformacoes(dadosPet);
	}

	@PatchMapping
	@Transactional
	public ResponseEntity<PetDetalhamentoDTO> patch(@RequestBody @Valid PetAlteracaoDTO dadosPet) {
		return atualizarInformacoes(dadosPet);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<MensagemDTO> delete(@PathVariable Long id) {
		Pet pet = buscarPetPeloId(id);
		petRepository.delete(pet);
		MensagemDTO mensagemDTO = new MensagemDTO(String.format("O pet com ID %d foi excluído com sucesso.", id));
		return ResponseEntity.ok(mensagemDTO);
	}
	
	private ResponseEntity<PetDetalhamentoDTO> atualizarInformacoes(PetAlteracaoDTO dadosPet) {
		Porte porte = buscarPortePeloId(dadosPet.porteId());
		Pet pet = buscarPetPeloId(dadosPet.id());
		pet.atualizarInformacoes(dadosPet, porte);
		return ResponseEntity.ok(new PetDetalhamentoDTO(pet));
	}

	private Pet buscarPetPeloId(Long id) {
		Pet pet = petRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
				String.format("O pet com ID %d não foi encontrado no banco de dados.", id)));
		if(pet.isAdotado()) {
			throw new AdocaoException(id);
		}
		return pet;
	}

	private Porte buscarPortePeloId(Long id) {
		return porteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
				String.format("O porte com ID %d não foi encontrado no banco de dados.", id)));
	}
}
