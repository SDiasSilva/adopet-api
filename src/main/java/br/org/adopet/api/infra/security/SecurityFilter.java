package br.org.adopet.api.infra.security;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import br.org.adopet.api.domain.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken = recuperarToken(request);
		if (jwtToken != null) {
			String subject = tokenService.getSubject(jwtToken);
			UserDetails usuario = usuarioRepository.findByEmail(subject);
			Authentication authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication );
		}
		filterChain.doFilter(request, response);
	}

	private String recuperarToken(HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		if (authorizationHeader != null) {
			return authorizationHeader.replace("Bearer ", "");
		}
		return null;
	}

}
