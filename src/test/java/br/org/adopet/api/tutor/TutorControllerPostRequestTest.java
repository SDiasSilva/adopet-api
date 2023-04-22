package br.org.adopet.api.tutor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.org.adopet.api.domain.dto.TutorCadastroDTO;
import br.org.adopet.api.domain.model.Funcao;
import br.org.adopet.api.domain.model.Tutor;
import br.org.adopet.api.domain.model.Usuario;
import br.org.adopet.api.domain.repository.FuncaoRepository;
import br.org.adopet.api.domain.repository.TutorRepository;
import br.org.adopet.api.domain.repository.UsuarioRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TutorControllerPostRequestTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TutorRepository tutorRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private FuncaoRepository funcaoRepository;
	
	private Funcao funcaoTutor;
	
	@BeforeEach
	public void recuperarFuncaoTutor() {
		funcaoTutor = funcaoRepository.findByNome("TUTOR");
	}

	@Test
	void testPostTutorComEmailInvalido() throws Exception {
		TutorCadastroDTO dadosTutor = new TutorCadastroDTO("Sidnei Dias", "emailInvalido", "I@mju5tTesting");
		Usuario usuario = usuarioRepository.save(new Usuario(dadosTutor.email(), dadosTutor.senha(), funcaoTutor));
		mockMvc.perform(
				post("/tutores").contentType(MediaType.APPLICATION_JSON).content(asJsonString(new Tutor(dadosTutor, usuario))))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testPostTutorComSenhaInvalida() throws Exception {
		TutorCadastroDTO dadosTutor = new TutorCadastroDTO("Sidnei Dias", "sidnei@example.com", "senhafraca");
		Usuario usuario = usuarioRepository.save(new Usuario(dadosTutor.email(), dadosTutor.senha(), funcaoTutor));
		mockMvc.perform(
				post("/tutores").contentType(MediaType.APPLICATION_JSON).content(asJsonString(new Tutor(dadosTutor, usuario))))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testPostTutorComEmailJaCadastrado() throws Exception {
		TutorCadastroDTO dadosTutor = new TutorCadastroDTO("Tutor Existente", "tutorexistente@example.com", "I@mju5tTesting");
		Usuario usuario = usuarioRepository.save(new Usuario(dadosTutor.email(), dadosTutor.senha(), funcaoTutor));
		Tutor tutorExistente = new Tutor(dadosTutor, usuario);
		tutorRepository.save(tutorExistente);

		TutorCadastroDTO dadosNovoTutorComEmailRepetido = new TutorCadastroDTO("Novo Tutor", "tutorexistente@example.com", "I@mju5tTesting");
		try {
			mockMvc.perform(post("/tutores").contentType(MediaType.APPLICATION_JSON).content(asJsonString(dadosNovoTutorComEmailRepetido)))
					.andExpect(status().isBadRequest());
		} catch (Exception ex) {
			if (ex.getCause() instanceof DataIntegrityViolationException) {
				assertTrue(ex.getCause().getMessage().contains("email"));
			} else {
				throw ex;
			}
		}
	}

	private String asJsonString(Object obj) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
}
