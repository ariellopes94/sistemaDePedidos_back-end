package com.sistemaspedidos.resources.handler;

import java.io.Serializable;

public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String titulo;
	private Long status;
	private Long time;
	private String mensagemDesenvolvedor;
	
	public StandardError() {
	}
	
	public StandardError(String titulo, Long status, Long time, String mensagemDesenvolvedor) {
		super();
		this.titulo = titulo;
		this.status = status;
		this.time = time;
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getMensagemDesenvolvedor() {
		return mensagemDesenvolvedor;
	}
	public void setMensagemDesenvolvedor(String mensagemDesenvolvedor) {
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
	}
	
	
}
