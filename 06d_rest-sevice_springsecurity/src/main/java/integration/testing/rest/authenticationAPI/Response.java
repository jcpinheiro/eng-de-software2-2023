package integration.testing.rest.authenticationAPI;

import org.springframework.http.HttpStatus;

public class Response {

	private String message;
	private HttpStatus httpStatus;

	public Response(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

}
