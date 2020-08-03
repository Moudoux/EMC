package me.deftware.client.framework.chat.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.text.ClickEvent;

/**
 * @author Deftware
 */
public @AllArgsConstructor class ChatClickEvent {

	private @Getter final EventType eventType;
	private @Setter String data;

	public ClickEvent toEvent() {
		return new ClickEvent(eventType.toAction(), data);
	}

	public static ChatClickEvent fromEvent(ClickEvent event) {
		EventType type = EventType.OPEN_URL;
		for (EventType eventType : EventType.values()) {
			if (eventType.getName().equalsIgnoreCase(event.getAction().getName())) {
				type = eventType;
				break;
			}
		}
		return new ChatClickEvent(type, event.getValue());
	}

	public @AllArgsConstructor enum EventType {

		OPEN_URL("open_url"),
		OPEN_FILE("open_file"),
		RUN_COMMAND("run_command"),
		SUGGEST_COMMAND("suggest_command"),
		CHANGE_PAGE("change_page"),
		COPY_TO_CLIPBOARD("copy_to_clipboard");

		private @Getter final String name;

		public ClickEvent.Action toAction() {
			for (ClickEvent.Action action : ClickEvent.Action.values()) {
				if (action.getName().equalsIgnoreCase(name)) {
					return action;
				}
			}
			return null;
		}

	}

}
