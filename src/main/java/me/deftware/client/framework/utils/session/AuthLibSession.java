package me.deftware.client.framework.utils.session;

import com.mojang.authlib.Agent;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

import java.net.Proxy;
import java.util.UUID;

/**
 * Allows modifying and creating sessions
 */
public class AuthLibSession {

	private final UserAuthentication userAuthentication;
	private final YggdrasilAuthenticationService authenticationService;
	private Session session;

	public AuthLibSession(CustomYggdrasil yggdrasil) {
        authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString());
		userAuthentication = new YggdrasilUserAuthentication(authenticationService, Agent.MINECRAFT);
	}

	public AuthLibSession() {
		this(null);
	}

	public void setCredentials(String username, String password) {
		userAuthentication.setUsername(username);
		userAuthentication.setPassword(password);
	}

	public void logout() {
		userAuthentication.logOut();
	}

	public boolean login() {
		try {
			userAuthentication.logIn();
			return true;
		} catch (AuthenticationException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean loggedIn() {
		return userAuthentication != null && session != null && userAuthentication.isLoggedIn();
	}

	public Session buildSession() {
		if (!userAuthentication.isLoggedIn()) throw new RuntimeException("Cannot create session without logging in!");
		if (session != null) {
			return session;
		}
		session = new Session(userAuthentication.getSelectedProfile().getName(), userAuthentication.getSelectedProfile().getId().toString(),
				userAuthentication.getAuthenticatedToken(), userAuthentication.getSelectedProfile().getName().contains("@") ? "mojang" : "legacy");
		return session;
	}

	public void setSession() {
		setSession(buildSession());
	}

	public void setSession(Session session) {
		((IMixinMinecraft) MinecraftClient.getInstance()).setSession(buildSession());
		((IMixinMinecraft) MinecraftClient.getInstance()).setSessionService(authenticationService.createMinecraftSessionService());
	}

}
