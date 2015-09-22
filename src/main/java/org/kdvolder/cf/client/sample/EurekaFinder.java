package org.kdvolder.cf.client.sample;

import java.util.ArrayList;
import java.util.List;

import org.cloudfoundry.client.lib.domain.CloudService;
import org.cloudfoundry.client.lib.domain.CloudServiceBinding;
import org.cloudfoundry.client.lib.domain.CloudServiceInstance;
import org.cloudfoundry.client.lib.rest.CloudControllerClient;

/**
 * Helper class to extract information about a bound Eureka service from an
 * app deployed on cloudfoundry.
 *
 * @author Kris De Volder
 */
public class EurekaFinder {

	private static final String SERVICE_LABEL = "p-service-registry";
	private CloudControllerClient client;

	public EurekaFinder(CloudControllerClient client) {
		this.client = client;
	}

	public List<CloudService> getEurekaServices() {
		List<CloudService> services = client.getServices();
		ArrayList<CloudService> eurekas = new ArrayList<CloudService>(services.size());

		System.out.println(">>> Services");
		for (CloudService s : services) {
			if (isEurekaService(s)) {
				eurekas.add(s);
			}
			System.out.println(s);
			System.out.println("  Label    : "+s.getLabel());
			System.out.println("  Name     : "+s.getName());
			System.out.println("  Provider : "+s.getProvider());
			System.out.println("  Version  : "+s.getVersion());
		}
		System.out.println("<<< Services");
		return eurekas;
	}

	public List<CloudServiceBinding> getServiceBindings(CloudService service) {
		CloudServiceInstance instance = client.getServiceInstance(service.getName());
		return instance.getBindings();
	}

	public List<CloudServiceBinding> getServiceBindings(List<CloudService> services) {
		ArrayList<CloudServiceBinding> bindings = new ArrayList<CloudServiceBinding>();
		for (CloudService s : services) {
			bindings.addAll(getServiceBindings(s));
		}
		return bindings;
	}

	private boolean isEurekaService(CloudService s) {
		return SERVICE_LABEL.equals(s.getLabel());
	}

	public List<CloudServiceBinding> getEurekaServicesBindings() {
		return getServiceBindings(getEurekaServices());
	}



}
