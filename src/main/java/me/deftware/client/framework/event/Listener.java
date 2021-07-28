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
        this.method = method;
        this.classInstance = classInstance;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public Object getClassInstance() {
        return classInstance;
    }

    public void invoke(Event event) throws Exception {
        method.invoke(getClassInstance(), event);
    }

}
