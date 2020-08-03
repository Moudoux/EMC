package me.deftware.client.framework.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Deftware
 */
public @AllArgsConstructor @Data class Tuple<T, E, K> {

	private T left;
	private E middle;
	private K right;

}
