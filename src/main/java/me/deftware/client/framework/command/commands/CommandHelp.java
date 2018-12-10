package me.deftware.client.framework.command.commands;

import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.CommandRegister;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.wrappers.IChat;

public class CommandHelp extends EMCModCommand {

	@Override
	public CommandBuilder getCommandBuilder() {
		return new CommandBuilder().addCommand("help", result -> {
			for (String cmd : CommandRegister.getCommandsAndUsage()) {
				IChat.sendClientMessage("." + cmd);
			}
		});
	}

}
