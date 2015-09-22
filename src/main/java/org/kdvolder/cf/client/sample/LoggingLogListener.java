package org.kdvolder.cf.client.sample;

import org.cloudfoundry.client.lib.ApplicationLogListener;
import org.cloudfoundry.client.lib.domain.ApplicationLog;

public class LoggingLogListener implements ApplicationLogListener {

	public void onMessage(ApplicationLog log) {
		System.out.println("MSG: "+log);
	}

	public void onComplete() {
		System.out.println("COMPLETE");
	}

	public void onError(Throwable exception) {
		System.out.println("ERROR");
		exception.printStackTrace();
	}

}
