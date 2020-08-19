package me.deftware.client.framework.conversion;

import com.google.common.collect.Iterables;
import me.deftware.client.framework.util.types.Pair;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Deftware
 */
public class ConvertedList<T, E> extends AbstractList<T> {

	private final List<T> delegate = new ArrayList<>();
	private final Predicate<Pair<T, Integer>> validator;
	private final Supplier<Iterable<E>> supplier;
	private final Function<E, T> converter;

	public ConvertedList(Supplier<Iterable<E>> supplier, Predicate<Pair<T, Integer>> validator, Function<E, T> converter) {
		this.supplier = supplier;
		this.validator = validator;
		this.converter = converter;
	}

	private void populate() {
		delegate.clear();
		for (E original : supplier.get()) {
			delegate.add(converter.apply(original));
		}
	}

	/**
	 * poll should be called prior to using the list
	 */
	public ConvertedList<T, E> poll() {
		if (Iterables.size(supplier.get()) != delegate.size()) populate();
		return this;
	}

	@Override
	public T get(int index) {
		if (validator != null && !validator.test(new Pair<>(delegate.get(index), index))) populate();
		return delegate.get(index);
	}

	@Override
	public int size() {
		return delegate.size();
	}

}
