package integration.testing.rest.authenticationAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import integration.testing.rest.core.JwtService;
import integration.testing.rest.core.UserDTO;
import integration.testing.rest.userAPI.UserNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


@Service
public class AuthenticationService {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RecoveryService recoveryService;

	@Value("${jwt.key.auth}")
	private String authApiSecret;

	@Value("${jwt.key.user}")
	private String userApiSecret;

	@Value("${api.key.user}")
	private String userApiKey;

	@Value("${api.url.user}")
	private String userApiUrl;

	@Retryable( value = ResourceAccessException.class, maxAttempts = 3, backoff = @Backoff(delay = 100, multiplier = 0.2))
	public Response generateJwt(UserDTO userDTO) throws Exception {
		Integer id = getUserId(userDTO);
		Claims claims = Jwts.claims();
		claims.put("uid",id);
		return new Response(jwtService.createJWT(claims, authApiSecret), HttpStatus.OK) ;
	}

	private Integer getUserId(UserDTO userDTO) throws Exception {
		Claims claimsUser = Jwts.claims();
		claimsUser.put("username",userApiKey);
		String jwt = jwtService.createJWT(claimsUser, userApiSecret);

		try {
			ResponseEntity<UserDTO> loggeduser = restTemplate.exchange(userApiUrl, HttpMethod.GET, headersForUserApi(userDTO, jwt), UserDTO.class);
			return loggeduser.getBody().getId();
		} catch (HttpClientErrorException e) {
			throw new UserNotFoundException();
		}
	}

	private HttpEntity<String> headersForUserApi(UserDTO userDTO, String jwt) {
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(jwt);
		headers.set("username", userDTO.getUsername());
		headers.set("pwd", userDTO.getPassword());

		HttpEntity<String> request = new HttpEntity<>("",headers);
		return request;
	}

	@Recover
	private Response recover(ResourceAccessException resourceAccessException, UserDTO userDTO) {
		recoveryService.recover(resourceAccessException,userDTO);
		return new Response("Service unavailable", HttpStatus.BAD_REQUEST);
	}

}
