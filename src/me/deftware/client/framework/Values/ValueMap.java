package me.deftware.client.framework.Values;

import java.util.HashMap;

public abstract class ValueMap {

	public static HashMap<Integer, HashMap<String, IValue>> values = new HashMap<Integer, HashMap<String, IValue>>();

	public static <V> V getValue(int map, String key, V _default) {
		if (values.containsKey(map)) {
			if (values.get(map).containsKey(key)) {
				return (V) values.get(map).get(key).getValue();
			}
		}
		return (V) new IValue(_default).getValue();
	}

	public static void clear() {
		values = new HashMap<Integer, HashMap<String, IValue>>();
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

	public static synchronized <V> void updateValue(int map, String key, V value) {
		if (!values.containsKey(map)) {
			values.put(map, new HashMap<String, IValue>());
		}
		if (!values.get(map).containsKey(key)) {
			values.get(map).put(key, new IValue(value));
		} else {
			values.get(map).get(key).setValue(value);
		}
	}

	private static class IValue<V> {

		private V value;

		public IValue(V value) {
			this.value = value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public V getValue() {
			return value;
		}

	}

}
