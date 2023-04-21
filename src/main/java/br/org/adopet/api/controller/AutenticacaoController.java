package br.org.adopet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.org.adopet.api.domain.dto.AutenticacaoDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("login")
public class AutenticacaoController extends BaseController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping
	public ResponseEntity postEfetuarLogin(@RequestBody @Valid AutenticacaoDTO dadosAutenticacao) {
		var token = new UsernamePasswordAuthenticationToken(dadosAutenticacao.email(), dadosAutenticacao.senha());
		Authentication autenticacao =  authenticationManager.authenticate(token);
		return ResponseEntity.ok().build();
	}
}
