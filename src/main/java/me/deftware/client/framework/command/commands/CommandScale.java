package me.deftware.client.framework.command.commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.CommandResult;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.utils.render.NonScaledRenderer;
import me.deftware.client.framework.wrappers.IChat;

public class CommandScale extends EMCModCommand {

    @Override
    public CommandBuilder getCommandBuilder() {
        return new CommandBuilder().set((LiteralArgumentBuilder) LiteralArgumentBuilder.literal("scale")
                .then(
                        LiteralArgumentBuilder.literal("set")
                                .then(
                                        RequiredArgumentBuilder.argument("size", FloatArgumentType.floatArg())
                                                .executes(c -> {
                                                    CommandResult r = new CommandResult(c);
                                                    if (r.getFloat("size") < 0.2 || r.getFloat("size") > 4.0f) {
                                                        IChat.sendClientMessage("Error! Scale must be between 0.2 and 4.0");
                                                        return 1;
                                                    }
                                                    NonScaledRenderer.setScale(r.getFloat("size"));
                                                    Bootstrap.EMCSettings.saveFloat("RENDER_SCALE", r.getFloat("size"));
                                                    IChat.sendClientMessage("Gui scale has been set to \"" + r.getFloat("size") + "\"");
                                                    return 1;
                                                })
                                )
                )
                .then(
                        LiteralArgumentBuilder.literal("restore")
                                .executes(c -> {
                                    NonScaledRenderer.setScale(1.0f);
                                    Bootstrap.EMCSettings.saveFloat("RENDER_SCALE", 1.0f);
                                    IChat.sendClientMessage("Gui scale has been reset to \"1.0\"");
                                    return 1;
                                })
                )
        );
    }

}
