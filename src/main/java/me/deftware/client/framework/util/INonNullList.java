package me.deftware.client.framework.util;

import org.apache.commons.lang3.Validate;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Deftware
 */
@Deprecated
public class INonNullList<E> extends AbstractList<E> {

    private final List<E> delegate;
    private final E defaultElement;

    protected INonNullList() {
        this(new ArrayList<>(), null);
    }

    protected INonNullList(List<E> delegate, E defaultElement) {
        this.delegate = delegate;
        this.defaultElement = defaultElement;
    }

    public static <E> INonNullList<E> create() {
        return new INonNullList<>();
    }

    public static <E> INonNullList<E> withSize(int size, E defaultElement) {
        Validate.notNull(defaultElement);
        List<E> newList = new ArrayList<>();
        newList.add(defaultElement);
        return new INonNullList<>(newList, defaultElement);
    }

    @SafeVarargs
    public static <E> INonNullList<E> from(E defaultElement, E... elements) {
        return new INonNullList<>(Arrays.asList(elements), defaultElement);
    }

    public E get(int index) {
        return this.delegate.get(index);
    }

    public E set(int index, E element) {
        Validate.notNull(element);
        return this.delegate.set(index, element);
    }

    public void add(int index, E element) {
        Validate.notNull(element);
        this.delegate.add(index, element);
    }

    public E remove(int index) {
        return this.delegate.remove(index);
    }

    public int size() {
        return this.delegate.size();
    }

    public void clear() {
        if (this.defaultElement == null) {
            super.clear();
        } else {
            for (int index = 0; index < this.size(); ++index) {
                this.set(index, this.defaultElement);
            }
        }
    }

}

