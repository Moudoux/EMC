package me.deftware.client.framework.command;

import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

@SuppressWarnings("ALL")
public class CommandBuilder {

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

	public LiteralArgumentBuilder build() {
		return builder;
	}

	@FunctionalInterface
	public interface CommandExecution {

		void onExecute(CommandResult result);

	}

	public class CommandResult {

		private CommandContext<CommandSource> context;

		public CommandResult(CommandContext<CommandSource> context) {
			this.context = context;
		}

		public String getString(String node) {
			return StringArgumentType.getString(context, node);
		}

		public int getInteger(String node) {
			return IntegerArgumentType.getInteger(context, node);
		}

		public float getFloat(String node) {
			return FloatArgumentType.getFloat(context, node);
		}

		public double getDouble(String node) {
			return DoubleArgumentType.getDouble(context, node);
		}

		public boolean getBoolean(String node) {
			return BoolArgumentType.getBool(context, node);
		}

	}

}
