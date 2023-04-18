package br.org.adopet.api.domain.model;

import br.org.adopet.api.domain.dto.TutorAlteracaoDTO;
import br.org.adopet.api.domain.dto.TutorCadastroDTO;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
	@Embedded
	private Contato contato;
	private String senha;
	private String foto;
	@ManyToOne
	private Cidade cidade;
	private String sobre;

	public Tutor(TutorCadastroDTO dadosTutor) {
		this.contato = new Contato(dadosTutor.nome(), dadosTutor.email());
		this.senha = dadosTutor.senha();
	}

	public void atualizarInformações(TutorAlteracaoDTO dadosTutor) {
		if (dadosTutor.foto() != null && !dadosTutor.foto().trim().isBlank()) {
			this.foto = dadosTutor.foto();
		}
		if (dadosTutor.nome() != null && !dadosTutor.nome().trim().isBlank()) {
			this.contato.setNome(dadosTutor.nome());
		}
		if (dadosTutor.telefone() != null && !dadosTutor.telefone().trim().isBlank()) {
			this.contato.setTelefone(dadosTutor.telefone());
		}
		if (dadosTutor.cidade() != null) {
			this.cidade = dadosTutor.cidade();
		}
		if (dadosTutor.sobre() != null && !dadosTutor.sobre().trim().isBlank()) {
			this.sobre = dadosTutor.sobre();
		}
	}
}
