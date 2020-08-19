package me.deftware.client.framework.conversion;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Deftware
 */
public class ComparedConversion<Original, Converted> {

	private final Supplier<Original> originalSupplier;
	private final Function<Original, Converted> converter;
	private Converted convertedObject;
	private Original localCopy;

	public ComparedConversion(Supplier<Original> originalSupplier, Function<Original, Converted> converter) {
		this.originalSupplier = originalSupplier;
		this.converter = converter;
	}

	public Converted get() {
		if (originalSupplier.get() == null) return null;
		if (localCopy == null) localCopy = originalSupplier.get();
		if (convertedObject == null || localCopy != originalSupplier.get()) {
			localCopy = originalSupplier.get();
			convertedObject = converter.apply(localCopy);
		}
		return convertedObject;
	}

}
