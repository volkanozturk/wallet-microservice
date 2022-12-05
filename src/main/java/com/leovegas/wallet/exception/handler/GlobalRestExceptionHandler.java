package com.leovegas.wallet.exception.handler;

import com.leovegas.wallet.exception.BalanceInsufficientException;
import com.leovegas.wallet.exception.NonUniqueTransactionException;
import com.leovegas.wallet.exception.WalletNotFoundException;
import com.leovegas.wallet.message.MessageUtilityServiceImpl;
import com.leovegas.wallet.shared.GenericResponse;
import com.leovegas.wallet.utility.CommonUtility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.MethodNotAllowedException;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author volkanozturk
 */
@ControllerAdvice
public class GlobalRestExceptionHandler {
	private static String RETRY_AFTER_MINUTES = "5";

	@ExceptionHandler(value = BalanceInsufficientException.class)
	public ResponseEntity<GenericResponse> handleBalanceInsufficientException(BalanceInsufficientException ex) {
		return responseEntity(ex, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = NonUniqueTransactionException.class)
	public ResponseEntity<GenericResponse> handleNonUniqueTransactionException(NonUniqueTransactionException ex) {
		return responseEntity(ex, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = WalletNotFoundException.class)
	public ResponseEntity<GenericResponse> handleWalletNotFoundException(WalletNotFoundException ex) {
		return responseEntity(ex, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<GenericResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		String locale = CommonUtility.getHeaderValue("locale");
		GenericResponse genericResponse = new GenericResponse<>();
		genericResponse.setSuccessful(false);
		genericResponse.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
		genericResponse.setMessage(MessageUtilityServiceImpl.getMessage("customException.methodArgumentNotValid.message", Objects.nonNull(locale) ? new Locale(locale) : null));
		return responseEntity(ex, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<GenericResponse> handleException(Exception ex) {
		String locale = CommonUtility.getHeaderValue("locale");
		GenericResponse genericResponse = new GenericResponse<>();
		genericResponse.setSuccessful(false);
		genericResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		genericResponse.setMessage(MessageUtilityServiceImpl.getMessage("customException.internalServerError.message", Objects.nonNull(locale) ? new Locale(locale) : null, RETRY_AFTER_MINUTES));
		return ResponseEntity.ok(genericResponse);
	}

	@ExceptionHandler(value = {MethodNotAllowedException.class, HttpClientErrorException.MethodNotAllowed.class})
	public ResponseEntity<GenericResponse> handleMethodNotAllowedException(Exception exception, HttpServletRequest request) {
		String locale = CommonUtility.getHeaderValue("locale");
		GenericResponse genericResponse = new GenericResponse<>();
		genericResponse.setSuccessful(false);
		genericResponse.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
		genericResponse.setMessage(MessageUtilityServiceImpl.getMessage("customException.methodNotAllowedException.message", Objects.nonNull(locale) ? new Locale(locale) : null));
		return ResponseEntity.ok(genericResponse);
	}

	static ResponseEntity<GenericResponse> responseEntity(Exception ex, HttpStatus httpStatus) {
		GenericResponse genericResponse = new GenericResponse<>();
		genericResponse.setSuccessful(false);
		genericResponse.setStatus(httpStatus.value());
		genericResponse.setMessage(ex.getMessage());
		genericResponse.setErrorDetail(ex.getClass().getSimpleName());
		return ResponseEntity.ok(genericResponse);
	}

}
