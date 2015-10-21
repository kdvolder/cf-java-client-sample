package org.kdvolder.cf.client.sample;

import static org.kdvolder.cf.client.sample.CfClientConstants.APP_NAME;
import static org.kdvolder.cf.client.sample.CfClientConstants.CC_URL;
import static org.kdvolder.cf.client.sample.CfClientConstants.EMAIL;
import static org.kdvolder.cf.client.sample.CfClientConstants.ORG_NAME;
import static org.kdvolder.cf.client.sample.CfClientConstants.PROXY_CONF;
import static org.kdvolder.cf.client.sample.CfClientConstants.PW;
import static org.kdvolder.cf.client.sample.CfClientConstants.SELF_SIGNED;
import static org.kdvolder.cf.client.sample.CfClientConstants.SPACE_NAME;

import java.net.URL;

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.CloudFoundryOperations;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.kdvolder.cf.client.sample.ssh.AuthorizationHeaderProvider;
import org.kdvolder.cf.client.sample.ssh.CloudInfoV2;
import org.kdvolder.cf.client.sample.ssh.SshClientSupport;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class MainGetSshCode {
	
	public static void main(String[] args) throws Exception {
		CloudCredentials creds = new CloudCredentials(EMAIL, PW);
		final CloudFoundryOperations client = new CloudFoundryClient(
				creds,
				new URL(CC_URL), ORG_NAME, SPACE_NAME, 
				PROXY_CONF,
				SELF_SIGNED
		);
		
		AuthorizationHeaderProvider oauth = new AuthorizationHeaderProvider() {
			public String getAuthorizationHeader() {
				OAuth2AccessToken token = client.login();
				return token.getTokenType()+" "+token.getValue();
			}
		};
		
		CloudInfoV2 cloudInfo = new CloudInfoV2(
				creds,
				new URL(CC_URL),
				PROXY_CONF,
				SELF_SIGNED
		);
		
		SshClientSupport ssh = new SshClientSupport(oauth, cloudInfo, SELF_SIGNED, PROXY_CONF);
		

		//Just to make sure stuff is alright:
		CloudApplication app = client.getApplication(APP_NAME);
		System.out.println("App = "+app);
		
		System.out.println("ssh-host: " +ssh.getSshHost());
		System.out.println("ssh-code: '"+ssh.getSshCode()+"'");
		
	}



}
