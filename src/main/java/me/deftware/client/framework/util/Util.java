package me.deftware.client.framework.util;

import com.google.common.collect.Lists;
import me.deftware.client.framework.item.ItemStack;

import java.util.List;
import java.util.function.Function;

/**
 * @author Deftware
 */
public class Util {

	public static List<ItemStack> getEmptyStackList(int size) {
		return getFilledList(size, i -> ItemStack.getEmpty());
	}

	public static <T> List<T> getFilledList(int size, Function<Integer, T> supplier) {
		List<T> list = Lists.newArrayListWithCapacity(size);
		for (int i = 0; i < size; i++)
			list.add(supplier.apply(i));
		return list;
	}

}
