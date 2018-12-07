package me.deftware.client.framework.command;

import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;

@SuppressWarnings("ALL")
public class CommandBuilder<T> {

	private LiteralArgumentBuilder builder;

	public CommandBuilder addCommand(String command, CommandExecution execution) {
		return then(Commands.literalArgument(command).executes(source -> {
			execution.onExecute(new CommandResult(source));
			return 1;
		}));
	}

	public CommandBuilder then(LiteralArgumentBuilder argument) {
		if (builder == null) {
			builder = argument;
		} else {
			builder.then(argument);
		}
		return this;
	}

	public ArgumentType<T> getEntityArgumentType() {
		return (ArgumentType<T>) EntityArgument.func_197094_d();
	}

	public LiteralArgumentBuilder build() {
		return builder;
	}

	@FunctionalInterface
	public interface CommandExecution {

		void onExecute(CommandResult result);

	}

}
