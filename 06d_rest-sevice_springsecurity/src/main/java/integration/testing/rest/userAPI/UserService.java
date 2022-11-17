package integration.testing.rest.userAPI;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import integration.testing.rest.core.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User login(User user) throws UserNotFoundException {
		Optional<User> loggedUser = userRepository.findOne(Example.of(user));
		if (loggedUser.isPresent()) {
			return loggedUser.get();
		}
		throw new UserNotFoundException();
	}


}
