package me.deftware.client.framework.command;

import com.mojang.brigadier.arguments.*;
import com.mojang.brigadier.context.CommandContext;
import me.deftware.client.framework.wrappers.entity.IEntity;
import net.minecraft.command.arguments.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;

@SuppressWarnings("ALL")
public class CommandResult {

	private CommandContext<?> context;

	public CommandResult(CommandContext<?> context) {
		this.context = context;
	}

	public String getString(String node) {
		return StringArgumentType.getString(context, node);
	}

	public IEntity getEntity(String node) throws Exception {
		try {
			return new IEntity(EntityArgumentType.getEntity((CommandContext<ServerCommandSource>) context, node));
		} catch (Exception ex) {
			// Will throw most likely if not in a Server
			return null;
		}
	}

	public String getEntityName(String node) throws Exception {
		return getEntity(node).getName();
	}

	public Object getCustom(String node, Class<?> clazz) {
		return context.getArgument(node, clazz);
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
