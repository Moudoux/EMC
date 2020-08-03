package me.deftware.client.framework.command.commands;

import me.deftware.client.framework.chat.ChatBuilder;
import me.deftware.client.framework.chat.style.ChatColors;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.EMCModCommand;
import me.deftware.client.framework.main.bootstrap.Bootstrap;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class CommandUnload extends EMCModCommand {

    @Override
    public CommandBuilder<?> getCommandBuilder() {
        return new CommandBuilder<>().set(literal("unload")
                /* Disable until a way to unload mod specific events/commands is made
                .then(
                        argument("modname", new EMCModArgument())
                                .executes(c -> {
                                    CommandResult r = new CommandResult(c);
                                    EMCMod mod = (EMCMod) r.getCustom("modname", EMCMod.class);
                                    Bootstrap.getMods().get(mod.modInfo.get("name").getAsString()).onUnload();
                                    Bootstrap.getMods().remove(mod.modInfo.get("name").getAsString());
                                    ChatProcessor.printFrameworkMessage("Unloaded " + mod.modInfo.get("name").getAsString());
                                    return 1;
                                })
                )*/
                .executes(c -> {
                    Bootstrap.ejectMods();
                    new ChatBuilder().withPrefix().withText("Unloaded all EMC mods, Minecraft is now running as vanilla").withColor(ChatColors.GRAY).build().print();
                    return 1;
                }));
    }

}
