package com.sistemaspedidos.resources.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sistemaspedidos.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> handlerObjectNotFoundException(ObjectNotFoundException e,
			                                                            HttpServletRequest request){
	
		StandardError erro = new StandardError();
		erro.setTitulo(e.getMessage());
		erro.setStatus(404l);
		erro.setMensagemDesenvolvedor("http://erros.sistemapedidos.com/404");
		erro.setTime(System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
}
