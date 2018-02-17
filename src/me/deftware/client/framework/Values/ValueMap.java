package me.deftware.client.framework.Values;

import java.util.concurrent.ConcurrentHashMap;

public abstract class ValueMap {

	public static ConcurrentHashMap<Integer, ConcurrentHashMap<String, Object>> values = new ConcurrentHashMap<Integer, ConcurrentHashMap<String, Object>>();

	public static <V> V getValue(int map, String key, V _default) {
		if (values.containsKey(map)) {
			if (values.get(map).containsKey(key)) {
				return (V) values.get(map).get(key);
			}
		}
		return _default;
	}

	public static void clear() {
		values = new ConcurrentHashMap<Integer, ConcurrentHashMap<String, Object>>();
	}

	public static void clearMap(int map) {
		if (values.containsKey(map)) {
			values.get(map).clear();
		}
	}

	public static void removeValue(int map, String key) {
		if (values.containsKey(map)) {
			if (values.get(map).containsKey(key)) {
				values.get(map).remove(key);
			}
		}
	}

	public static boolean hasMapKey(int map, String key) {
		if (values.containsKey(map)) {
			return values.get(map).containsKey(key);
		}
		return false;
	}

	public static <V> void updateValue(int map, String key, V value) {
		values.putIfAbsent(map, new ConcurrentHashMap<String, Object>());
		values.get(map).put(key, value);
	}

}
