package me.deftware.client.framework.util.types;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Deftware
 */
public @AllArgsConstructor @Data class Tuple<L, M, R> {

	private L left;
	private M middle;
	private R right;

}
