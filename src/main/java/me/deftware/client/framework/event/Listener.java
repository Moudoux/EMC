package me.deftware.client.framework.event;

import java.lang.reflect.Method;

/**
 * @author Deftware
 */
public class Listener {

    private final Method method;
    private final Object classInstance;
    private final int priority;

    public Listener(Method method, Object classInstance, int priority) {
        if (method == null || classInstance == null) {
            throw new RuntimeException("Null params");
        }
        this.method = method;
        this.classInstance = classInstance;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public Method getMethod() {
        return method;
    }

    public Object getClassInstance() {
        return classInstance;
    }

}
