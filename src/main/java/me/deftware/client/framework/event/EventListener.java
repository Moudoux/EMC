package me.deftware.client.framework.event;

public class EventListener {

	public EventListener() {
		EventBus.registerClass(this.getClass(), this);
	}

}
