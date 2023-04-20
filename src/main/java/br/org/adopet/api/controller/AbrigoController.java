package br.org.adopet.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
import br.org.adopet.api.domain.repository.AbrigoRepository;
import br.org.adopet.api.domain.repository.CidadeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import br.org.adopet.api.domain.dto.AbrigoCadastroDTO;
import br.org.adopet.api.domain.dto.AbrigoDetalhamentoDTO;
import br.org.adopet.api.domain.dto.MensagemDTO;
import br.org.adopet.api.domain.dto.AbrigoAlteracaoDTO;

@RestController
@RequestMapping("abrigos")
public class AbrigoController {

	@Autowired
	AbrigoRepository abrigoRepository;

	@Autowired
	CidadeRepository cidadeRepository;

	@GetMapping
	public ResponseEntity<List<AbrigoDetalhamentoDTO>> get() {
		List<AbrigoDetalhamentoDTO> abrigos = abrigoRepository.findAll().stream().map(AbrigoDetalhamentoDTO::new).toList();
		if (abrigos.size() == 0) {
			throw new EntityNotFoundException("Nenhum abrigo ");
		}
		return ResponseEntity.ok(abrigos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AbrigoDetalhamentoDTO> get(@PathVariable Long id) {
		Abrigo abrigo = buscarAbrigoPeloId(id);
		return ResponseEntity.ok(new AbrigoDetalhamentoDTO(abrigo));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<AbrigoDetalhamentoDTO> post(@RequestBody @Valid AbrigoCadastroDTO dadosAbrigo) {
		Cidade cidade = buscarCidadePeloId(dadosAbrigo.cidadeId());
		Abrigo abrigoCriado = abrigoRepository.save(new Abrigo(dadosAbrigo, cidade));
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
		Abrigo abrigo = buscarAbrigoPeloId(id);
		abrigoRepository.delete(abrigo);
		MensagemDTO mensagemDTO = new MensagemDTO(String.format("O Abrigo com ID %d foi excluído com sucesso.", id));
		return ResponseEntity.ok(mensagemDTO);
	}
	
	private ResponseEntity<AbrigoDetalhamentoDTO> alterarAbrigo(AbrigoAlteracaoDTO dadosAbrigo) {
		Cidade cidade = buscarCidadePeloId(dadosAbrigo.cidadeId());
		Abrigo abrigo = buscarAbrigoPeloId(dadosAbrigo.id());
		abrigo.atualizarInformacoes(dadosAbrigo, cidade);
		return ResponseEntity.ok(new AbrigoDetalhamentoDTO(abrigo));
	}

	private Abrigo buscarAbrigoPeloId(Long id) {
		return abrigoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
				String.format("O abrigo com ID %d não foi encontrado no banco de dados.", id)));
	}

	private Cidade buscarCidadePeloId(Long id) {
		if (id == null) {
			return null;
		}
		return cidadeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(
				String.format("A cidade com ID %d não foi encontrado no banco de dados.", id)));
	}
}
