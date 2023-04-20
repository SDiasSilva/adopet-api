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
import br.org.adopet.api.domain.dto.TutorAlteracaoDTO;
import br.org.adopet.api.domain.dto.TutorCadastroDTO;
import br.org.adopet.api.domain.dto.TutorDetalhamentoDTO;
import br.org.adopet.api.domain.model.Cidade;
import br.org.adopet.api.domain.model.Tutor;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("tutores")
public class TutorController extends BaseController{

	@GetMapping
	public ResponseEntity<List<TutorDetalhamentoDTO>> getTodosOsTutores() {
		List<TutorDetalhamentoDTO> tutores = super.tutorRepository().findAll().stream().map(TutorDetalhamentoDTO::new).toList();
		if (tutores.size() == 0) {
			throw new EntityNotFoundException();
		}
		return ResponseEntity.ok(tutores);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TutorDetalhamentoDTO> getTutorPeloId(@PathVariable Long id) {
		Tutor tutor = super.buscarEntidade(id, super.tutorRepository(), "tutor");
		return ResponseEntity.ok(new TutorDetalhamentoDTO(tutor));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<TutorDetalhamentoDTO> postCadastrarTutor(@RequestBody @Valid TutorCadastroDTO dadosTutor) {
		Tutor tutorCriado = super.tutorRepository().save(new Tutor(dadosTutor));
		return ResponseEntity.ok(new TutorDetalhamentoDTO(tutorCriado));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<TutorDetalhamentoDTO> putAtualizarTutor(@RequestBody @Valid TutorAlteracaoDTO dadosTutor) {
		return atualizarTutor(dadosTutor);
	}

	@PatchMapping
	@Transactional
	public ResponseEntity<TutorDetalhamentoDTO> patchAtualizarTutor(@RequestBody @Valid TutorAlteracaoDTO dadosTutor) {
		return atualizarTutor(dadosTutor);
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<MensagemDTO> deleteTutor(@PathVariable Long id) {
		Tutor tutor = super.buscarEntidade(id, super.tutorRepository(), "tutor");
		super.tutorRepository().delete(tutor);
		MensagemDTO mensagemDTO = new MensagemDTO(String.format("O tutor com ID %d foi excluído com sucesso.", id));
		return ResponseEntity.ok(mensagemDTO);
	}

	private ResponseEntity<TutorDetalhamentoDTO> atualizarTutor(TutorAlteracaoDTO dadosTutor) {
		Tutor tutor = super.buscarEntidade(dadosTutor.id(), super.tutorRepository(), "tutor");
		Cidade cidade = super.buscarEntidade(dadosTutor.cidadeId(), super.cidadeRepository(), "cidade");
		tutor.atualizarInformações(dadosTutor, cidade);
		return ResponseEntity.ok(new TutorDetalhamentoDTO(tutor));
	}
}
