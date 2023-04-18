package br.org.adopet.api.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.org.adopet.api.domain.dto.TutorAlteracaoDTO;
import br.org.adopet.api.domain.dto.TutorCadastroDTO;
import br.org.adopet.api.domain.dto.TutorListagemDTO;
import br.org.adopet.api.domain.model.Tutor;
import br.org.adopet.api.domain.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("tutores")
public class TutorController {

	@Autowired
	private TutorRepository repository;

	@GetMapping
	public ResponseEntity<List<TutorListagemDTO>> get() {
		List<TutorListagemDTO> tutores = repository.findAll().stream().map(TutorListagemDTO::new).toList();
		if(tutores.size() == 0) {
			throw new EntityNotFoundException();
		}
		return ResponseEntity.ok(tutores);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TutorListagemDTO> get(@PathVariable Long id) {
		Tutor tutor = repository.getReferenceById(id);
		return ResponseEntity.ok(new TutorListagemDTO(tutor));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<TutorListagemDTO> post(@RequestBody @Valid TutorCadastroDTO dadosTutor) {
		Tutor tutorCriado = repository.save(new Tutor(dadosTutor));
		return ResponseEntity.ok(new TutorListagemDTO(tutorCriado));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<TutorAlteracaoDTO> put(@RequestBody @Valid TutorAlteracaoDTO dadosTutor) {
		Tutor tutor = repository.getReferenceById(dadosTutor.id());
		tutor.atualizarInformações(dadosTutor);
		return ResponseEntity.ok(new TutorAlteracaoDTO(tutor));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Optional<Tutor> tutor = repository.findById(id);
		if(tutor.isEmpty()) { 
			throw new EntityNotFoundException();
		}
		repository.deleteById(id);
		String mensagem = "Tutor excluído com sucesso.";
		return new ResponseEntity<String>(mensagem, HttpStatus.NO_CONTENT);
	}
}
