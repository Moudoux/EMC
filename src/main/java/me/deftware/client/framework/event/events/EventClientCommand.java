package me.deftware.client.framework.event.events;

import me.deftware.client.framework.event.Event;

@Deprecated
public class EventClientCommand extends Event {

	private String command, args, full, trigger;

	public EventClientCommand(String command, String trigger) {
		full = command;
		this.trigger = trigger;
		if (command.contains(" ")) {
			this.command = command.split(" ")[0];
			args = command.replace(this.command + " ", "");
		} else {
			args = "";
			this.command = command;
		}
	}

	public String getCommand() {
		return command;
	}

	public String getArgs() {
		return args;
	}

	public String getFull() {
		return full;
	}

}
