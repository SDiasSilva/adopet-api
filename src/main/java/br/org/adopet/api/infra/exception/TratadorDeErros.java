package br.org.adopet.api.infra.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import br.org.adopet.api.domain.dto.ErroDTO;
import br.org.adopet.api.domain.dto.MensagemDTO;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErroDTO> tratarErro404(EntityNotFoundException ex) {
		String mensagem = ex.getMessage();
		if (mensagem == null || mensagem.trim().isBlank()) {
			mensagem = "Nada foi encontrado no banco de dados.";
		}
		ErroDTO erroDeBuscaDTO = new ErroDTO(mensagem);
		return new ResponseEntity<ErroDTO>(erroDeBuscaDTO, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<DadosErroValidacao>> tratarErro400(MethodArgumentNotValidException ex) {
		List<FieldError> erros = ex.getFieldErrors();
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<ErroDTO> tratarErroDeIntegridade(SQLIntegrityConstraintViolationException ex) {
		ErroDTO erroDeBuscaDTO = new ErroDTO(ex.getMessage());
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

	@ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MensagemDTO> tratarErroBadCredentials() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MensagemDTO("Credenciais inválidas"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MensagemDTO> tratarErroAcessoNegado() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MensagemDTO("Acesso negado"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> tratarErro500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErroDTO(ex.getLocalizedMessage()));
    }
	
	private record DadosErroValidacao(String campo, String mensagem) {
		public DadosErroValidacao(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}
}
