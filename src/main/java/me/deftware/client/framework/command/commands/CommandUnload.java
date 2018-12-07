package me.deftware.client.framework.command.commands;

import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.utils.ChatProcessor;
import net.minecraft.client.Minecraft;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class CommandUnload extends EMCModCommand {

	@Override
	public CommandBuilder getCommandBuilder() {
		CommandBuilder builder = new CommandBuilder();
		builder.then(literal("unload")
				.then(
						argument("modname", string())
								.executes(c -> {
									String arg = getString(c, "modname");
									// Unload specific EMC mod
									if (Bootstrap.getMods().containsKey(arg)) {
										Bootstrap.getMods().get(arg).onUnload();
										Bootstrap.getMods().remove(arg);
										ChatProcessor.printFrameworkMessage("Unloaded " + arg);
									} else {
										ChatProcessor.printFrameworkMessage("Could not find mod " + arg);
									}
									return 1;
								})
				)
				.executes(c -> {
					// Unload all mods
					Bootstrap.ejectMods();
					Minecraft.getMinecraft().gameSettings.gammaSetting = 0.5F;
					ChatProcessor.printFrameworkMessage("Unloaded all EMC mods, Minecraft is now running as vanilla");
					return 1;
				}));
		return builder;
	}

}
