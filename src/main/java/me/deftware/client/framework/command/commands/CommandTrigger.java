package me.deftware.client.framework.command.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.deftware.client.framework.chat.builder.ChatBuilder;
import me.deftware.client.framework.chat.style.ChatColors;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.CommandResult;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.maps.SettingsMap;

/**
 * @author Deftware
 */
public class CommandTrigger extends EMCModCommand {

    @Override
    public CommandBuilder<?> getCommandBuilder() {
        return new CommandBuilder<>().set(LiteralArgumentBuilder.literal("trigger")
                .then(
                        LiteralArgumentBuilder.literal("set")
                                .then(
                                        RequiredArgumentBuilder.argument("prefix", StringArgumentType.string())
                                                .executes(c -> {
                                                    CommandResult r = new CommandResult(c);
                                                    if (r.getString("prefix").trim().isEmpty()) {
                                                        new ChatBuilder().withPrefix().withText("Please enter a valid trigger prefix").withColor(ChatColors.RED).build().print();
                                                        return 1;
                                                    }
                                                    SettingsMap.update(SettingsMap.MapKeys.EMC_SETTINGS, "COMMAND_TRIGGER", r.getString("prefix"));
                                                    Bootstrap.EMCSettings.putPrimitive("commandtrigger", r.getString("prefix"));
                                                    new ChatBuilder().withPrefix().withText(
                                                            String.format("Set command trigger to \"%s\"", r.getString("prefix"))
                                                    ).withColor(ChatColors.GRAY).build().print();
                                                    return 1;
                                                })
                                )
                )
                .then(
                        LiteralArgumentBuilder.literal("restore")
                                .executes(c -> {
                                    SettingsMap.update(SettingsMap.MapKeys.EMC_SETTINGS, "COMMAND_TRIGGER", ".");
                                    Bootstrap.EMCSettings.putPrimitive("commandtrigger", ".");
                                    new ChatBuilder().withPrefix().withText("Command trigger has been reset to \".\" (single dot)").withColor(ChatColors.RED).build().print();
                                    return 1;
                                })
                )
        );
    }

}
