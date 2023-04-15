package br.org.adopet.api.model;

import br.org.adopet.api.dto.TutorAlteracaoDTO;
import br.org.adopet.api.dto.TutorCadastroDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "Tutor")
@Table(name = "tutores")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Tutor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String foto;
	private String telefone;
	private String cidade;
	private String sobre;

	public Tutor(TutorCadastroDTO dadosTutor) {
		this.nome = dadosTutor.nome();
		this.email = dadosTutor.email();
		this.senha = dadosTutor.senha();
	}

	public void atualizarInformações(TutorAlteracaoDTO dadosTutor) {
		if (dadosTutor.foto() != null && !dadosTutor.foto().trim().isBlank()) {
			this.foto = dadosTutor.foto();
		}
		if (dadosTutor.nome() != null && !dadosTutor.nome().trim().isBlank()) {
			this.nome = dadosTutor.nome();
		}
		if (dadosTutor.telefone() != null && !dadosTutor.telefone().trim().isBlank()) {
			this.telefone = dadosTutor.telefone();
		}
		if (dadosTutor.cidade() != null && !dadosTutor.cidade().trim().isBlank()) {
			this.cidade = dadosTutor.cidade();
		}
		if (dadosTutor.sobre() != null && !dadosTutor.sobre().trim().isBlank()) {
			this.sobre = dadosTutor.sobre();
		}
	}
}
