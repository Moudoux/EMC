package me.deftware.client.framework.utils.session;

import com.mojang.authlib.Agent;
import com.mojang.authlib.Environment;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
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

	private Session session;
	private final UserAuthentication userAuthentication;
	private final YggdrasilAuthenticationService authenticationService;

	private AuthLibSession(Environment yggdrasil) {
		// Custom yggdrasil as an argument below is only applicable for Minecraft 1.16 and above. Prior version of Minecraft do not need it.
		authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString(), yggdrasil);
		userAuthentication = new YggdrasilUserAuthentication(authenticationService, Agent.MINECRAFT, yggdrasil);
	}

	public AuthLibSession(CustomYggdrasil yggdrasil) {
		this(yggdrasil.build());
	}

	public AuthLibSession() {
		this(YggdrasilEnvironment.PROD);
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

	public void setOfflineSession(String username) {
		((IMixinMinecraft) MinecraftClient.getInstance()).setSession(new Session(username, "", "0", "legacy"));
	}

}
