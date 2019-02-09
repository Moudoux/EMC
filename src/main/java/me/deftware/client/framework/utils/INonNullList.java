package me.deftware.client.framework.utils;

import org.apache.commons.lang3.Validate;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("All")
public class INonNullList<E> extends AbstractList<E> {

    private final List<E> delegate;
    private final E defaultElement;

    protected INonNullList() {
        this(new ArrayList(), null);
    }

    protected INonNullList(List<E> p_i47327_1_, E p_i47327_2_) {
        this.delegate = p_i47327_1_;
        this.defaultElement = p_i47327_2_;
    }

    public static <E> INonNullList<E> create() {
        return new INonNullList();
    }

    public static <E> INonNullList<E> withSize(int p_withSize_0_, E p_withSize_1_) {
        Validate.notNull(p_withSize_1_);
        Object[] lvt_2_1_ = new Object[p_withSize_0_];
        Arrays.fill(lvt_2_1_, p_withSize_1_);
        return new INonNullList(Arrays.asList(lvt_2_1_), p_withSize_1_);
    }

    @SafeVarargs
    public static <E> INonNullList<E> from(E p_from_0_, E... p_from_1_) {
        return new INonNullList(Arrays.asList(p_from_1_), p_from_0_);
    }

    public E get(int p_get_1_) {
        return this.delegate.get(p_get_1_);
    }

    public E set(int p_set_1_, E p_set_2_) {
        Validate.notNull(p_set_2_);
        return this.delegate.set(p_set_1_, p_set_2_);
    }

    public void add(int p_add_1_, E p_add_2_) {
        Validate.notNull(p_add_2_);
        this.delegate.add(p_add_1_, p_add_2_);
    }

    public E remove(int p_remove_1_) {
        return this.delegate.remove(p_remove_1_);
    }

    public int size() {
        return this.delegate.size();
    }

    public void clear() {
        if (this.defaultElement == null) {
            super.clear();
        } else {
            for (int lvt_1_1_ = 0; lvt_1_1_ < this.size(); ++lvt_1_1_) {
                this.set(lvt_1_1_, this.defaultElement);
            }
        }
    }

}

