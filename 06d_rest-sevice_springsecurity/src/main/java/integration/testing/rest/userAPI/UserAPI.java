package integration.testing.rest.userAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import integration.testing.rest.core.User;
import integration.testing.rest.core.UserDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class UserAPI {

	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public UserDTO user(@RequestHeader(value="username") String username, @RequestHeader(value="pwd") String pwd) throws Exception {
		User user = userService.login(new User(username, pwd));
		return new UserDTO(user.getId(), user.getUsername());
	}

}
