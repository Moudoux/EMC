package me.deftware.client.framework.command.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.CommandResult;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.wrappers.IChat;

/**
 * Demo command demonstrating how to use argument
 */
public class TestCommand extends EMCModCommand {

	@Override
	public CommandBuilder<?> getCommandBuilder() {
		return new CommandBuilder<>().set(LiteralArgumentBuilder.literal("friends")
				.then(
						LiteralArgumentBuilder.literal("add")
								.then(
										RequiredArgumentBuilder.argument("username", StringArgumentType.string())
												.executes(c -> {
													onExecute(true, new CommandResult(c));
													return 1;
												})
								)
				)
				.then(
						LiteralArgumentBuilder.literal("remove")
								.then(
										RequiredArgumentBuilder.argument("username", StringArgumentType.string())
												.executes(c -> {
													onExecute(false, new CommandResult(c));
													return 1;
												})
								)
				)
				.then(
						LiteralArgumentBuilder.literal("list")
								.executes(c -> {
									IChat.sendClientMessage("No friends added");
									return 1;
								})
				)
		).registerAlias("f");
	}

	public void onExecute(boolean add, CommandResult result) {
		try {
			String username = result.getString("username");
			if (add) {
				IChat.sendClientMessage("Added " + username + " as a friend");
			} else {
				IChat.sendClientMessage("Removed " + username + " as a friend");
			}
		} catch (Exception ex) {
			IChat.sendClientMessage("Invalid player name");
			ex.printStackTrace();
		}
	}

}
