package me.deftware.client.framework.command.commands;

import me.deftware.client.framework.chat.ChatBuilder;
import me.deftware.client.framework.chat.style.ChatColors;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.CommandRegister;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.maps.SettingsMap;

/**
 * @author Deftware
 */
public class CommandHelp extends EMCModCommand {

	@Override
	public CommandBuilder<?> getCommandBuilder() {
		return new CommandBuilder<>().addCommand("help", result -> {
			String trigger = (String) SettingsMap.getValue(SettingsMap.MapKeys.EMC_SETTINGS, "COMMAND_TRIGGER", ".");
			for (String cmd : CommandRegister.getCommandsAndUsage()) {
				new ChatBuilder().withPrefix().withText(trigger).withText(cmd).withColor(ChatColors.GRAY).build().print();
			}
		});
	}

}
