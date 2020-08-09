package me.deftware.client.framework.conversion;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Deftware
 */
public class CachedConvertedList<Original, Converted> {

	private List<Original> originalPtr;
	private List<Converted> convertedList = new ArrayList<>();
	private Converter<Original, Converted> supplier;

	public CachedConvertedList(List<Original> originalPtr, Converter<Original, Converted> supplier) {
		this.originalPtr = originalPtr;
		this.supplier = supplier;
	}

	public List<Converted> getValue() {
		if (convertedList.size() != originalPtr.size()) {
			convertedList.clear();
			for (Original original : originalPtr) {
				convertedList.add(supplier.convert(original));
			}
		}
		return convertedList;
	}

	@FunctionalInterface
	public interface Converter<Original, Converted> {

		Converted convert(Original original);

	}

}
