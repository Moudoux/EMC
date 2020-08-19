package me.deftware.client.framework.conversion;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author Deftware
 */
public class InstanceList<T, E> extends AbstractList<T> {

	private int internalSize = 0;
	private final ArrayList<T> delegate = new ArrayList<>();
	private final Supplier<List<E>> dataSource;
	private final Function<E, T> converter;
	private final Predicate<E> tester;

	public InstanceList(Supplier<List<E>> dataSource, Predicate<E> tester, Function<E, T> converter) {
		this.tester = tester;
		this.dataSource = dataSource;
		this.converter = converter;
	}

	private void populate() {
		delegate.clear();
		internalSize = dataSource.get().size();
		for (E original : dataSource.get()) {
			if (tester.test(original)) delegate.add(converter.apply(original));
		}
	}

	/**
	 * poll should be called prior to using the list
	 */
	public InstanceList<T, E> poll() {
		if (dataSource.get().size() != internalSize) populate();
		return this;
	}

	@Override
	public T get(int index) {
		return delegate.get(index);
	}

	@Override
	public int size() {
		return delegate.size();
	}

}
