package com.example.Testify.Exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.Testify.util.ResponseStructure;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<ObjectError> objectErrors = ex.getAllErrors();
		Map<String, String> map = new LinkedHashMap<>();

		for (ObjectError objectError : objectErrors) {
			String field = ((FieldError) objectError).getField();
			String message = objectError.getDefaultMessage();
			map.put(field, message);
		}

		ResponseStructure<Map<String, String>> responseStructure = new ResponseStructure<Map<String, String>>();
		responseStructure.setStatus(HttpStatus.BAD_REQUEST.value());
		responseStructure.setMessage(HttpStatus.BAD_REQUEST.name());
		responseStructure.setData(map);

		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleInvaliIdException(IdNotFoundException exception) {
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();

		responseStructure.setStatus(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage("id not found");
		responseStructure.setData(exception.getMessage());

		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	
}


