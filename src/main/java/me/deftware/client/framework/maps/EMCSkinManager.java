package me.deftware.client.framework.maps;

import me.deftware.client.framework.utils.HashUtils;

import java.util.concurrent.ConcurrentHashMap;

public class EMCSkinManager {

	private static final ConcurrentHashMap<String, String> capes = new ConcurrentHashMap<>();

	public static void putCape(String name, String url, boolean hashed) {
		try {
			capes.putIfAbsent(translateUsername(name), url);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static String getCape(String name) throws Exception {
		return capes.getOrDefault(translateUsername(name), "");
	}

	private static String translateUsername(String username) throws Exception {
		return HashUtils.getSHA(username.toLowerCase()).toLowerCase();
	}

}
