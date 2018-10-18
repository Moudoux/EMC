package me.deftware.client.framework.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand {

	private LiteralArgumentBuilder<Object> mainStructure;

	protected abstract LiteralArgumentBuilder<Object> getCommandStructure();

	protected abstract int execute(CommandContext command, int args);

	/**
	 * Using this method you can register another command structure and provide auto completion
	 *
	 * @return
	 */
	protected List<LiteralArgumentBuilder> registerOptionalCompletions() { return new ArrayList<>(); }

	protected boolean hasArgument(CommandContext context, String name, Class clazz) {
		try {
			context.getArgument(name, clazz);
			return true;
		} catch (IllegalArgumentException ex) {
			return false;
		}
	}

	public LiteralArgumentBuilder<Object> getMainStructure() {
		return mainStructure = getCommandStructure();
	}

	public AbstractCommand() {
		registerOptionalCompletions().forEach(builder -> CommandRegister.getDispatcher().register(builder));
	}

}
