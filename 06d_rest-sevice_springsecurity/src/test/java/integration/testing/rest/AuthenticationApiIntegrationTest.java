package integration.testing.rest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import integration.testing.rest.authenticationAPI.RecoveryLogRepository;
import integration.testing.rest.authenticationAPI.RecoveryService;
import integration.testing.rest.core.JwtService;
import integration.testing.rest.core.UserDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthenticationApiIntegrationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private JwtService jwtService;

	@MockBean
	private RestTemplate restTemplate;

	@SpyBean
	private RecoveryService recoveryService;

	@Autowired
	private RecoveryLogRepository recoveryLogRepository;

	@Value("${api.url.user}")
	private String userApiUrl;

	@Value("${jwt.key.auth}")
	private String authApiSecret;

	@Test
	public void givenARegularUserAJwtIsGenerated() {
		Mockito.when(restTemplate.exchange(Mockito.eq(userApiUrl),
				    Mockito.eq(HttpMethod.GET)
				, Mockito.any(HttpEntity.class), Mockito.eq(UserDTO.class)))
		.thenReturn(ResponseEntity.ok(new UserDTO(1,"user")));

		ResponseEntity<String> response = testRestTemplate.postForEntity("/login", new UserDTO("john","pass"), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).isNotNull();
		Assertions.assertThat(response.getBody()).isNotEmpty();
		Assertions.assertThat(jwtService.validateToken(response.getBody(), authApiSecret)).isEqualTo(Boolean.TRUE);
	}

	@Test
	public void givenANonRegiteredUserReturnUserWasNotFound() {
		Mockito.when(restTemplate.exchange(Mockito.eq(userApiUrl), Mockito.eq(HttpMethod.GET)
				, Mockito.any(HttpEntity.class), Mockito.eq(UserDTO.class)))
		.thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "User not found"));

		ResponseEntity<String> response = testRestTemplate.postForEntity("/login", new UserDTO("seth","123"), String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		Assertions.assertThat(response.getBody()).isEqualTo("User was not found");

	}

	@Test
	public void givenResourceAccessIssueRetryIsTriggered() {
		Mockito.when(restTemplate.exchange(Mockito.eq(userApiUrl), Mockito.eq(HttpMethod.GET)
				, Mockito.any(HttpEntity.class), Mockito.eq(UserDTO.class)))
		.thenThrow(new ResourceAccessException(""));

		UserDTO userDto = new UserDTO("john","pass");

		ResponseEntity<String> response = testRestTemplate.postForEntity("/login", userDto, String.class);
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

		Mockito.verify(restTemplate, Mockito.times(3))
		.exchange(Mockito.eq(userApiUrl), Mockito.eq(HttpMethod.GET)
				, Mockito.any(HttpEntity.class), Mockito.eq(UserDTO.class));

		Mockito.verify(recoveryService, Mockito.times(1))
		.recover(Mockito.any(ResourceAccessException.class),Mockito.eq(userDto));

		Long count = recoveryLogRepository.count();
		Assertions.assertThat(count).isEqualTo(1L);

		Assertions.assertThat(response.getBody()).isEqualTo("Service unavailable");

	}

}
