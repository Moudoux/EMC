package me.deftware.client.framework.Values;

import java.util.HashMap;

/**
 * Multidimensional value map
 * 
 * @author deftware
 *
 */
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

	/**
	 * Resets all maps and values
	 */
	public static void clear() {
		values = new HashMap<Integer, HashMap<String, IValue>>();
	}

	/**
	 * Clears a specific map
	 * 
	 * @param map
	 */
	public static void clearMap(int map) {
		if (values.containsKey(map)) {
			values.get(map).clear();
		}
	}

	/**
	 * Removes a value from a map
	 * 
	 * @param map
	 * @param key
	 */
	public static void removeValue(int map, String key) {
		if (values.containsKey(map)) {
			if (values.get(map).containsKey(key)) {
				values.get(map).remove(key);
			}
		}
	}

	/**
	 * Checks if a map has a key
	 * 
	 * @param map
	 * @param key
	 * @return
	 */
	public static boolean hasMapKey(int map, String key) {
		if (values.containsKey(map)) {
			return values.get(map).containsKey(key);
		}
		return false;
	}

	/**
	 * Updates a map value
	 * 
	 * @param map
	 * @param key
	 * @param value
	 */
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
