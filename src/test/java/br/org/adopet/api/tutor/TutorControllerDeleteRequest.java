package br.org.adopet.api.tutor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import br.org.adopet.api.domain.dto.TutorCadastroDTO;
import br.org.adopet.api.domain.model.Tutor;
import br.org.adopet.api.domain.model.Usuario;
import br.org.adopet.api.domain.repository.TutorRepository;
import br.org.adopet.api.domain.repository.UsuarioRepository;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TutorControllerDeleteRequest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TutorRepository tutorRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Test
	public void testDeleteTutorPeloId() throws Exception {
		TutorCadastroDTO dadosTutor = new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@");
		Usuario usuario = usuarioRepository.save(new Usuario(dadosTutor.email(), dadosTutor.senha()));
		Tutor tutor = tutorRepository.save(new Tutor(dadosTutor, usuario));
		mockMvc.perform(delete("/tutores/{id}", tutor.getId()))
			.andExpect(status().isNoContent());
		assertFalse(tutorRepository.existsById(tutor.getId()));
		assertFalse(usuarioRepository.existsById(usuario.getId()));
	}

	@Test
	public void testDeleteTutorPeloIdNaoExistente() throws Exception {
		mockMvc.perform(delete("/tutores/{id}", 999))
			.andExpect(status().isNotFound());
	}

}
