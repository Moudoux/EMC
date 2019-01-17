package me.deftware.client.framework.event;

import me.deftware.client.framework.main.Bootstrap;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class EventBus {

	private static ConcurrentHashMap<Class, ConcurrentHashMap<Class, ConcurrentHashMap<Method, Object>>> classes = new ConcurrentHashMap<>();

	public static void clearEvents() {
		classes.clear();
	}

	public static synchronized void registerClass(Class clazz, Object instance) {
		Bootstrap.logger.debug(String.format("Loading event handlers in class %s", clazz.getName()));
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(EventHandler.class)) {
				if (!method.isAccessible()) {
					Bootstrap.logger.debug(String.format("Making method %s accessible", method.getName()));
					method.setAccessible(true);
				}
				EventHandler annotation = method.getAnnotation(EventHandler.class);
				Class eventType = annotation.eventType();
				if (!classes.containsKey(eventType)) {
					classes.put(eventType, new ConcurrentHashMap<>());
				}
				if (!classes.get(eventType).containsKey(clazz)) {
					classes.get(eventType).put(clazz, new ConcurrentHashMap<>());
				}
				classes.get(eventType).get(clazz).putIfAbsent(method, instance);
				Bootstrap.logger.debug(String.format("Loaded event handler for method %s", method.getName()));
			}
		}
	}

	public static synchronized void unRegisterClass(Class clazz) {
		classes.forEach((event, map) -> {
			if (map.containsKey(clazz)) {
				classes.get(event).remove(clazz);
			}
		});
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
