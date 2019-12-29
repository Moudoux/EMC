package me.deftware.client.framework.command.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.CommandResult;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.wrappers.IChat;

@SuppressWarnings("ALL")
public class CommandTrigger extends EMCModCommand {

    @Override
    public CommandBuilder getCommandBuilder() {
        return new CommandBuilder().set((LiteralArgumentBuilder) LiteralArgumentBuilder.literal("trigger")
                .then(
                        LiteralArgumentBuilder.literal("set")
                                .then(
                                        RequiredArgumentBuilder.argument("prefix", StringArgumentType.string())
                                                .executes(c -> {
                                                    CommandResult r = new CommandResult(c);
                                                    if (r.getString("prefix").trim().isEmpty()) {
                                                        IChat.sendClientMessage("Please enter a valid trigger prefix");
                                                        return 1;
                                                    }
                                                    SettingsMap.update(SettingsMap.MapKeys.EMC_SETTINGS, "COMMAND_TRIGGER", r.getString("prefix"));
                                                    Bootstrap.EMCSettings.saveString("commandtrigger", r.getString("prefix"));
                                                    IChat.sendClientMessage("Set command trigger to \"" + r.getString("prefix") + "\"");
                                                    return 1;
                                                })
                                )
                )
                .then(
                        LiteralArgumentBuilder.literal("restore")
                                .executes(c -> {
                                    SettingsMap.update(SettingsMap.MapKeys.EMC_SETTINGS, "COMMAND_TRIGGER", ".");
                                    Bootstrap.EMCSettings.saveString("commandtrigger", ".");
                                    IChat.sendClientMessage("Command trigger has been reset to \".\" (single dot)");
                                    return 1;
                                })
                )
        );
    }

}
