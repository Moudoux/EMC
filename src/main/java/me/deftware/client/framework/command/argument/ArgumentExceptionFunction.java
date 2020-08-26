package me.deftware.client.framework.command.argument;

import com.mojang.brigadier.Message;

/**
 * @author Deftware
 */
public class ArgumentExceptionFunction implements Message {

	private final String message;

	public ArgumentExceptionFunction(String message) {
		this.message = message;
	}

	@Override
	public String getString() {
		return message;
	}

}
