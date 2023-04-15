package br.org.adopet.api.tutor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.containsString;
import br.org.adopet.api.repository.TutorRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TutorControllerGetRequest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TutorRepository tutorRepository;
	
	@Test
	void testGetTutorPeloId() throws Exception {
		Long tutorId = 26L;

		mockMvc.perform(get("/tutores/{id}", tutorId)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
	}

	@Test
	void testGetTutorPeloIdNaoEncontrado() throws Exception {
		Long tutorId = 999L;
		mockMvc.perform(get("/tutores/{id}", tutorId))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Não foi encontrado nenhum tutor com este ID.")))
        .andReturn();
	}
	
	@Test
	void testGetTodosTutores() throws Exception {
		mockMvc.perform(get("/tutores")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
	}

	@Test
	void testGetTodosTutoresTabelaVazia() throws Exception {
		tutorRepository.deleteAll();
		mockMvc.perform(get("/tutores"))
        .andExpect(status().isNotFound())
        .andExpect(content().string(containsString("Não foi encontrado nenhum tutor cadastrado no banco de dados.")))
        .andReturn();
	}
}
