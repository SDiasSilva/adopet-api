package br.org.adopet.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.org.adopet.api.domain.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	UserDetails findByEmail(String email);

}
