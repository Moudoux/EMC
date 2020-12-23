package me.deftware.client.framework.command.commands;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.chat.LiteralChatMessage;
import me.deftware.client.framework.chat.style.ChatColors;
import me.deftware.client.framework.command.CommandRegister;
import me.deftware.client.framework.command.types.AbstractPagedOutputCommand;

import java.util.List;
import java.util.stream.Collectors;

public class CommandHelp extends AbstractPagedOutputCommand {

    public CommandHelp() {
        super("help", new LiteralChatMessage("Client Commands", ChatColors.GREEN));
    }

    @Override
    public List<ChatMessage> list() {
        return CommandRegister.getCommandsAndUsage()
                .stream()
                .map(s -> new LiteralChatMessage(CommandRegister.getCommandTrigger() + s, ChatColors.GRAY))
                .collect(Collectors.toList());
    }

}
