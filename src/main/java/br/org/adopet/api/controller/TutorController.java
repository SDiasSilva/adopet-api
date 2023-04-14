package br.org.adopet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.org.adopet.api.dto.TutorCadastroDTO;
import br.org.adopet.api.model.Tutor;
import br.org.adopet.api.repository.TutorRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("tutores")
public class TutorController {

	@Autowired
	private TutorRepository repository;

	@PostMapping
	@Transactional
	public Tutor post(@RequestBody @Valid TutorCadastroDTO dadosTutor) {
		Tutor tutorCriado = repository.save(new Tutor(dadosTutor));
		return tutorCriado;
	}
}
