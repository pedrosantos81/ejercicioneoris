package com.excepcion;

import java.util.List;
import java.util.Map;

public class ErrorMensaje {

	private int status;
	private String message;
	private String error;
	private Map<String,Object> listaerrores;
	
	public ErrorMensaje(int status, String message,String error) {
		super();
		this.status = status;
		this.message = message;
		this.error = error;
	}
	
	public ErrorMensaje(int status, Map<String,Object> listaerrores,String error,String message) {
		super();
		this.status = status;
		this.error = error;
		this.listaerrores=listaerrores;
		this.message=message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Map<String, Object> getListaerrores() {
		return listaerrores;
	}

	public void setListaerrores(Map<String, Object> listaerrores) {
		this.listaerrores = listaerrores;
	}

}
