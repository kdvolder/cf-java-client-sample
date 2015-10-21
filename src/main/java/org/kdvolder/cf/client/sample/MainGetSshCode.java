package org.kdvolder.cf.client.sample;

import static org.kdvolder.cf.client.sample.AppConstants.APP_NAME;
import static org.kdvolder.cf.client.sample.AppConstants.CC_URL;
import static org.kdvolder.cf.client.sample.AppConstants.EMAIL;
import static org.kdvolder.cf.client.sample.AppConstants.ORG_NAME;
import static org.kdvolder.cf.client.sample.AppConstants.PW;
import static org.kdvolder.cf.client.sample.AppConstants.RUNNING_TIME;
import static org.kdvolder.cf.client.sample.AppConstants.SELF_SIGNED;
import static org.kdvolder.cf.client.sample.AppConstants.SPACE_NAME;

import java.net.URL;

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.CloudFoundryOperations;
import org.cloudfoundry.client.lib.StreamingLogToken;
import org.cloudfoundry.client.lib.domain.CloudApplication;

public class MainGetSshCode {
	
	public static void main(String[] args) throws Exception {
		CloudCredentials creds = new CloudCredentials(EMAIL, PW);
		CloudFoundryOperations client = new CloudFoundryClient(
				creds,
				new URL(CC_URL), ORG_NAME, SPACE_NAME, SELF_SIGNED);

		//Just to make sure stuff is alright:
		CloudApplication app = client.getApplication(APP_NAME);
		System.out.println("App = "+app);
		
		System.out.println("ssh-host: And get" +client.getSshHost());
		System.out.println("ssh-code: '"+client.getSshCode()+"'");
		
	}



}
