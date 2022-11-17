package integration.testing.rest.authenticationAPI;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecoveryLogRepository extends JpaRepository<RecoveryLog, String> {

}
