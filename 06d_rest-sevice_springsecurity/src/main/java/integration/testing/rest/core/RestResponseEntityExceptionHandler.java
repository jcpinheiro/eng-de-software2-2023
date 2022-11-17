package integration.testing.rest.core;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import integration.testing.rest.userAPI.UserNotFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<String> handleUserNotFoundException (UserNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>("User was not found", HttpStatus.NOT_FOUND);
	}

}
