package integration.testing.rest.authenticationAPI;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RecoveryLog {

	@Id
	private String log;

	public RecoveryLog() {}

	public RecoveryLog(String log) {
		this.log = log;
	}

	public String getLog() {
		return log;
	}


}
