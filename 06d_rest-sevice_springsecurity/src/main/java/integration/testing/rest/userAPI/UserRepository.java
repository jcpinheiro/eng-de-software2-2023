package integration.testing.rest.userAPI;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import integration.testing.rest.core.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM users where us.i = 1") User errorQuery();

}
