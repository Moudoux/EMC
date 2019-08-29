package me.deftware.client.framework.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.wrappers.IChat;
import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.client.framework.wrappers.world.IWorld;

public class CommandReload extends EMCModCommand {

    @Override
    public CommandBuilder getCommandBuilder() {
        return new CommandBuilder().set((LiteralArgumentBuilder) LiteralArgumentBuilder.literal("reload")
                .then(
                        LiteralArgumentBuilder.literal("skins")
                                .executes(c -> {
                                    IChat.sendClientMessage("Reloading skins...");
                                    for (IEntity entity : IWorld.getLoadedEntities()) {
                                        entity.reloadSkin();
                                    }
                                    return 1;
                                })
                )
                .then(
                        LiteralArgumentBuilder.literal("mods")
                                .executes(c -> {
                                    IChat.sendClientMessage("Reloading mods ...");
                                    Bootstrap.reloadMods();
                                    return 1;
                                })
                )
        );
    }

}