package me.deftware.client.framework.event;

import java.lang.reflect.Method;

public class Listener {
    Method method;
    Object classInstance;

    public Listener(Method method, Object classInstance) {
        this.method = method;
        this.classInstance = classInstance;
    }

    public Method getMethod() {
        return method;
    }

    public Object getClassInstance() {
        return classInstance;
    }
}
