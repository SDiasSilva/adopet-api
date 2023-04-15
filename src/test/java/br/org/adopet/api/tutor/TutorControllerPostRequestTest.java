package br.org.adopet.api.tutor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import br.org.adopet.api.dto.TutorCadastroDTO;
import br.org.adopet.api.model.Tutor;
import br.org.adopet.api.repository.TutorRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TutorControllerPostRequestTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TutorRepository tutorRepository;

	@Test
	void testPostTutorComEmailInvalido() throws Exception {
		TutorCadastroDTO dadosTutor = new TutorCadastroDTO("Sidnei Dias", "emailInvalido", "I@mju5tTesting");
		mockMvc.perform(
				post("/tutores").contentType(MediaType.APPLICATION_JSON).content(asJsonString(new Tutor(dadosTutor))))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testPostTutorComSenhaInvalida() throws Exception {
		TutorCadastroDTO dadosTutor = new TutorCadastroDTO("Sidnei Dias", "sidnei@example.com", "senhafraca");
		mockMvc.perform(
				post("/tutores").contentType(MediaType.APPLICATION_JSON).content(asJsonString(new Tutor(dadosTutor))))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testPostTutorComEmailJaCadastrado() throws Exception {
		Tutor tutorExistente = new Tutor(new TutorCadastroDTO("Tutor Existente", "tutorexistente@example.com", "I@mju5tTesting"));
		tutorRepository.save(tutorExistente);

		TutorCadastroDTO dadosTutor = new TutorCadastroDTO("Novo Tutor", "tutorexistente@example.com", "I@mju5tTesting");
		try {
			mockMvc.perform(post("/tutores").contentType(MediaType.APPLICATION_JSON).content(asJsonString(dadosTutor)))
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
