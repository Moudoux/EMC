package me.deftware.client.framework.command.commands;

import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.CommandResult;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.command.argument.arguments.EMCModArgument;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.utils.ChatProcessor;

import net.minecraft.client.Minecraft;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class CommandUnload extends EMCModCommand {

	@Override
	public CommandBuilder getCommandBuilder() {
		return new CommandBuilder().set(literal("unload")
				.then(
						argument("modname", new EMCModArgument())
								.executes(c -> {
									CommandResult r = new CommandResult(c);
									EMCMod mod = (EMCMod) r.getCustom("modname", EMCMod.class);
									Bootstrap.getMods().get(mod.modInfo.get("name").getAsString()).onUnload();
									Bootstrap.getMods().remove(mod.modInfo.get("name").getAsString());
									ChatProcessor.printFrameworkMessage("Unloaded " + mod.modInfo.get("name").getAsString());
									return 1;
								})
				)
				.executes(c -> {
					// Unload all mods
					Bootstrap.ejectMods();
					Minecraft.getInstance().gameSettings.gammaSetting = 0.5F;
					ChatProcessor.printFrameworkMessage("Unloaded all EMC mods, Minecraft is now running as vanilla");
					return 1;
				}));
	}

}
