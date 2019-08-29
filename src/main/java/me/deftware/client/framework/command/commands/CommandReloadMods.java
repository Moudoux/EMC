package me.deftware.client.framework.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.wrappers.IChat;

public class CommandReloadMods extends EMCModCommand {
    @Override
    public CommandBuilder getCommandBuilder() {
        return new CommandBuilder().set((LiteralArgumentBuilder) LiteralArgumentBuilder.literal("rl")
                .executes(c -> {
                    IChat.sendClientMessage("Reloading mods ...");
                    Bootstrap.reloadMods();
                    return 1;
                })
        );
    }
}