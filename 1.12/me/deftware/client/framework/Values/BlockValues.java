package me.deftware.client.framework.Values;

import java.util.HashMap;

/**
 * Multidimensional HashMaps to store block values and event handlers that
 * update the HashMaps
 * 
 * @author deftware
 *
 */
public class BlockValues {

	public static HashMap<Integer, HashMap<String, IBlockValue>> values = new HashMap<Integer, HashMap<String, IBlockValue>>();

	/**
	 * Returns a give value for a block
	 * 
	 * @param <V>
	 * 
	 * @param map
	 *            = The block ID
	 * @param key
	 *            = The value you want
	 * @param _default
	 *            = Default in case of no values set
	 * @return
	 */
	public static <V> V getValue(int map, String key, V _default) {
		if (values.containsKey(map)) {
			if (values.get(map).containsKey(key)) {
				return (V) values.get(map).get(key).getValue();
			}
		}
		return (V) new IBlockValue(_default).getValue();
	}

	public static boolean hasMapKey(int map, String key) {
		if (values.containsKey(map)) {
			return values.get(map).containsKey(key);
		}
		return false;
	}

	/**
	 * @see getValue
	 * @param map
	 * @param key
	 * @param value
	 */
	public static synchronized <V> void updateValue(int map, String key, V value) {
		if (!values.containsKey(map)) {
			values.put(map, new HashMap<String, IBlockValue>());
		}
		if (!values.get(map).containsKey(key)) {
			values.get(map).put(key, new IBlockValue(value));
		} else {
			values.get(map).get(key).setValue(value);
		}
	}

	public static class IBlockValue<V> {

		private V value;

		public IBlockValue(V value) {
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
