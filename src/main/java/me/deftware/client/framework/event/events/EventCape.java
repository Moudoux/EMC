package me.deftware.client.framework.event.events;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import me.deftware.client.framework.event.Event;

import java.util.Map;

public class EventCape extends Event {

	private Map<Type, MinecraftProfileTexture> map;
	private String id;

	public EventCape(GameProfile profile, Map<Type, MinecraftProfileTexture> map) {
		this.map = map;
		id = profile.getName().toLowerCase().replace("-", "");
	}

	public String getID() {
		return id;
	}

	public void setCape(String url) {
		map.put(Type.CAPE, new MinecraftProfileTexture(url, null));
	}

}
