package me.deftware.client.framework.Utils;

import java.net.Proxy;
import java.util.UUID;

import com.mojang.authlib.Agent;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import me.deftware.client.framework.AltAPIs.MCLeaks.MCLeaks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

/**
 * Various utils for the session, such as logging into an account
 * 
 * @author deftware
 *
 */
public class SessionUtils {

	public static boolean loginWithPassword(String username, String password) {
		Session session = null;

		UserAuthentication auth = new YggdrasilUserAuthentication(
				new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString()), Agent.MINECRAFT);
		auth.setUsername(username);
		auth.setPassword(password);
		try {
			auth.logIn();
			String userName = auth.getSelectedProfile().getName();
			UUID playerUUID = auth.getSelectedProfile().getId();
			String accessToken = auth.getAuthenticatedToken();

			session = new Session(userName, playerUUID.toString(), accessToken,
					username.contains("@") ? "mojang"
							: "legacy");
			
			MCLeaks.clearMCLeaksSession();
			
			Minecraft.getMinecraft().session = session;

			return true;
		} catch (AuthenticationException e) {}
		return false;
	}

	public static void loginWithoutPassword(String username) {
		Minecraft.getMinecraft().session = new Session(username, "", "0", "legacy");
		MCLeaks.clearMCLeaksSession();
	}
	
}
