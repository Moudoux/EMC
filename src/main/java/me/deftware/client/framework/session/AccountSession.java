package me.deftware.client.framework.session;

import com.mojang.authlib.Agent;
import com.mojang.authlib.Environment;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import me.deftware.client.framework.minecraft.Minecraft;
import net.minecraft.client.util.Session;

import java.net.Proxy;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Deftware
 * @since 17.0.0
 */
public class AccountSession {

	/**
	 * The account type
	 */
	private Session.AccountType type = Session.AccountType.MOJANG;

	/**
	 * Unique client ID which needs to be used for every request
	 */
	private final String clientId = UUID.randomUUID().toString();

	/**
	 * Authentication environment
	 */
	private final Environment environment;

	/**
	 * Minecraft session object
	 */
	private Session session;

	private final UserAuthentication userAuthentication;
	private final YggdrasilAuthenticationService authenticationService;

	private AccountSession(Environment environment) {
		this.environment = environment;
		// Set up authentication service
		this.authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, clientId, environment);
		this.userAuthentication = new YggdrasilUserAuthentication(authenticationService, clientId, Agent.MINECRAFT, environment);
	}

	/**
	 * Creates a new account session with a custom environment
	 *
	 * @param environment Custom Yggdrasil environment
	 */
	public AccountSession(AuthEnvironment environment) {
		this(environment == null ? YggdrasilEnvironment.PROD.getEnvironment() : environment.build());
	}

	/**
	 * Logs into a Mojang or legacy account
	 *
	 * @param username Account username
	 * @param password Account password
	 * @throws Exception Authentication exception
	 */
	public AccountSession withCredentials(String username, String password) throws Exception {
		this.type = username.contains("@") ? Session.AccountType.MOJANG : Session.AccountType.LEGACY;
		this.userAuthentication.setUsername(username);
		this.userAuthentication.setPassword(password);
		this.userAuthentication.logIn();
		// Set session
		this.session = new Session(userAuthentication.getSelectedProfile().getName(), userAuthentication.getSelectedProfile().getId().toString(),
				userAuthentication.getAuthenticatedToken(), Optional.empty(), Optional.of(clientId), this.type);
		return this;
	}

	/**
	 * Creates an offline session
	 *
	 * @param username Any username
	 */
	public AccountSession withOfflineUsername(String username) {
		this.type = Session.AccountType.LEGACY;
		this.session = new Session(username, "", "0", Optional.empty(),
				Optional.of(clientId), this.type);
		return this;
	}

	/**
	 * Logs into a custom provided session
	 *
	 * @param map Account session details
	 * @param accountType Account type
	 */
	public AccountSession withSession(Map<String, Object> map, AccountType accountType) {
		this.type = accountType.getType();
		this.session = new Session(map.get("username").toString(), map.get("uuid").toString(), map.get("accessToken").toString(),
				Optional.of(map.getOrDefault("xuid", "").toString()), Optional.of(clientId), this.type);
		this.userAuthentication.loadFromStorage(map);
		return this;
	}

	/**
	 * @return If the session has been created
	 */
	public boolean isSessionAvailable() {
		return this.session != null;
	}

	/**
	 * @return The session username
	 */
	public String getSessionUsername() {
		return session.getProfile().getName();
	}

	/**
	 * @return The session uuid
	 */
	public UUID getSessionUUID() {
		return session.getProfile().getId();
	}

	/**
	 * Sets the Minecraft session to this session
	 */
	public AccountSession setSession() {
		Minecraft minecraft = Minecraft.getMinecraftGame();
		minecraft.setSession(this.session);
		minecraft.setSessionService(new CustomSessionService(this.authenticationService, this.environment));
		return this;
	}

}
