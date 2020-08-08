package me.deftware.client.framework.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.deftware.client.framework.chat.ChatBuilder;
import me.deftware.client.framework.chat.style.ChatColors;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.client.framework.wrappers.world.IWorld;

public class CommandReload extends EMCModCommand {

    @Override
    public CommandBuilder<?> getCommandBuilder() {
        return new CommandBuilder<>().set(LiteralArgumentBuilder.literal("reload")
                .then(
                        LiteralArgumentBuilder.literal("skins")
                                .executes(c -> {
                                    new ChatBuilder().withPrefix().withText("Reloading skins...").withColor(ChatColors.GRAY).build().print();
                                    for (IEntity entity : IWorld.getLoadedEntities().values()) {
                                        entity.reloadSkin();
                                    }
                                    return 1;
                                })
                )
        );
    }

}


