package me.deftware.client.framework.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
/**
 * Wrapper for building EMC commands
 */
public class CommandBuilder<T> {

	private LiteralArgumentBuilder<?> builder;
	private List<String> aliases = new ArrayList<>();

	/**
	 * Adds a single command (.test for example) with no argument, recommended for simple
	 * no argument commands
	 *
	 * @param command
	 * @param execution
	 * @return CommandBuilder
	 */
	public CommandBuilder addCommand(String command, CommandExecution execution) {
		return set(Commands.literal(command).executes(source -> {
			execution.onExecute(new CommandResult(source));
			return 1;
		}));
	}

	/**
	 * Set's the LiteralArgumentBuilder of this commandbuilder, used for building your own command tree
	 *
	 * @param argument
	 * @return
	 */
	public CommandBuilder set(LiteralArgumentBuilder argument) {
		builder = argument;
		return this;
	}

	/**
	 * Appends a literal or an argument to the existing command tree
	 *
	 * @param argument
	 * @return
	 */
	public CommandBuilder append(LiteralArgumentBuilder argument) {
		if (builder == null) {
			builder = argument;
		} else {
			builder.then(argument);
		}
		return this;
	}

	/**
	 * Registers an alternate alias for the command
	 *
	 * @param alias
	 * @return
	 */
	public CommandBuilder registerAlias(String alias) {
		aliases.add(alias);
		return this;
	}

	/**
	 * Autocomplete list of all entities on the current server
	 *
	 * @return
	 */
	public ArgumentType<T> getEntityArgumentType() {
		return (ArgumentType<T>) EntityArgument.players();
	}

	/**
	 * Returns a list of all aliases for this command
	 *
	 * @return
	 */
	public List<String> getAliases() {
		return aliases;
	}

	protected LiteralArgumentBuilder build() {
		return builder;
	}

	@FunctionalInterface
	public interface CommandExecution {

		void onExecute(CommandResult result);

	}

}
