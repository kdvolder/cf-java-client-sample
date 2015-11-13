package org.kdvolder.cf.client.sample;

import org.cloudfoundry.client.lib.HttpProxyConfiguration;

public class CfClientConstants {

	public static final String CC_URL = "https://api.run.pivotal.io";
	public static final String ORG_NAME = "FrameworksAndRuntimes";
	public static final String SPACE_NAME = "kdevolder";
	public static final String EMAIL = "kdevolder@gopivotal.com";
	public static final String PW = System.getProperty("cf.password");
	public static final String APP_NAME = "demo";

	public static final boolean SELF_SIGNED = false;
	public static final HttpProxyConfiguration PROXY_CONF = null;



	public static final long RUNNING_TIME = 1000 * 60 * 1; // 1 minutes


	//public static final String CC_URL = "https://api.run.pez.pivotal.io";
	//public static final String ORG_NAME = "pivot-kdevolder";
	//public static final String SPACE_NAME = "development";
	//public static final String EMAIL = "kris.de.volder@gmail.com";
	//public static final String PW = System.getProperty("cf.password");
	//public static final boolean SELF_SIGNED = false;
	//public static final String APP_NAME = "demo-logger";
	//public static final long RUNNING_TIME = 1000 * 60 * 1; // 1 minutes



}
