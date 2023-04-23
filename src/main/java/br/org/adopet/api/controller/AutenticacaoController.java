package br.org.adopet.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.org.adopet.api.domain.dto.AutenticacaoDTO;
import br.org.adopet.api.domain.dto.TokenDTO;
import br.org.adopet.api.domain.model.Usuario;
import br.org.adopet.api.infra.exception.LoginException;
import br.org.adopet.api.infra.security.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("login")
public class AutenticacaoController extends BaseController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<TokenDTO> postEfetuarLogin(@RequestBody @Valid AutenticacaoDTO dadosAutenticacao){
		try {
			Authentication authenticationToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.email(),
					dadosAutenticacao.senha());
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			String jwtToken = tokenService.gerarToken((Usuario) authentication.getPrincipal());
			return ResponseEntity.ok(new TokenDTO(jwtToken));
		} catch (AuthenticationException ex) {
			throw new LoginException();
		}
	}
}
