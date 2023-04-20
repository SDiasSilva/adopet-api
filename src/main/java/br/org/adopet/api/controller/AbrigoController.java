package br.org.adopet.api.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.org.adopet.api.domain.model.Abrigo;
import br.org.adopet.api.domain.model.Cidade;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import br.org.adopet.api.domain.dto.AbrigoCadastroDTO;
import br.org.adopet.api.domain.dto.AbrigoDetalhamentoDTO;
import br.org.adopet.api.domain.dto.MensagemDTO;
import br.org.adopet.api.domain.dto.AbrigoAlteracaoDTO;

@RestController
@RequestMapping("abrigos")
public class AbrigoController extends BaseController{

	@GetMapping
	public ResponseEntity<List<AbrigoDetalhamentoDTO>> get() {
		List<AbrigoDetalhamentoDTO> abrigos = super.abrigoRepository().findAll().stream().map(AbrigoDetalhamentoDTO::new).toList();
		if (abrigos.size() == 0) {
			throw new EntityNotFoundException();
		}
		return ResponseEntity.ok(abrigos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AbrigoDetalhamentoDTO> get(@PathVariable Long id) {
		Abrigo abrigo = super.buscarEntidade(id, super.abrigoRepository(), "abrigo");
		return ResponseEntity.ok(new AbrigoDetalhamentoDTO(abrigo));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<AbrigoDetalhamentoDTO> post(@RequestBody @Valid AbrigoCadastroDTO dadosAbrigo) {
		Cidade cidade = super.buscarEntidade(dadosAbrigo.cidadeId(), super.cidadeRepository(), "cidade");
		Abrigo abrigoCriado = super.abrigoRepository().save(new Abrigo(dadosAbrigo, cidade));
		return ResponseEntity.ok(new AbrigoDetalhamentoDTO(abrigoCriado));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<AbrigoDetalhamentoDTO> put(@RequestBody @Valid AbrigoAlteracaoDTO dadosAbrigo) {
		return alterarAbrigo(dadosAbrigo);
	}
	
	@PatchMapping
	@Transactional
	public ResponseEntity<AbrigoDetalhamentoDTO> patch(@RequestBody @Valid AbrigoAlteracaoDTO dadosAbrigo) {
		return alterarAbrigo(dadosAbrigo);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MensagemDTO> delete(@PathVariable Long id){
		Abrigo abrigo = super.buscarEntidade(id, super.abrigoRepository(), "abrigo");
		super.abrigoRepository().delete(abrigo);
		MensagemDTO mensagemDTO = new MensagemDTO(String.format("O Abrigo com ID %d foi excluído com sucesso.", id));
		return ResponseEntity.ok(mensagemDTO);
	}
	
	private ResponseEntity<AbrigoDetalhamentoDTO> alterarAbrigo(AbrigoAlteracaoDTO dadosAbrigo) {
		Cidade cidade = super.buscarEntidade(dadosAbrigo.cidadeId(), super.cidadeRepository(), "cidade");
		Abrigo abrigo = super.buscarEntidade(dadosAbrigo.id(), super.abrigoRepository(), "abrigo");
		abrigo.atualizarInformacoes(dadosAbrigo, cidade);
		return ResponseEntity.ok(new AbrigoDetalhamentoDTO(abrigo));
	}
}
