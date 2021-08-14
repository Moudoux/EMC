package me.deftware.client.framework.command.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.deftware.client.framework.chat.builder.ChatBuilder;
import me.deftware.client.framework.chat.style.ChatColors;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.world.ClientWorld;
import me.deftware.client.framework.world.World;

/**
 * @author Deftware
 */
public class CommandReload extends EMCModCommand {

    @Override
    public CommandBuilder<?> getCommandBuilder() {
        return new CommandBuilder<>().set(LiteralArgumentBuilder.literal("reload")
                .then(
                        LiteralArgumentBuilder.literal("skins")
                                .executes(c -> {
                                    new ChatBuilder().withPrefix().withText("Reloading skins...").withColor(ChatColors.GRAY).build().print();
                                    ClientWorld.getClientWorld().getLoadedEntities().forEach(Entity::reloadSkin);
                                    return 1;
                                })
                )
        );
    }

}


