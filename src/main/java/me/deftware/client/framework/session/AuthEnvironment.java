package me.deftware.client.framework.session;

import com.mojang.authlib.Environment;

import java.util.StringJoiner;

/**
 * @author Deftware
 */
public class AuthEnvironment {

	String authHost, accountsHost, sessionHost;

	/**
	 * The provided URLs should NOT include a trailing slash.
	 */
	public AuthEnvironment(String authHost, String accountsHost, String sessionHost) {
		this.authHost = authHost;
		this.accountsHost = accountsHost;
		this.sessionHost = sessionHost;
	}

	/**
	 * Only applicable in >= 1.16.4
	 */
	public String getServicesHost() {
		return "https://api.minecraftservices.com";
	}

	public String getAuthHost() {
		return authHost;
	}

	public String getAccountsHost() {
		return accountsHost;
	}

	public String getSessionHost() {
		return sessionHost;
	}

	public String getName() {
		return "PROD";
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", "", "")
				.add("authHost='" + authHost + "'")
				.add("accountsHost='" + accountsHost + "'")
				.add("sessionHost='" + sessionHost + "'")
				.add("servicesHost='" + getServicesHost() + "'")
				.add("name='" + getName() + "'")
				.toString();
	}

	public Environment build() {
		return Environment.create(getAuthHost(), getAccountsHost(), getSessionHost(), getServicesHost(), getName());
	}

}
