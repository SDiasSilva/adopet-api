package br.org.adopet.api.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
import br.org.adopet.api.domain.model.Cidade;
import br.org.adopet.api.domain.model.Tutor;
import br.org.adopet.api.domain.repository.CidadeRepository;
import br.org.adopet.api.domain.repository.TutorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("tutores")
public class TutorController {

	@Autowired
	private TutorRepository tutorRepository;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@GetMapping
	public ResponseEntity<List<TutorListagemDTO>> get() {
		List<TutorListagemDTO> tutores = tutorRepository.findAll().stream().map(TutorListagemDTO::new).toList();
		if(tutores.size() == 0) {
			throw new EntityNotFoundException();
		}
		return ResponseEntity.ok(tutores);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TutorListagemDTO> get(@PathVariable Long id) {
		Tutor tutor = tutorRepository.getReferenceById(id);
		return ResponseEntity.ok(new TutorListagemDTO(tutor));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<TutorListagemDTO> post(@RequestBody @Valid TutorCadastroDTO dadosTutor) {
		Tutor tutorCriado = tutorRepository.save(new Tutor(dadosTutor));
		return ResponseEntity.ok(new TutorListagemDTO(tutorCriado));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<TutorListagemDTO> put(@RequestBody @Valid TutorAlteracaoDTO dadosTutor) {
		Cidade cidade = buscarPetPeloId(dadosTutor.cidadeId());
		Tutor tutor = buscarTutorPeloId(dadosTutor.id());
		tutor.atualizarInformações(dadosTutor, cidade);
		return ResponseEntity.ok(new TutorListagemDTO(tutor));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<String> delete(@PathVariable Long id) {
		Optional<Tutor> tutor = tutorRepository.findById(id);
		if(tutor.isEmpty()) { 
			throw new EntityNotFoundException();
		}
		tutorRepository.deleteById(id);
		String mensagem = "Tutor com o id("+id+") foi excluído com sucesso.";
		return ResponseEntity.ok(mensagem);
	}
	
	private Tutor buscarTutorPeloId(Long id) {
		return tutorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
				String.format("O tutor com ID %d não foi encontrado no banco de dados.", id)));
	}
	
	private Cidade buscarPetPeloId(Long id) {
		return cidadeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
				String.format("A cidade com ID %d não foi encontrado no banco de dados.", id)));
	}
}
