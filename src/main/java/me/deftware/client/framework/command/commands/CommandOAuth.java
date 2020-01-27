package me.deftware.client.framework.command.commands;

import me.deftware.client.framework.apis.oauth.OAuth;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.utils.ChatColor;
import me.deftware.client.framework.wrappers.IChat;

public class CommandOAuth extends EMCModCommand {

	@Override
	public CommandBuilder<?> getCommandBuilder() {
		return new CommandBuilder<>().addCommand("auth", result -> {
			IChat.sendClientMessage("Authenticating...");
			OAuth.oAuth((status, code, time) -> {
				if (status) {
					IChat.sendClientMessage("Your authentication code is " + ChatColor.BOLD + ChatColor.AQUA + code + ChatColor.RESET + ChatColor.GRAY + ", your code will expire in " + ChatColor.RED + time);
				} else {
					IChat.sendClientMessage("Authentication failed.");
				}
			});
		});
	}

}
