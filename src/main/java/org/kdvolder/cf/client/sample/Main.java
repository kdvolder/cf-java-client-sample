package org.kdvolder.cf.client.sample;

import java.net.URL;

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.StreamingLogToken;
import org.cloudfoundry.client.lib.domain.CloudApplication;

public class Main {

	private static final String CC_URL = "https://api.run.pez.pivotal.io";
	private static final String ORG_NAME = "pivot-kdevolder";
	private static final String SPACE_NAME = "development";
	private static final String EMAIL = "kris.de.volder@gmail.com";
	private static final String PW = System.getProperty("cf.password");
	private static final boolean SELF_SIGNED = false;
	private static final String APP_NAME = "demo-logger";
	private static final long RUNNING_TIME = 1000 * 60 * 10; // 10 minutes


	public static void main(String[] args) throws Exception {
		CloudCredentials creds = new CloudCredentials(EMAIL, PW);
		CloudFoundryClient client = new CloudFoundryClient(
				creds,
				new URL(CC_URL), ORG_NAME, SPACE_NAME, SELF_SIGNED);

		CloudApplication app = client.getApplication(APP_NAME);
		System.out.println("App = "+app);
		StreamingLogToken logToken = client.streamLogs(APP_NAME, new LoggingLogListener());
		sleep(RUNNING_TIME);
		System.out.println("******** Canceling log token **********");
		logToken.cancel();
		sleep(4000);
	}


	private static void sleep(long runningTime) {
		long endTime = System.currentTimeMillis() + runningTime;
		while (System.currentTimeMillis() < endTime) {
			try {
				Thread.sleep(4000);
			} catch (Exception e) {
				//ignore
			}
		}
	}

//	public static void main(String[] args) throws Exception {
//
//		CloudControllerClientFactory factory = new CloudControllerClientFactory(null, true);
//
//		CloudCredentials creds = new CloudCredentials(EMAIL, PW);
//		CloudControllerClient client = factory.newCloudController(new URL(CC_URL), creds, ORG_NAME, SPACE_NAME);
//
////		for (CloudService s : client.getServices()) {
////			System.out.println(s);
////		}
//
//		EurekaFinder eurekaFinder = new EurekaFinder(client);
//		List<CloudServiceBinding> eurekas = eurekaFinder.getEurekaServicesBindings();
//		for (CloudServiceBinding s : eurekas) {
//			System.out.println(s);
//			System.out.println("creds = "+s.getCredentials());
//		}
//
////		CloudServiceInstance eureka = client.getServiceInstance("another-eureka");
//
//	}

}
