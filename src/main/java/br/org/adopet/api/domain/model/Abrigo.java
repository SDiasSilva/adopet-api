package br.org.adopet.api.domain.model;

import br.org.adopet.api.domain.dto.AbrigoCadastroDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import br.org.adopet.api.domain.dto.AbrigoAlteracaoDTO;
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

@Entity
@Table(name = "abrigos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Abrigo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Embedded
	private Contato contato;
	@ManyToOne
	private Cidade cidade;
	@OneToMany(mappedBy = "abrigo")
	private List<Pet> pets = new ArrayList<Pet>();

	public Abrigo(AbrigoCadastroDTO dadosAbrigo, Cidade cidade) {
		this.contato = new Contato(dadosAbrigo.nome(), dadosAbrigo.email(), dadosAbrigo.telefone());
		this.cidade = cidade;
	}

	public void atualizarInformacoes(AbrigoAlteracaoDTO dadosAbrigo, Cidade cidade) {
		if (dadosAbrigo.nome() != null && !dadosAbrigo.nome().trim().isBlank()) {
			this.contato.setNome(dadosAbrigo.nome());
		}
		if (dadosAbrigo.email() != null && !dadosAbrigo.email().trim().isBlank()) {
			this.contato.setEmail(dadosAbrigo.email());
		}
		if (dadosAbrigo.telefone() != null && !dadosAbrigo.telefone().trim().isBlank()) {
			this.contato.setTelefone(dadosAbrigo.telefone());
		}
		if (cidade != null) {
			this.cidade = cidade;
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
	
	public void adicionarPet(Pet pet) {
		this.pets.add(pet);
	}
}
