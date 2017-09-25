package me.deftware.client.framework.Event.Events;

import java.util.Map;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import me.deftware.client.framework.Event.Event;

public class EventCape extends Event {

	private Map<Type, MinecraftProfileTexture> map;
	private String id;

	public EventCape(GameProfile profile, Map<Type, MinecraftProfileTexture> map) {
		this.map = map;
		this.id = profile.getName().toLowerCase().replace("-", "");
	}

	public String getID() {
		return this.id;
	}

	public void setCape(String url) {
		map.put(Type.CAPE, new MinecraftProfileTexture(url, null));
	}

}
