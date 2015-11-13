package org.kdvolder.cf.client.sample;

import static org.kdvolder.cf.client.sample.CfClientConstants.APP_NAME;
import static org.kdvolder.cf.client.sample.CfClientConstants.CC_URL;
import static org.kdvolder.cf.client.sample.CfClientConstants.EMAIL;
import static org.kdvolder.cf.client.sample.CfClientConstants.ORG_NAME;
import static org.kdvolder.cf.client.sample.CfClientConstants.PROXY_CONF;
import static org.kdvolder.cf.client.sample.CfClientConstants.PW;
import static org.kdvolder.cf.client.sample.CfClientConstants.SELF_SIGNED;
import static org.kdvolder.cf.client.sample.CfClientConstants.SPACE_NAME;

import static org.springsource.ide.eclipse.commons.cloudfoundry.client.diego.HealthCheckSupport.*;

import java.net.URL;

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.springsource.ide.eclipse.commons.cloudfoundry.client.diego.HealthCheckSupport;

public class MainHealthCheck {

	public static void main(String[] args) throws Exception {
		CloudCredentials creds = new CloudCredentials(EMAIL, PW);
		final CloudFoundryClient client = new CloudFoundryClient(
				creds,
				new URL(CC_URL), ORG_NAME, SPACE_NAME,
				PROXY_CONF,
				SELF_SIGNED
		);

		HealthCheckSupport hc = HealthCheckSupport.create(client, creds, PROXY_CONF, SELF_SIGNED);


		//Just to make sure stuff is alright:
		CloudApplication app = client.getApplication(APP_NAME);
		System.out.println("App = "+app);

		String[] types = {
				HC_NONE,
				HC_PORT
		};
		for (String type : types) {
			System.out.println("set-health-check: "+type);
			hc.setHealthCheck(app, type);
			System.out.println("healh-check: " + hc.getHealthCheck(app));
		}

	}



}
