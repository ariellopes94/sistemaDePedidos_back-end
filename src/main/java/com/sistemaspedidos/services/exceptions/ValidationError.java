package com.sistemaspedidos.services.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.sistemaspedidos.resources.handler.StandardError;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError() {
	}
	
	public ValidationError(String titulo , Long status, Long time, String mensagemDesenvolvedor) {
		super(titulo, status, time, mensagemDesenvolvedor);
		
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}
	
}
