package me.deftware.client.framework.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.Map;

/**
 * Handles custom EMC commands
 *
 * @author Deftware
 */
public class CommandRegister {

    private static CommandDispatcher<ServerCommandSource> dispatcher = new CommandDispatcher<>();

    /**
     * @return Brigadier dispatcher object
     */
    public static CommandDispatcher<ServerCommandSource> getDispatcher() {
        return dispatcher;
    }

    /**
     * Clears the Brigadier dispatcher object
     * (restores to the dafault state - no commands loaded)
     */
    public static void clearDispatcher() {
        dispatcher = new CommandDispatcher<>();
    }

    /**
     * Registers a commandbuilder
     *
     * @param command The command to register
     */
    public static synchronized void registerCommand(CommandBuilder<?> command) {
        CommandNode<ServerCommandSource> node = dispatcher.register(command.build());
        for (Object alias : command.getAliases()) {
            LiteralArgumentBuilder<ServerCommandSource> argumentBuilder = LiteralArgumentBuilder.literal((String) alias);
            dispatcher.register(argumentBuilder.redirect(node));
        }
    }

    /**
     * Registers a EMCModCommand
     *
     * @param modCommand The command to register
     */
    public static void registerCommand(EMCModCommand modCommand) {
        registerCommand(modCommand.getCommandBuilder());
    }

    /**
     * Returns an array of all registered commands, without any argument usage
     *
     * @return an array of all registered commands, without any argument usage
     */
    public static ArrayList<String> listCommands() {
        ArrayList<String> commands = new ArrayList<>();
        RootCommandNode<?> rootNode = dispatcher.getRoot();
        for (CommandNode<?> child : rootNode.getChildren()) {
            commands.add(child.getName());
        }
        return commands;
    }

    /**
     * Returns an array of all registered commands, with argument usage
     *
     * @return an array of all registered commands, with argument usage
     */
    public static ArrayList<String> getCommandsAndUsage() {
        Map<CommandNode<ServerCommandSource>, String> map = getSmartUsage();
        return new ArrayList<>(map.values());
    }

    /**
     * Returns a map of all root commands with their correct usage
     *
     * @return a map of all root commands with their correct usage
     */
    public static Map<CommandNode<ServerCommandSource>, String> getSmartUsage() {
        return dispatcher.getSmartUsage(dispatcher.getRoot(), MinecraftClient.getInstance().player.getCommandSource());
    }

    /**
     * Returns the command trigger used to trigger commands, default is a .
     *
     * @return the command trigger used to trigger commands, default is a .
     */
    public static String getCommandTrigger() {
        return Bootstrap.EMCSettings.getPrimitive("commandtrigger", ".");
    }

}
