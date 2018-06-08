package me.deftware.client.framework.maps;

import java.util.concurrent.ConcurrentHashMap;

public class EMCSkinManager {

	private static final ConcurrentHashMap<String, String> capes = new ConcurrentHashMap<>();

	public static void putCape(String name, String url) {
		capes.putIfAbsent(name, url);
	}

	public static String getCape(String name) {
		return capes.getOrDefault(name, "");
	}

}
