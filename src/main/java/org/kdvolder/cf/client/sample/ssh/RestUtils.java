package org.kdvolder.cf.client.sample.ssh;

import static org.apache.http.conn.ssl.SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;

import java.security.GeneralSecurityException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.cloudfoundry.client.lib.HttpProxyConfiguration;
import org.cloudfoundry.client.lib.rest.CloudControllerResponseErrorHandler;
import org.cloudfoundry.client.lib.rest.LoggingRestTemplate;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestUtils {
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////// Copied from cf-java-client lib with minor modifications to allow disabling redirect handling

	/**
	 * @author Thomas Risberg
	 * @author Kris De Volder
	 */
	public static RestTemplate createRestTemplate(HttpProxyConfiguration httpProxyConfiguration, boolean trustSelfSignedCerts, boolean disableRedirectHandling) {
		RestTemplate restTemplate = new LoggingRestTemplate();
		restTemplate.setRequestFactory(createRequestFactory(httpProxyConfiguration, trustSelfSignedCerts, disableRedirectHandling));
		restTemplate.setErrorHandler(new CloudControllerResponseErrorHandler());
		//TODO: for now we don't need, we don't even care about the body of the message anyhow.
		//restTemplate.setMessageConverters(getHttpMessageConverters());

		return restTemplate;
	}
	
	/**
	 * @author Thomas Risberg
	 * @author Kris De Volder
	 */
	public static ClientHttpRequestFactory createRequestFactory(HttpProxyConfiguration httpProxyConfiguration, boolean trustSelfSignedCerts, boolean disableRedirectHandling) {
		HttpClientBuilder httpClientBuilder = HttpClients.custom().useSystemProperties();

		if (trustSelfSignedCerts) {
			httpClientBuilder.setSslcontext(buildSslContext());
			httpClientBuilder.setHostnameVerifier(BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		}
		
		if (disableRedirectHandling) {
			httpClientBuilder.disableRedirectHandling();
		}

		if (httpProxyConfiguration != null) {
			HttpHost proxy = new HttpHost(httpProxyConfiguration.getProxyHost(), httpProxyConfiguration.getProxyPort());
			httpClientBuilder.setProxy(proxy);

			if (httpProxyConfiguration.isAuthRequired()) {
				BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
				credentialsProvider.setCredentials(
						new AuthScope(httpProxyConfiguration.getProxyHost(), httpProxyConfiguration.getProxyPort()),
						new UsernamePasswordCredentials(httpProxyConfiguration.getUsername(), httpProxyConfiguration.getPassword()));
				httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
			}

			HttpRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
			httpClientBuilder.setRoutePlanner(routePlanner);
		}

		HttpClient httpClient = httpClientBuilder.build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

		return requestFactory;
	}

	private static javax.net.ssl.SSLContext buildSslContext()  {
		try {
			return new SSLContextBuilder().useSSL().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
		} catch (GeneralSecurityException gse) {
			throw new RuntimeException("An error occurred setting up the SSLContext", gse);
		}
	}



}
