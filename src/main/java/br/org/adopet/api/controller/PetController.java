package br.org.adopet.api.controller;

import java.util.List;
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
import br.org.adopet.api.infra.exception.AdocaoException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "pets")
public class PetController extends BaseController {

	@GetMapping
	public ResponseEntity<List<PetDetalhamentoDTO>> getTodosOsPets() {
		List<PetDetalhamentoDTO> pets = super.petRepository().findAllByAdotadoIsFalse().stream()
				.map(PetDetalhamentoDTO::new).toList();
		if (pets.size() == 0) {
			throw new EntityNotFoundException();
		}
		return ResponseEntity.ok(pets);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PetDetalhamentoDTO> getPetPeloId(@PathVariable Long id) {
		Pet pet = buscarPetPeloId(id);
		return ResponseEntity.ok(new PetDetalhamentoDTO(pet));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<PetDetalhamentoDTO> postCadastrarPet(@RequestBody @Valid PetCadastroDTO dadosPet) {
		Abrigo abrigo = super.buscarEntidade(dadosPet.abrigoId(), super.abrigoRepository(), "abrigo");
		Porte porte = super.buscarEntidade(dadosPet.porteId(), super.porteRepository(), "porte");
		Pet pet = super.petRepository().save(new Pet(dadosPet, abrigo, porte));
		return ResponseEntity.ok(new PetDetalhamentoDTO(pet));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<PetDetalhamentoDTO> putAtualizarPet(@RequestBody @Valid PetAlteracaoDTO dadosPet) {
		return atualizarPet(dadosPet);
	}

	@PatchMapping
	@Transactional
	public ResponseEntity<PetDetalhamentoDTO> patchAtualizarPet(@RequestBody @Valid PetAlteracaoDTO dadosPet) {
		return atualizarPet(dadosPet);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<MensagemDTO> deletePet(@PathVariable Long id) {
		Pet pet = buscarPetPeloId(id);
		super.petRepository().delete(pet);
		MensagemDTO mensagemDTO = new MensagemDTO(String.format("O pet com ID %d foi excluído com sucesso.", id));
		return ResponseEntity.ok(mensagemDTO);
	}

	private ResponseEntity<PetDetalhamentoDTO> atualizarPet(PetAlteracaoDTO dadosPet) {
		Porte porte = super.buscarEntidade(dadosPet.porteId(), super.porteRepository(), "porte");
		Pet pet = buscarPetPeloId(dadosPet.id());
		pet.atualizarInformacoes(dadosPet, porte);
		return ResponseEntity.ok(new PetDetalhamentoDTO(pet));
	}

	private Pet buscarPetPeloId(Long id) {
		Pet pet = super.buscarEntidade(id, super.petRepository(), "pet");
		if (pet.isAdotado()) {
			throw new AdocaoException(id);
		}
		return pet;
	}

}
