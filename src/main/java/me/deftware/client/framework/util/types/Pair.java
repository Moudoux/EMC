package me.deftware.client.framework.util.types;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Deftware
 */
public @AllArgsConstructor @Data class Pair<L, R> {

	private L left;
	private R right;

}
