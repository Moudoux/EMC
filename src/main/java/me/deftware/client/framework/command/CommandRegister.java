package me.deftware.client.framework.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.tree.CommandNode;

import java.util.ArrayList;
import java.util.Map;

/**
 * Handles custom EMC commands
 */
public class CommandRegister {

	private static CommandDispatcher<Object> dispatcher = new CommandDispatcher<>();
	private static ArrayList<AbstractCommand> commands = new ArrayList<>();

	/**
	 * @return the Brigadier dispatcher object
	 */
	public static CommandDispatcher<Object> getDispatcher() {
		return dispatcher;
	}

	/**
	 * Registers a custom command
	 * @param command
	 */
	public static void registerCommand(AbstractCommand command) {
		dispatcher.register(command.getMainStructure());
		commands.add(command);
	}

	/**
	 * Takes a string as a command and dispatches it to all registered commands
	 * @param input
	 * @return
	 */
	public static CommandResult dispatchCommand(String input) {
		final ParseResults<Object> parse = dispatcher.parse(input, CommandRegister.class);
		try {
			return new CommandResult(true, dispatcher.execute(parse), "");
		} catch (Exception ex) {
			return new CommandResult(false, 0, ex.getMessage());
		}
	}

	/**
	 * Returns commands completion for a command, takes a string as an input and returns an array of all
	 * possible outcomes
	 *
	 * @param input
	 * @return
	 */
	public static String[] getCommandCompletion(String input) {
		final ParseResults<Object> parse = dispatcher.parse(input, CommandRegister.class);
		CommandNode lastNode = null;
		for (Map.Entry<CommandNode<Object>, StringRange> entry : parse.getContext().getNodes().entrySet()) {
			lastNode = entry.getKey();
		}
		return dispatcher.getAllUsage(lastNode, CommandRegister.class, false);
	}


}
