package br.org.adopet.api.exception;

import jakarta.persistence.PersistenceException;

public class AdocaoException extends PersistenceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static final String MENSAGEM_PADRAO = "O Pet com o ID %d já foi adotado";
    
    public AdocaoException(Long id) {
        super(String.format(MENSAGEM_PADRAO, id));
    }

}
