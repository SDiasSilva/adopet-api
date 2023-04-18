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

import br.org.adopet.api.domain.dto.TutorCadastroDTO;
import br.org.adopet.api.domain.model.Tutor;
import br.org.adopet.api.domain.repository.TutorRepository;

import static org.hamcrest.Matchers.containsString;

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
		Tutor tutor = tutorRepository.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
		mockMvc.perform(get("/tutores/{id}", tutor.getId())).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
	}

	@Test
	void testGetTutorPeloIdNaoEncontrado() throws Exception {
		Long tutorId = 999L;
		mockMvc.perform(get("/tutores/{id}", tutorId))
        .andExpect(status().isNotFound());
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
