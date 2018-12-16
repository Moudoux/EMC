package me.deftware.client.framework.event;

import java.lang.reflect.Method;
import java.util.HashMap;

public class EventBus {

	private static HashMap<Class, HashMap<Class, HashMap<Method, Object>>> classes = new HashMap<>();

	public static void registerClass(Class clazz, Object instance) {
		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(EventHandler.class)) {
				EventHandler annotation = method.getAnnotation(EventHandler.class);
				Class eventType = annotation.eventType();
				if (!classes.containsKey(eventType)) {
					classes.put(eventType, new HashMap<>());
				}
				if (!classes.get(eventType).containsKey(clazz)) {
					classes.get(eventType).put(clazz, new HashMap<>());
				}
				classes.get(eventType).get(clazz).putIfAbsent(method, instance);
			}
		}
	}

	public static void sendEvent(Event event) {
		if (classes.containsKey(event.getClass())) {
			classes.get(event.getClass()).forEach((clazz, map) -> {
				map.forEach((method, instance) -> {
					try {
						method.invoke(instance, event);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				});
			});
		}
	}

}
