package br.org.adopet.api.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import br.org.adopet.api.domain.dto.TutorAlteracaoDTO;
import br.org.adopet.api.domain.dto.TutorCadastroDTO;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	@OneToMany(mappedBy = "tutor")
	private List<Adocao> adocoes = new ArrayList<Adocao>();

	public Tutor(TutorCadastroDTO dadosTutor) {
		this.contato = new Contato(dadosTutor.nome(), dadosTutor.email());
		this.senha = dadosTutor.senha();
	}

	public void atualizarInformações(TutorAlteracaoDTO dadosTutor, Cidade cidade) {
		if (dadosTutor.foto() != null && !dadosTutor.foto().trim().isBlank()) {
			this.foto = dadosTutor.foto();
		}
		if (dadosTutor.nome() != null && !dadosTutor.nome().trim().isBlank()) {
			this.contato.setNome(dadosTutor.nome());
		}
		if (dadosTutor.telefone() != null && !dadosTutor.telefone().trim().isBlank()) {
			this.contato.setTelefone(dadosTutor.telefone());
		}
		if (cidade != null) {
			this.cidade = cidade;
		}
		if (dadosTutor.sobre() != null && !dadosTutor.sobre().trim().isBlank()) {
			this.sobre = dadosTutor.sobre();
		}
	}

	public String getNomeCidade() {
		return getCidadeAtributo(Cidade::getNome);
	}

	public String getEstado() {
		return getCidadeAtributo(cidade -> cidade.getEstado().getSigla());
	}

	public String getNome() {
		return this.contato.getNome();
	}
	
	public String getTelefone() {
		return this.contato.getTelefone();
	}

	public String getEmail() {
		return this.contato.getEmail();
	}
	
	private String getCidadeAtributo(Function<Cidade, String> atributoGetter) {
		if (this.cidade == null) {
			return null;
		}
		return atributoGetter.apply(this.cidade);
	}
	
	public void adicionarAdocao(Adocao adocao) {
		this.adocoes.add(adocao);
	}

	public void removerAdocao(Adocao adocao) {
		this.adocoes.remove(adocao);
	}
}
