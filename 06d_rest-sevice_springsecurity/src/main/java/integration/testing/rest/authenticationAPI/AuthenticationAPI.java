package integration.testing.rest.authenticationAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import integration.testing.rest.core.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class AuthenticationAPI {

	@Autowired
	private AuthenticationService authenticationService;

	@Operation(summary = "Do a login for an user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "JWT",
					content = { @Content(mediaType = org.springframework.http.MediaType.TEXT_PLAIN_VALUE,
					schema = @Schema(implementation = String.class))}),
			@ApiResponse(responseCode = "400", description = "User Service unavailable, it is tried reach the service two times in case of an error",
			content = @Content),
			@ApiResponse(responseCode = "404", description = "User not found",
			content = @Content) })


	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDTO userDTO) throws Exception {
		Response response = authenticationService.generateJwt(userDTO);
		return ResponseEntity.status(response.getHttpStatus()).body(response.getMessage());
	}

}
