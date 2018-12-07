package me.deftware.client.framework.command.commands;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.utils.ChatProcessor;

public class CommandVersion extends EMCModCommand {

	@Override
	public CommandBuilder getCommandBuilder() {
		CommandBuilder builder = new CommandBuilder();
		builder.addCommand("version", result -> {
			ChatProcessor.printFrameworkMessage("You are running " + FrameworkConstants.FRAMEWORK_NAME
					+ " version " + FrameworkConstants.VERSION + "." + FrameworkConstants.PATCH + " built by " + FrameworkConstants.AUTHOR);
		});
		return builder;
	}

}
