package me.deftware.client.framework.event;

/**
 * @author Deftware
 */
public class EventListener {

	public EventListener() {
		EventBus.registerClass(this.getClass(), this);
	}

}
