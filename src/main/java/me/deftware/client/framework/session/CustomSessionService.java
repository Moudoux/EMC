package me.deftware.client.framework.session;

import com.mojang.authlib.Environment;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class CustomSessionService extends YggdrasilMinecraftSessionService {

	public CustomSessionService(YggdrasilAuthenticationService service, Environment env) {
		super(service, env);
	}

	@Override
	protected GameProfile fillGameProfile(final GameProfile profile, final boolean requireSecure) {
		try {
			Field baseUrl = this.getClass().getSuperclass().getDeclaredField("baseUrl");
			baseUrl.setAccessible(true);
			String currentServer = (String) baseUrl.get(this);
			boolean reset = false;
			if (!currentServer.contains("sessionserver.mojang.com")) {
				if (Modifier.isFinal(baseUrl.getModifiers())) {
					Field modifiersField = Field.class.getDeclaredField("modifiers");
					modifiersField.setAccessible(true);
					modifiersField.setInt(baseUrl, baseUrl.getModifiers() & ~Modifier.FINAL);
				}
				baseUrl.set(this, "https://sessionserver.mojang.com/session/minecraft/");
				reset = true;
			}
			GameProfile filled = super.fillGameProfile(profile, requireSecure);
			if (reset) {
				baseUrl.set(this, currentServer);
			}
			return filled;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return profile;
	}

}
