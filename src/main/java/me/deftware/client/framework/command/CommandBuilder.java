package me.deftware.client.framework.command;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Wrapper for building EMC commands
 *
 * @author Deftware
 */
public class CommandBuilder<T> {

    private LiteralArgumentBuilder<ServerCommandSource> builder;
    private final List<String> aliases = new ArrayList<>();

    /**
     * Adds a single command (.test for example) with no argument, recommended for simple
     * no argument commands
     *
     * @param command The command to add
     * @param execution The execution data for the command
     * @return CommandBuilder
     */
    public CommandBuilder<?> addCommand(String command, Consumer<CommandResult> execution) {
        return set(CommandManager.literal(command).executes(source -> {
            execution.accept(new CommandResult(source));
            return 1;
        }));
    }

    /**
     * Set's the LiteralArgumentBuilder of this commandbuilder, used for building your own command tree
     *
     * @param argument the LiteralArgumentBuilder of this commandbuilder
     * @return The adjusted command builder
     */
    public CommandBuilder<?> set(LiteralArgumentBuilder<?> argument) {
        builder = (LiteralArgumentBuilder<ServerCommandSource>) argument;
        return this;
    }

    /**
     * Appends a literal or an argument to the existing command tree
     *
     * @param argument the argument to append
     * @return the adjusted command builder
     */
    public CommandBuilder<?> append(LiteralArgumentBuilder<ServerCommandSource> argument) {
        if (builder == null) {
            builder = argument;
        } else {
            builder.then(argument);
        }
        return this;
    }

    /**
     * Registers an alternate alias for the command
     *
     * @param alias The alias to register
     * @return the adjusted command builder
     */
    public CommandBuilder<?> registerAlias(String alias) {
        aliases.add(alias);
        return this;
    }

    /**
     * Autocomplete list of all entities on the current server
     *
     * @return a list of all entities on the current server
     */
    public ArgumentType<T> getEntityArgumentType() {
        return (ArgumentType<T>) EntityArgumentType.players();
    }

    /**
     * Returns a list of all aliases for this command
     *
     * @return a list of all aliases for this command
     */
    public List<String> getAliases() {
        return aliases;
    }

    protected LiteralArgumentBuilder<ServerCommandSource> build() {
        return builder;
    }

}
