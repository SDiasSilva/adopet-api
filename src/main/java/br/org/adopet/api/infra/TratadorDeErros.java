package br.org.adopet.api.infra;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<String> tratarErro404() {
		String mensagem = "Nada foi encontrado no banco de dados.";
		return new ResponseEntity<String>(mensagem, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
		List<FieldError> erros = ex.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
	}
	
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<String> tratarErroDeIntegridade(SQLIntegrityConstraintViolationException ex) {
		String sqlState = ex.getMessage();
		return ResponseEntity.badRequest().body(sqlState);
	}
	
	private record DadosErroValidacao(String campo, String mensagem) {
		public DadosErroValidacao(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}
}
