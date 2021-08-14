package me.deftware.client.framework.command.commands;

import me.deftware.client.framework.chat.builder.ChatBuilder;
import me.deftware.client.framework.chat.style.ChatColors;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.main.bootstrap.Bootstrap;

/**
 * @author Deftware
 */
public class CommandMods extends EMCModCommand {

	@Override
	public CommandBuilder<?> getCommandBuilder() {
		return new CommandBuilder<>().addCommand("mods", result -> {
			new ChatBuilder().withPrefix().withText(
					Bootstrap.modsInfo == null || Bootstrap.getMods().isEmpty() ?
							 "No EMC mods are loaded" : "== Loaded EMC Mods =="
			).withColor(ChatColors.GRAY).build().print();
			if (Bootstrap.modsInfo != null || !Bootstrap.getMods().isEmpty()) {
				Bootstrap.getMods().values().forEach(mod ->
					new ChatBuilder().withPrefix().withText(String.format(
							"%s by %s version %s",
							mod.getMeta().getName(),
							mod.getMeta().getAuthor(),
							mod.getMeta().getVersion())
					).withColor(ChatColors.GRAY).build().print()
				);
			}
		});
	}

}
