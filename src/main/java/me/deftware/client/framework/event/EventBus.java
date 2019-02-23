package me.deftware.client.framework.event;

import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.utils.MultiMap;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

public class EventBus {
    private static MultiMap<Class, Listener> listeners = new MultiMap<>();
    public static final Object lock = new Object();

    public static synchronized void registerClass(Class clazz, Object instance) {
        synchronized (lock) {
            Bootstrap.logger.debug(String.format("Loading event handlers in class %s", clazz.getName()));
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(EventHandler.class)) {
                    if (!method.isAccessible()) {
                        Bootstrap.logger.debug(String.format("Making method %s accessible", method.getName()));
                        method.setAccessible(true);
                    }
                    EventHandler annotation = method.getAnnotation(EventHandler.class);
                    Class eventType = annotation.eventType();
                    listeners.putIfAbsent(eventType, new Listener(method, instance));
                    Bootstrap.logger.debug(String.format("Loaded event handler for method %s", method.getName()));
                    System.out.println("Added event: " + clazz.getName());
                }
            }
        }
    }

    public static synchronized void unRegisterClass(Class clazz) {
        synchronized (lock) {
            HashMap<Class, Listener> removeList = new HashMap<>();
            for (Class event : listeners.keySet()) {
                Collection<Listener> listenerCollection = listeners.get(event);
                for (Listener listener : listenerCollection) {
                    if (listener.getClassInstance().getClass() == clazz) {
                        removeList.put(event, listener);
                        System.out.println("Unregistered " + listener.getClassInstance().getClass().getName());
                    }
                }

            }
            removeList.forEach((key, value) -> {
                listeners.remove(key, value);
            });

        }
    }

    public static void clearEvents() {
        synchronized (lock) {
            listeners.clear();
        }
        System.gc(); //Clear event instances left in the VM
    }

    public static void sendEvent(Event event) {
        Collection<Listener> listenerCollection = listeners.get(event.getClass());
        if (listenerCollection != null) {
            try {
                for (Listener listener : listenerCollection) {
                    listener.getMethod().invoke(listener.getClassInstance(), event);
                }
            } catch (Exception e) {
                Bootstrap.logger.error("Cannot invoke event listener " + e.getMessage());
            }
        }
    }
}
