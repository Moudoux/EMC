package me.deftware.client.framework.main.subsystem;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class ClassModifier<T> {

	private final Unsafe unsafe;
	private final Class<T> clazz;

	public ClassModifier(Class<T> clazz) throws Exception {
		this.clazz = clazz;
		Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
		unsafeField.setAccessible(true);
		unsafe = (Unsafe) unsafeField.get(null);
	}

	public void setField(String name, Object object) throws Exception {
		Field loaderInstance = clazz.getDeclaredField(name);
		Object staticFieldBase = unsafe.staticFieldBase(loaderInstance);
		long staticFieldOffset = unsafe.staticFieldOffset(loaderInstance);
		unsafe.putObject(staticFieldBase, staticFieldOffset, object);
	}

}
