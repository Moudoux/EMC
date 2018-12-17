package me.deftware.client.framework.event;

import me.deftware.client.framework.main.Bootstrap;

import java.lang.reflect.Method;
import java.util.HashMap;

public class EventBus {

	private static HashMap<Class, HashMap<Class, HashMap<Method, Object>>> classes = new HashMap<>();

	public static synchronized void registerClass(Class clazz, Object instance) {
		Bootstrap.logger.info(String.format("Loading event handlers in class %s", clazz.getName()));
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
				Bootstrap.logger.info(String.format("Loaded event handler %s", method.getName()));
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
