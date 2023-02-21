package com.excepcion;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GestionExcepciones extends ResponseEntityExceptionHandler {

	public static final Logger LOGGER = LoggerFactory.getLogger(GestionExcepciones.class);

	@ExceptionHandler(ClienteNotFound.class)
	public ResponseEntity<ErrorMensaje> manejaError(ClienteNotFound exc) {
		LOGGER.debug("ClienteNotFound thrown", exc);
		ErrorMensaje errormsg = new ErrorMensaje(HttpStatus.NOT_FOUND.value(), exc.getMessage(), "No encontrado");
		return new ResponseEntity<ErrorMensaje>(errormsg, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ManejoCuentaExcepcion.class)
	public ResponseEntity<ErrorMensaje> manejaError(ManejoCuentaExcepcion exc) {
		LOGGER.debug("ManejoCuentaExcepcion thrown", exc);
		ErrorMensaje errormsg = new ErrorMensaje(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), "Mala peticion");
		return new ResponseEntity<ErrorMensaje>(errormsg, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorMensaje> manejaError(NoSuchElementException exc) {
		LOGGER.debug("NoSuchElementException thrown", exc);
		ErrorMensaje errormsg = new ErrorMensaje(HttpStatus.NOT_FOUND.value(), exc.getMessage(),
				"No encontrado en la bd");
		return new ResponseEntity<ErrorMensaje>(errormsg, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(javax.validation.UnexpectedTypeException.class)
	public ResponseEntity<ErrorMensaje> errorvalidacion(UnexpectedTypeException exc) {
		LOGGER.debug("UnexpectedTypeException thrown", exc);
		ErrorMensaje errormsg = new ErrorMensaje(HttpStatus.BAD_REQUEST.value(), exc.getMessage(),
				"Faltan campos");
		return new ResponseEntity<ErrorMensaje>(errormsg, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleValidationError2(ConstraintViolationException exception) {
		LOGGER.debug("ConstraintViolationException thrown", exception);

		String str = exception.getConstraintViolations().stream()
				.map(err -> err.getPropertyPath().toString()
						.substring(err.getPropertyPath().toString().lastIndexOf('.') + 1) + " " + err.getMessage())
				.collect(Collectors.joining(";"));

		return new ResponseEntity<Object>(
				new ErrorMensaje(HttpStatus.BAD_REQUEST.value(), str, "Error al insertar en la bd"),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOGGER.debug("MethodArgumentNotValidException thrown", ex);
		Map<String, Object> response = new HashMap<>();
		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> "El campo '" + err.getField() + " " + err.getDefaultMessage()).collect(Collectors.toList());

		String typeRequest = "";

		LOGGER.debug(((ServletWebRequest) request).getHttpMethod().toString());
		if (((ServletWebRequest) request).getHttpMethod().toString().equals("POST")) {
			typeRequest = "Error al realizar el insert en la base de datos";
		} else if (((ServletWebRequest) request).getHttpMethod().toString().equals("PUT")) {
			typeRequest = "Error al actualizar en la base de datos";
		}

		response.put("errors", errors);

		return new ResponseEntity<Object>(
				new ErrorMensaje(HttpStatus.BAD_REQUEST.value(), response, typeRequest, ex.getLocalizedMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOGGER.debug("HttpMessageNotReadableException thrown", ex);
		ErrorMensaje errormsg = new ErrorMensaje(HttpStatus.NOT_ACCEPTABLE.value(), ex.getHttpInputMessage().toString(),
				ex.getLocalizedMessage());

		return new ResponseEntity<Object>(errormsg, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(value = {CannotCreateTransactionException.class})
    public ResponseEntity<?> cannotCreateTransactionException(CannotCreateTransactionException exception, WebRequest request) {
		LOGGER.debug("CannotCreateTransactionException thrown", exception);
        if (exception.contains(ConnectException.class)) {
            LOGGER.error("DB ConnectException :  {}", exception.getMessage());
            ErrorMensaje errormsg = new ErrorMensaje(HttpStatus.SERVICE_UNAVAILABLE.value(),"DB ConnectException : " +exception.getMessage(),exception.getMessage());
            return new ResponseEntity<Object>(errormsg,HttpStatus.SERVICE_UNAVAILABLE);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
