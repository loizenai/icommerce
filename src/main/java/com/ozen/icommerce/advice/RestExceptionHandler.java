package com.ozen.icommerce.advice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ozen.icommerce.exception.ApiError;
import com.ozen.icommerce.exception.ICommerceException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ ICommerceException.class })
	public ResponseEntity<Object> handleAll(ICommerceException ex, WebRequest request) {
		final var errorCodeObj = ex.getErrorCode();

		final var errorCode = errorCodeObj.getErrorCode();
		final var message = errorCodeObj.getErrorMessage();
		final var errors = errorCodeObj.getErrors();

		final var apiError = new ApiError<>(errorCode, message, errors);
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.valueOf(errorCodeObj.getHttpStatusCode()));
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
		final var errorCode = "-5000000";
		final var message = ex.getLocalizedMessage();
		final var error = ex.getMessage();

		final var apiError = new ApiError<>(errorCode, message, Arrays.asList(error));
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String errorMessage = "Method Argument Not Valid Exception";
		List<String> validationList = ex.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

		final var errors = new ApiError<>(ICommerceException.BAD_REQUEST_CODE, errorMessage, validationList);
		
		log.info("Validation error list : {} ", validationList);
		return new ResponseEntity<>(errors, new HttpHeaders(), status);
	}
}
