package me.deftware.client.framework.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

	@Deprecated
	Class<?> eventType() default Event.class;

}
