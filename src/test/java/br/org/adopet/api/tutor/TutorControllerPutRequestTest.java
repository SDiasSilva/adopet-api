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

import br.org.adopet.api.domain.dto.TutorAlteracaoDTO;
import br.org.adopet.api.domain.dto.TutorCadastroDTO;
import br.org.adopet.api.domain.model.Cidade;
import br.org.adopet.api.domain.model.Tutor;
import br.org.adopet.api.domain.repository.CidadeRepository;
import br.org.adopet.api.domain.repository.TutorRepository;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TutorControllerPutRequestTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TutorRepository tutorRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;

	@Test
	public void testPutTutorNomeEmBranco() throws Exception {
		Tutor tutor = tutorRepository
				.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
		TutorAlteracaoDTO tutorAlteracaoDTO = new TutorAlteracaoDTO(tutor.getId(), "", "http://example.com/profile.jpg",
				"1234567890", 1l, "I am a tutor.");
	    String requestBody = new ObjectMapper().writeValueAsString(tutorAlteracaoDTO);
	    mockMvc.perform(put("/tutores")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id", is(tutor.getId().intValue())))
	            .andExpect(jsonPath("$.nome", is(tutor.getContato().getNome())));
	}
	
	@Test
	public void testPutTutorNome() throws Exception {
	    Tutor tutor = tutorRepository.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
	    Cidade cidade = null;
	    TutorAlteracaoDTO tutorAlteracaoDTO = new TutorAlteracaoDTO(tutor.getId(), null, "Updated Name", null, null, null);
	    tutor.atualizarInformações(tutorAlteracaoDTO, cidade);
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
	    Tutor tutor = tutorRepository.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
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
	    Tutor tutor = tutorRepository.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
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
	    Tutor tutor = tutorRepository.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
	    TutorAlteracaoDTO tutorAlteracaoDTO = new TutorAlteracaoDTO(tutor.getId(), null, null, null, 1l, null);
	    Cidade cidade = cidadeRepository.getReferenceById(1l);
	    String requestBody = new ObjectMapper().writeValueAsString(tutorAlteracaoDTO);
	    mockMvc.perform(put("/tutores")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(requestBody))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.id", is(tutor.getId().intValue())))
	            .andExpect(jsonPath("$.cidade.nome", is(cidade.getNome())));
	}

	@Test
	public void testPutTutorDescricao() throws Exception {
	    Tutor tutor = tutorRepository.save(new Tutor(new TutorCadastroDTO("John Doe", "johndoe@example.com", "Password123@")));
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
