package br.org.adopet.api.tutor;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import br.org.adopet.api.dto.TutorAlteracaoDTO;
import br.org.adopet.api.dto.TutorCadastroDTO;
import br.org.adopet.api.model.Tutor;
import br.org.adopet.api.repository.TutorRepository;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TutorControllerPutRequestTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TutorRepository repository;

	@Test
	public void testPutTutorNomeEmBranco() throws Exception {
		Tutor tutor = repository
				.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
		TutorAlteracaoDTO tutorAlteracaoDTO = new TutorAlteracaoDTO(tutor.getId(), "", "http://example.com/profile.jpg",
				"1234567890", "New York", "I am a tutor.");
	    String requestBody = new ObjectMapper().writeValueAsString(tutorAlteracaoDTO);
	    mockMvc.perform(put("/tutores")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id", is(tutor.getId().intValue())))
	            .andExpect(jsonPath("$.nome", is(tutor.getNome())));
	}
	
	@Test
	public void testPutTutorNome() throws Exception {
	    Tutor tutor = repository.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
	    TutorAlteracaoDTO tutorAlteracaoDTO = new TutorAlteracaoDTO(tutor.getId(), null, "Updated Name", null, null, null);
	    tutor.atualizarInformações(tutorAlteracaoDTO);
	    String requestBody = new ObjectMapper().writeValueAsString(tutor);
	    mockMvc.perform(put("/tutores")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id", is(tutor.getId().intValue())))
	            .andExpect(jsonPath("$.nome", is("Updated Name")));
	}


	@Test
	public void testPutTutorFotoUrl() throws Exception {
	    Tutor tutor = repository.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
	    TutorAlteracaoDTO tutorAlteracaoDTO = new TutorAlteracaoDTO(tutor.getId(), "http://example.com/profile.jpg", null, null, null, null);
	    String requestBody = new ObjectMapper().writeValueAsString(tutorAlteracaoDTO);
	    mockMvc.perform(put("/tutores")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id", is(tutor.getId().intValue())))
	            .andExpect(jsonPath("$.foto", is("http://example.com/profile.jpg")));
	}
	
	@Test
	public void testPutTutorTelefone() throws Exception {
	    Tutor tutor = repository.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
	    TutorAlteracaoDTO tutorAlteracaoDTO = new TutorAlteracaoDTO(tutor.getId(), null, null, "9876543210", null, null);
	    String requestBody = new ObjectMapper().writeValueAsString(tutorAlteracaoDTO);
	    mockMvc.perform(put("/tutores")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id", is(tutor.getId().intValue())))
	            .andExpect(jsonPath("$.telefone", is("9876543210")));
	}
	
	@Test
	public void testPutTutorCidade() throws Exception {
	    Tutor tutor = repository.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
	    TutorAlteracaoDTO tutorAlteracaoDTO = new TutorAlteracaoDTO(tutor.getId(), null, null, null, "New City", null);
	    String requestBody = new ObjectMapper().writeValueAsString(tutorAlteracaoDTO);
	    mockMvc.perform(put("/tutores")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id", is(tutor.getId().intValue())))
	            .andExpect(jsonPath("$.cidade", is("New City")));
	}

	@Test
	public void testPutTutorDescricao() throws Exception {
	    Tutor tutor = repository.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
	    TutorAlteracaoDTO tutorAlteracaoDTO = new TutorAlteracaoDTO(tutor.getId(), null, null, null, null, "Lorem Ipsum");
	    String requestBody = new ObjectMapper().writeValueAsString(tutorAlteracaoDTO);
	    mockMvc.perform(put("/tutores")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id", is(tutor.getId().intValue())))
	            .andExpect(jsonPath("$.sobre", is("Lorem Ipsum")));
	}


}
