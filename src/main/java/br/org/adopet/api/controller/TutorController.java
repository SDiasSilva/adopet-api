package br.org.adopet.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.org.adopet.api.dto.TutorCadastroDTO;
import br.org.adopet.api.dto.TutorListagemDTO;
import br.org.adopet.api.model.Tutor;
import br.org.adopet.api.repository.TutorRepository;
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

	
	@PostMapping
	@Transactional
	public Tutor post(@RequestBody @Valid TutorCadastroDTO dadosTutor) {
		Tutor tutorCriado = repository.save(new Tutor(dadosTutor));
		return tutorCriado;
	}
}
