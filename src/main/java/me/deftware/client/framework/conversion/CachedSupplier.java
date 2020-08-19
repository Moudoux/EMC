package me.deftware.client.framework.conversion;

import java.util.function.Supplier;

/**
 * @author Deftware
 */
public class CachedSupplier<Type> {

	private Type value;
	private final Supplier<Type> supplier;

	public CachedSupplier(Supplier<Type> supplier) {
		this.supplier = supplier;
	}

	public Type get() {
		if (value == null) {
			value = supplier.get();
		}
		return value;
	}

}
