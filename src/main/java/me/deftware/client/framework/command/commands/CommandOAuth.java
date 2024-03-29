package me.deftware.client.framework.command.commands;

import me.deftware.client.framework.chat.builder.ChatBuilder;
import me.deftware.client.framework.chat.style.ChatColors;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.oauth.OAuth;

/**
 * @author Deftware
 */
public class CommandOAuth extends EMCModCommand {

	@Override
	public CommandBuilder<?> getCommandBuilder() {
		return new CommandBuilder<>().addCommand("auth", result -> {
			new ChatBuilder().withPrefix().withText("Authenticating...").withColor(ChatColors.GRAY).build().print();
			OAuth.oAuth((status, code, time) -> {
				if (status) {
					new ChatBuilder().withPrefix().withText("Your authentication code is").withColor(ChatColors.GRAY).append().withSpace()
							.withText(code).setBold().withColor(ChatColors.AQUA).append().withSpace()
							.withText("and will expire").withColor(ChatColors.GRAY).append()
							.withText(time).withColor(ChatColors.RED).build().print();
				} else {
					new ChatBuilder().withPrefix().withText("Authentication failed").withColor(ChatColors.RED).build().print();
				}
			});
		});
	}

}
