package br.org.adopet.api.domain.dto;

import br.org.adopet.api.domain.model.Abrigo;

public record AbrigoListagemDTO(Long id, String nome, String email, String telefone, CidadeListagemDTO cidade) {
	public AbrigoListagemDTO(Abrigo abrigo){
		this(abrigo.getId(), abrigo.getContato().getNome(), abrigo.getContato().getEmail(), abrigo.getContato().getTelefone(), new CidadeListagemDTO(abrigo.getCidade()));
	}
}
