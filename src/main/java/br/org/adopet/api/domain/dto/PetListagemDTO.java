package br.org.adopet.api.domain.dto;

import java.time.LocalDate;
import java.time.Period;

import br.org.adopet.api.domain.model.Pet;

public record PetListagemDTO(Long id, Long abrigoId, String nome, String porte, String descricao, Boolean adotado,
		String idade, String cidade, String estado, String foto) {

	public PetListagemDTO(Pet pet) {
		this(pet.getId(), pet.getAbrigo().getId(), pet.getNome(), pet.getPorte().getDescricao(), pet.getDescricao(),
				pet.getAdotado(), getIdade(pet.getDataNascimento()), pet.getAbrigo().getCidade().getNome(),
				pet.getAbrigo().getCidade().getEstado().getSigla(), pet.getFoto());
	}

	private static String getIdade(LocalDate dataNascimento) {
		LocalDate hoje = LocalDate.now();
		Period periodo = Period.between(dataNascimento, hoje);
		int anos = periodo.getYears();
		int meses = periodo.getMonths();
		int dias = periodo.getDays();
		if (anos > 0) {
			return anos + (anos == 1 ? " ano" : " anos");
		} else if (meses > 0) {
			return meses + (meses == 1 ? " mês" : " meses");
		} else {
			return dias + (dias == 1 ? " dia" : " dias");
		}
	}
}
