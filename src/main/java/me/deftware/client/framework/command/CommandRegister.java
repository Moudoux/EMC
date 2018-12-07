package me.deftware.client.framework.command;

import com.mojang.brigadier.CommandDispatcher;
import me.deftware.client.framework.command.commands.CommandMods;
import net.minecraft.command.ISuggestionProvider;

/**
 * Handles custom EMC commands
 */
@SuppressWarnings("ALL")
public class CommandRegister {

	private static CommandDispatcher<ISuggestionProvider> dispatcher = new CommandDispatcher<ISuggestionProvider>();

	/**
	 * @return Brigadier dispatcher object
	 */
	public static CommandDispatcher<ISuggestionProvider> getDispatcher() {
		return dispatcher;
	}

	/**
	 * Registers a custom command
	 * @param command
	 */
	public static void registerCommand(CommandBuilder command) {
		dispatcher.register(command.build());
	}

	public static void registerCommand(EMCModCommand modCommand) {
		registerCommand(modCommand.getCommandBuilder());
	}

}
