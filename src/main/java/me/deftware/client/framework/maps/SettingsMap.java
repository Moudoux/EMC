package me.deftware.client.framework.maps;

import java.util.concurrent.ConcurrentHashMap;

public class SettingsMap {

	private static boolean overrideMode = false;
	private static final ConcurrentHashMap<Integer, ConcurrentHashMap<String, Object>> map = new ConcurrentHashMap<>();

	public static void update(int mapKey, String key, Object value) {
		SettingsMap.map.putIfAbsent(mapKey, new ConcurrentHashMap<>());
		SettingsMap.map.get(mapKey).put(key, value);
	}

	public static Object getValue(int mapKey, String key, Object _default) {
		if (SettingsMap.map.containsKey(mapKey)) {
			if (SettingsMap.map.get(mapKey).containsKey(key)) {
				return SettingsMap.map.get(mapKey).get(key);
			}
		}
		return _default;
	}

	public static boolean isOverrideMode() {
		return SettingsMap.overrideMode;
	}

	public static void setOverrideMode(boolean state) {
		SettingsMap.overrideMode = state;
	}

	public static class MapKeys {

		/**
		 * lightValue = 0 - 10
		 * render = true | false
		 * translucent = true | false
		 * liquid_aabb_solid = true | false
		 * custom_cactus_aabb = true | false
		 */
		public static final int BLOCKS = 1;

		/**
		 * RAINBOW_ITEM_GLINT = true | false
		 * WORLD_DEPTH = true | false
		 * FLIP_USERNAMES = String,String...
		 * CROSSHAIR = true | false
		 */
		public static final int RENDER = 2;


	}

}
