package me.deftware.client.framework.command.argument;

import com.mojang.brigadier.Message;

public class ArgumentExceptionFunction implements Message {

	private String message;

	public ArgumentExceptionFunction(String message) {
		this.message = message;
	}

	@Override
	public String getString() {
		return message;
	}

}
