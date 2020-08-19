package me.deftware.client.framework.command.commands;

import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.chat.ChatBuilder;
import me.deftware.client.framework.chat.style.ChatColors;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.minecraft.Minecraft;

/**
 * @author Deftware
 */
public class CommandVersion extends EMCModCommand {

	@Override
	public CommandBuilder<?> getCommandBuilder() {
		return new CommandBuilder<>().addCommand("version", result -> {
			new ChatBuilder().withPrefix().withText("== EMC info ==").withColor(ChatColors.GRAY).build().print();
			new ChatBuilder().withPrefix().withText(FrameworkConstants.toDataString()).withColor(ChatColors.GRAY).build().print();
			new ChatBuilder().withPrefix().withText(String.format(
					"Minecraft version %s protocol %s",
					Minecraft.getMinecraftVersion(),
					Minecraft.getMinecraftProtocolVersion()
			)).withColor(ChatColors.GRAY).build().print();
			new ChatBuilder().withPrefix().withText(
					"EMC mappings is " + FrameworkConstants.MAPPING_LOADER.name()
			).withColor(ChatColors.GRAY).build().print();
			new ChatBuilder().withPrefix().withText(
					"EMC mapper is " + FrameworkConstants.MAPPING_SYSTEM.name()
			).withColor(ChatColors.GRAY).build().print();
		});
	}

}
