package me.deftware.client.framework.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ISuggestionProvider;

import java.util.ArrayList;
import java.util.Map;

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
	 * Registers a commandbuilder
	 * @param command
	 */
	public static void registerCommand(CommandBuilder command) {
		CommandNode<?> node = dispatcher.register(command.build());
		for (Object alias : command.getAliases()) {
			LiteralArgumentBuilder argumentBuilder = LiteralArgumentBuilder.literal((String) alias);
			dispatcher.register((LiteralArgumentBuilder) argumentBuilder.redirect(node));
		}
	}

	/**
	 * Returns an array of all registered commands, without any argument usage
	 * @return
	 */
	public static ArrayList<String> listCommands() {
		ArrayList<String> commands = new ArrayList<>();
		RootCommandNode<?> rootNode = dispatcher.getRoot();
		for (CommandNode<?> child : rootNode.getChildren()) {
			commands.add(child.getName());
		}
		return commands;
	}

	/**
	 * Returns an array of all registered commands, with argument usage
	 * @return
	 */
	public static ArrayList<String> getCommandsAndUsage() {
		ArrayList<String> commands = new ArrayList<>();
		Map<CommandNode<ISuggestionProvider>, String> map = getSmartUsage();
		for (String cmd : map.values()) {
			commands.add(cmd);
		}
		return commands;
	}

	/**
	 * Returns a map of all root commands with their correct usage
	 * @return
	 */
	public static Map<CommandNode<ISuggestionProvider>, String> getSmartUsage() {
		return dispatcher.getSmartUsage(dispatcher.getRoot(), Minecraft.getMinecraft().player.func_195051_bN());
	}

	/**
	 * Registers a EMCModCommand
	 * @param command
	 */
	public static void registerCommand(EMCModCommand modCommand) {
		registerCommand(modCommand.getCommandBuilder());
	}

}
