package br.org.adopet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import br.org.adopet.api.domain.dto.AbrigoListagemDTO;
import br.org.adopet.api.domain.dto.AbrigoAlteracaoDTO;

@RestController
@RequestMapping("abrigos")
public class AbrigoController {

	@Autowired
	AbrigoRepository abrigoRepository;

	@Autowired
	CidadeRepository cidadeRepository;

	@GetMapping
	public ResponseEntity<List<AbrigoListagemDTO>> get() {
		List<AbrigoListagemDTO> abrigos = abrigoRepository.findAll().stream().map(AbrigoListagemDTO::new).toList();
		if (abrigos.size() == 0) {
			throw new EntityNotFoundException();
		}
		return ResponseEntity.ok(abrigos);
	}

	@GetMapping({ "/{id}" })
	public ResponseEntity<AbrigoListagemDTO> get(@PathVariable Long id) {
		Abrigo abrigo = abrigoRepository.getReferenceById(id);
		return ResponseEntity.ok(new AbrigoListagemDTO(abrigo));
	}

	@PostMapping
	@Transactional
	public ResponseEntity<AbrigoListagemDTO> post(@RequestBody @Valid AbrigoCadastroDTO dadosAbrigo) {
		Cidade cidade = cidadeRepository.getReferenceById(dadosAbrigo.cidadeId());
		Abrigo abrigoCriado = abrigoRepository.save(new Abrigo(dadosAbrigo, cidade));
		return ResponseEntity.ok(new AbrigoListagemDTO(abrigoCriado));
	}

	@PutMapping
	@Transactional
	public ResponseEntity<AbrigoListagemDTO> put(@RequestBody @Valid AbrigoAlteracaoDTO dadosAbrigo) {
		return alterarAbrigo(dadosAbrigo);
	}
	
	@PatchMapping
	@Transactional
	public ResponseEntity<AbrigoListagemDTO> patch(@RequestBody @Valid AbrigoAlteracaoDTO dadosAbrigo) {
		return alterarAbrigo(dadosAbrigo);
	}

	private Cidade verificarCidadeId(Long id) {
		Cidade cidade = null;
		if (id != null) {
			cidade = cidadeRepository.getReferenceById(id);
		}
		return cidade;
	}

	private ResponseEntity<AbrigoListagemDTO> alterarAbrigo(AbrigoAlteracaoDTO dadosAbrigo) {
		Cidade cidade = verificarCidadeId(dadosAbrigo.cidadeId());
		Abrigo abrigo = abrigoRepository.getReferenceById(dadosAbrigo.id());
		abrigo.atualizarInformacoes(dadosAbrigo, cidade);
		return ResponseEntity.ok(new AbrigoListagemDTO(abrigo));
	}

}
