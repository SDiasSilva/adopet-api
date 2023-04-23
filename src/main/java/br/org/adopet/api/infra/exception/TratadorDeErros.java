package br.org.adopet.api.infra.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.org.adopet.api.domain.dto.ErroDeBuscaDTO;
import br.org.adopet.api.domain.dto.MensagemDTO;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErroDeBuscaDTO> tratarErro404(EntityNotFoundException ex) {
		String mensagem = ex.getMessage();
		if (mensagem == null || mensagem.trim().isBlank()) {
			mensagem = "Nada foi encontrado no banco de dados.";
		}
		ErroDeBuscaDTO erroDeBuscaDTO = new ErroDeBuscaDTO(mensagem);
		return new ResponseEntity<ErroDeBuscaDTO>(erroDeBuscaDTO, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
		List<FieldError> erros = ex.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ErroDeBuscaDTO> tratarErroDeIntegridade(SQLIntegrityConstraintViolationException ex) {
		ErroDeBuscaDTO erroDeBuscaDTO = new ErroDeBuscaDTO(ex.getMessage());
		return ResponseEntity.badRequest().body(erroDeBuscaDTO);
	}

	@ExceptionHandler(AdocaoException.class)
	public ResponseEntity<MensagemDTO> tratarPetJaAdotado(AdocaoException ex) {
		MensagemDTO mensagemDTO = new MensagemDTO(ex.getMessage());
		return new ResponseEntity<MensagemDTO>(mensagemDTO, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(LoginException.class)
	public ResponseEntity<MensagemDTO> tratarLoginInvalido(LoginException ex) {
		MensagemDTO mensagemDTO = new MensagemDTO(ex.getMessage());
		return new ResponseEntity<MensagemDTO>(mensagemDTO, HttpStatus.FORBIDDEN);
	}

	private record DadosErroValidacao(String campo, String mensagem) {
		public DadosErroValidacao(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}
}
