package me.deftware.client.framework.event;

import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.maps.MultiMap;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Deftware, Ananas
 */
public class EventBus {

    private static final Object lock = new Object();
    private static MultiMap<Class<?>, Listener> listeners = new MultiMap<>();

    public static synchronized void registerClass(Class<?> clazz, Object instance) {
        synchronized (lock) {
            Bootstrap.logger.debug(String.format("Loading event handlers in class %s", clazz.getName()));
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(EventHandler.class)) {
                    if (!method.isAccessible()) {
                        Bootstrap.logger.debug(String.format("Making method %s accessible", method.getName()));
                        method.setAccessible(true);
                    }
                    Class<? extends Event> eventType = method.getParameterTypes()[0].asSubclass(Event.class);
                    listeners.putIfAbsent(eventType, new Listener(method, instance));
                    Bootstrap.logger.debug(String.format("Loaded event handler for method %s", method.getName()));
                }
            }
        }
    }

    public static synchronized void unRegisterClass(Class<?> clazz) {
        synchronized (lock) {
            HashMap<Class<?>, Listener> removeList = new HashMap<>();
            for (Class<?> event : listeners.keySet()) {
                Collection<Listener> listenerCollection = listeners.get(event);
                for (Listener listener : listenerCollection) {
                    if (listener.getClassInstance().getClass() == clazz) {
                        removeList.put(event, listener);
                        Bootstrap.logger.debug("Unregistered " + listener.getClassInstance().getClass().getName());
                    }
                }
            }
            removeList.forEach((key, value) -> listeners.remove(key, value));
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
                    try {
                        listener.getMethod().invoke(listener.getClassInstance(), event);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } catch (Exception e) {
                Bootstrap.logger.error("Cannot invoke event listener " + e.getMessage());
            }
        }
    }

}
