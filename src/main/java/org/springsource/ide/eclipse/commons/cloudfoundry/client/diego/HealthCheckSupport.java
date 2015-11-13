/*******************************************************************************
 * Copyright (c) 2015 Pivotal Software, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Pivotal Software, Inc. - initial API and implementation
 *******************************************************************************/
package org.springsource.ide.eclipse.commons.cloudfoundry.client.diego;

import java.util.UUID;

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.HttpProxyConfiguration;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Kris De Volder
 */
public class HealthCheckSupport extends CfClientSideCart {

	public static final String HC_NONE = "none";
	public static final String HC_PORT = "port";

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class HealthCheck {

		/**
		 * For jackson to be able to deserialize
		 */
		public HealthCheck() {
		}

		public HealthCheck(String type) {
			this.healthCheckType = type;
		}

		@JsonProperty("health_check_type")
		private String healthCheckType;

		public String getHealthCheckType() {
			return healthCheckType;
		}

		public void setHealthCheckType(String healthCheckType) {
			this.healthCheckType = healthCheckType;
		}
	}

	public HealthCheckSupport(CloudFoundryClient client, CloudInfoV2 cloudInfo, boolean trustSelfSigned, HttpProxyConfiguration httpProxyConfiguration) {
		super(client, cloudInfo, trustSelfSigned, httpProxyConfiguration);
	}

	protected RestTemplate createRestTemplate(boolean trustSelfSigned, HttpProxyConfiguration httpProxyConfiguration) {
		return RestUtils.createRestTemplate(httpProxyConfiguration, trustSelfSigned, /*disableRedirects*/false);
	}

	public String getHealthCheck(CloudApplication app) {
		UUID guid = app.getMeta().getGuid();
		HealthCheck summary = restTemplate.getForObject(url("/v2/apps/{guid}/summary"), HealthCheck.class, guid);
		if (summary!=null) {
			return summary.getHealthCheckType();
		}
		return null;
	}

	public void setHealthCheck(CloudApplication app, String type) {
		UUID guid = app.getMeta().getGuid();
		restTemplate.put(url("/v2/apps/{guid}"), new HealthCheck(type), guid);
	}

	private String url(String path) {
		return cloudInfo.getCloudControllerUrl()+path;
	}

	public static HealthCheckSupport create(final CloudFoundryClient client, CloudCredentials creds, HttpProxyConfiguration proxyConf, boolean selfSigned) {
		CloudInfoV2 cloudInfo = new CloudInfoV2(
				creds,
				client.getCloudControllerUrl(),
				proxyConf,
				selfSigned
		);

		return new HealthCheckSupport(client, cloudInfo, selfSigned, proxyConf);
	}

}
