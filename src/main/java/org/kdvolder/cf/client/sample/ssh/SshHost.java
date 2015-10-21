package org.kdvolder.cf.client.sample.ssh;

/**
 * Info object containing various bits of info about the host to which
 * an ssh client may wish to connect.
 * 
 * @author Kris De Volder
 */
public class SshHost {

	final private String host;
	final private int port;
	final private String fingerPrint;
	
	public SshHost(String host, int port, String fingerPrint) {
		super();
		this.host = host;
		this.port = port;
		this.fingerPrint = fingerPrint;
	}
	public String getFingerPrint() {
		return fingerPrint;
	}
	public int getPort() {
		return port;
	}
	public String getHost() {
		return host;
	}
	@Override
	public String toString() {
		return "SshHost [host=" + host + ", port=" + port + ", fingerPrint=" + fingerPrint + "]";
	}
	
	
}
