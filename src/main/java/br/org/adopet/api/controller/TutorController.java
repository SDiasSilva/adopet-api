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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.org.adopet.api.dto.TutorCadastroDTO;
import br.org.adopet.api.dto.TutorListagemDTO;
import br.org.adopet.api.model.Tutor;
import br.org.adopet.api.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("tutores")
public class TutorController {

	@Autowired
	private TutorRepository repository;

	@GetMapping
	public ResponseEntity<?> get() {
		List<TutorListagemDTO> tutores = repository.findAll().stream().map(TutorListagemDTO::new).toList();
		if (tutores.isEmpty()) {
			String mensagem = "Não foi encontrado nenhum tutor cadastrado no banco de dados.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
		}
		return ResponseEntity.ok(tutores);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> get(@PathVariable Long id) {
		try {
			Tutor tutor = repository.getReferenceById(id);
			return ResponseEntity.ok(new TutorListagemDTO(tutor));
		} catch (EntityNotFoundException e) {
			String mensagem = "Não foi encontrado nenhum tutor com este ID.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
		}
	}

	@PostMapping
	@Transactional
	public Tutor post(@RequestBody @Valid TutorCadastroDTO dadosTutor) {
		Tutor tutorCriado = repository.save(new Tutor(dadosTutor));
		return tutorCriado;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletar(@PathVariable Long id) {
		String mensagem = "";
		Optional<Tutor> tutor = repository.findById(id);
		if (tutor.isEmpty()) {
			mensagem = "O tutor com ID(" + id + ") não existe, portanto não pode ser deletado.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagem);
		}
		repository.deleteById(id);
		mensagem = "O tutor com ID(" + id + ") foi deletado com sucesso.";
		return ResponseEntity.ok().body(mensagem);
	}
}
