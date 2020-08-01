package com.sistemaspedidos.resources.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sistemaspedidos.services.exceptions.AuthorizationException;
import com.sistemaspedidos.services.exceptions.DataIntegrityException;
import com.sistemaspedidos.services.exceptions.ObjectNotFoundException;
import com.sistemaspedidos.services.exceptions.ValidationError;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> handlerObjectNotFoundException(ObjectNotFoundException e,
			                                                            HttpServletRequest request){
	
		StandardError erro = new StandardError();
		erro.setTimestamp(System.currentTimeMillis());
		erro.setStatus(404);
		erro.setError("Não encontrado");
		erro.setMessage(e.getMessage());
		erro.setPath(request.getRequestURI());
		erro.setMensagemDesenvolvedor("http://erros.sistemapedidos.com/404");
		
		
	
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrityException(DataIntegrityException e,
			                                                            HttpServletRequest request){
		
		
		StandardError erro = new StandardError();
		erro.setTimestamp(System.currentTimeMillis());
		erro.setStatus(400);
		erro.setError("Integridade de dados");
		erro.setMessage(e.getMessage());
		erro.setPath(request.getRequestURI());
		erro.setMensagemDesenvolvedor("http://erros.sistemapedidos.com/400");
	
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e,
			                                                            HttpServletRequest request){
	
		ValidationError erro = new ValidationError();
		
	   for(FieldError x : e.getBindingResult().getFieldErrors()) {
		   erro.addError(x.getField(), x.getDefaultMessage());
	   }
			
	   
		erro.setTimestamp(System.currentTimeMillis());
		erro.setStatus(422);
		erro.setError("Erro de Validação");
		erro.setMessage(e.getMessage());
		erro.setPath(request.getRequestURI());
		erro.setMensagemDesenvolvedor("http://erros.sistemapedidos.com/422");
		
		
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(erro);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e,
			                                                            HttpServletRequest request){
	
		StandardError erro = new StandardError();
		
		 
		erro.setTimestamp(System.currentTimeMillis());
		erro.setStatus(403);
		erro.setError("Acesso negado");
		erro.setMessage(e.getMessage());
		erro.setPath(request.getRequestURI());
		erro.setMensagemDesenvolvedor("http://erros.sistemapedidos.com/403");
		
		
	
		
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
	}
	
}
