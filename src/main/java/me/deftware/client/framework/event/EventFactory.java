package me.deftware.client.framework.event;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EventFactory<T extends Event> {

	private static final Map<Class<? extends Event>, EventFactory<?>> events = new HashMap<>();

	@SuppressWarnings("unchecked")
	public static <T extends Event> EventFactory<T> getOrCreate(Class<T> clazz) {
		if (!events.containsKey(clazz)) {
			events.put(clazz, new EventFactory<>(clazz));
		}
		return (EventFactory<T>) events.get(clazz);
	}

	private Class<?>[] signature;
	private final T instance;
	private Method method;

	public EventFactory(Class<T> clazz) {
		try {
			this.instance = clazz.getDeclaredConstructor().newInstance();
		} catch (Exception ex) {
			throw new RuntimeException("Unable to create event", ex);
		}
	}

	public T setArguments(Object... arguments) {
		try {
			if (this.signature == null) {
				this.signature = Arrays.stream(arguments)
						.map(Object::getClass)
						.toArray(Class[]::new);
				this.method = this.instance.getClass().getMethod("create", this.signature);
			}
			this.method.invoke(this.instance, arguments);
		} catch (Exception ex) {
			throw new RuntimeException("Invalid event", ex);
		}
		return this.instance;
	}

}
