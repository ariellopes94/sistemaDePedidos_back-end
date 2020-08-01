package com.sistemaspedidos.services.exceptions;

import java.util.ArrayList;
import java.util.List;

import com.sistemaspedidos.resources.handler.StandardError;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> errors = new ArrayList<>();

	public ValidationError() {
	}
	


	public ValidationError(Long timestamp, Integer status, String error, String message, String path,
			String mensagemDesenvolvedor) {
		super(timestamp, status, error, message, path, mensagemDesenvolvedor);
	}



	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}
	
}
