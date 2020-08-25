package me.deftware.client.framework.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Deftware
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

	/**
	 * Default priority is 1, events are sent to the highest priority first
	 */
	int priority() default 1;

}
