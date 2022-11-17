package integration.testing.rest.authenticationAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import integration.testing.rest.core.UserDTO;

@Service
public class RecoveryService {

	@Autowired
	private RecoveryLogRepository recoveryLogRepository;

	public void recover(ResourceAccessException resourceAccessException, UserDTO userDTO) {
		recoveryLogRepository.save(new RecoveryLog("The userApi is down"));
	}

}
