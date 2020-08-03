package me.deftware.client.framework.command.commands;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.deftware.client.framework.chat.ChatBuilder;
import me.deftware.client.framework.chat.style.ChatColors;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.CommandResult;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.utils.render.NonScaledRenderer;

public class CommandScale extends EMCModCommand {

    @Override
    public CommandBuilder<?> getCommandBuilder() {
        return new CommandBuilder<>().set(LiteralArgumentBuilder.literal("scale")
                .then(
                        LiteralArgumentBuilder.literal("set")
                                .then(
                                        RequiredArgumentBuilder.argument("size", FloatArgumentType.floatArg())
                                                .executes(c -> {
                                                    CommandResult r = new CommandResult(c);
                                                    if (r.getFloat("size") < 0.2 || r.getFloat("size") > 4.0f) {
                                                        new ChatBuilder().withPrefix().withText("Error! Scale must be between 0.2 and 4.0!").withColor(ChatColors.RED).build().print();
                                                        return 1;
                                                    }
                                                    NonScaledRenderer.setScale(r.getFloat("size"));
                                                    Bootstrap.EMCSettings.putPrimitive("RENDER_SCALE", r.getFloat("size"));
                                                    new ChatBuilder().withPrefix().withText(
                                                            String.format("Gui scale has been set to \"%s\"", r.getFloat("size"))
                                                    ).withColor(ChatColors.GRAY).build().print();
                                                    return 1;
                                                })
                                )
                )
                .then(
                        LiteralArgumentBuilder.literal("restore")
                                .executes(c -> {
                                    NonScaledRenderer.setScale(1.0f);
                                    Bootstrap.EMCSettings.putPrimitive("RENDER_SCALE", 1.0f);
                                    new ChatBuilder().withPrefix().withText("Scale has been reset to 1.0!").withColor(ChatColors.GRAY).build().print();
                                    return 1;
                                })
                )
        );
    }

}
