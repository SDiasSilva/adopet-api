package br.org.adopet.api.tutor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import br.org.adopet.api.dto.TutorCadastroDTO;
import br.org.adopet.api.model.Tutor;
import br.org.adopet.api.repository.TutorRepository;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.hamcrest.Matchers.containsString;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TutorControllerDeleteRequest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TutorRepository tutorRepository;

	@Test
	public void testDeleteTutorPeloId() throws Exception {
		Tutor tutor = tutorRepository.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
		mockMvc.perform(delete("/tutores/{id}", tutor.getId()))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("deletado com sucesso")));
		assertFalse(tutorRepository.existsById(tutor.getId()));
	}

	@Test
	public void testDeleteTutorPeloIdNaoExistente() throws Exception {
		mockMvc.perform(delete("/tutores/{id}", 999))
			.andExpect(status().isNotFound())
			.andExpect(content().string(containsString("não existe, portanto não pode ser deletado")));
	}

}
