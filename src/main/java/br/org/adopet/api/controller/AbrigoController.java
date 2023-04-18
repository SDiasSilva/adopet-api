package br.org.adopet.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

@RestController
@RequestMapping("abrigos")
public class AbrigoController {
	
	@Autowired
	AbrigoRepository abrigoRepository;
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	@GetMapping
	public ResponseEntity<List<AbrigoListagemDTO>> get(){
		List<AbrigoListagemDTO> abrigos = abrigoRepository.findAll().stream().map(AbrigoListagemDTO::new).toList();
		if(abrigos.size() == 0) {
			throw new EntityNotFoundException();
		}
		return ResponseEntity.ok(abrigos);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<AbrigoListagemDTO> post(@RequestBody @Valid AbrigoCadastroDTO dadosAbrigo){
		Cidade cidade = cidadeRepository.getReferenceById(dadosAbrigo.cidadeId());
		Abrigo abrigoCriado = abrigoRepository.save(new Abrigo(dadosAbrigo, cidade));
		return ResponseEntity.ok(new AbrigoListagemDTO(abrigoCriado));
	}
	
}
