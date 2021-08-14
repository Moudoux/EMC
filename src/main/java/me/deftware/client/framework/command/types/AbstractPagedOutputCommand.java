package me.deftware.client.framework.command.types;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.chat.LiteralChatMessage;
import me.deftware.client.framework.chat.builder.ChatBuilder;
import me.deftware.client.framework.chat.event.ChatClickEvent;
import me.deftware.client.framework.chat.hud.ChatHud;
import me.deftware.client.framework.chat.hud.HudLine;
import me.deftware.client.framework.chat.style.ChatColors;
import me.deftware.client.framework.command.CommandBuilder;
import me.deftware.client.framework.command.CommandRegister;
import me.deftware.client.framework.command.CommandResult;
import me.deftware.client.framework.command.EMCModCommand;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Deftware
 */
public abstract class AbstractPagedOutputCommand extends EMCModCommand {

    protected final ChatMessage title;
    protected final String command;
    protected int chunkSize = 6;

    public AbstractPagedOutputCommand(String command, ChatMessage title) {
        this.command = command;
        this.title = title;
    }

    public abstract List<ChatMessage> list();

    public List<List<ChatMessage>> getChunks() {
        return Lists.partition(list(), chunkSize);
    }

    @Override
    public CommandBuilder<?> getCommandBuilder() {
        return new CommandBuilder<>().set(LiteralArgumentBuilder.literal(command)
                .then(
                        RequiredArgumentBuilder.argument("page", IntegerArgumentType.integer(1))
                                .executes(c ->
                                        onExecute(new CommandResult(c).getInteger("page") - 1)
                                )
                )
                .executes(c -> onExecute(0))
        );
    }

    private final List<ChatMessage> previousOuput = new ArrayList<>();

    protected void removePreviousOutput() {
        List<HudLine> removalQueue = new ArrayList<>();
        for (HudLine line : ChatHud.getLines()) {
            for (ChatMessage message : previousOuput) {
                if (line.getMessage().toString(false).equalsIgnoreCase(message.toString(false))) {
                    removalQueue.add(line);
                }
            }
        }
        removalQueue.forEach(ChatHud::remove);
        previousOuput.clear();
    }

    protected void print(ChatMessage message) {
        previousOuput.add(message);
        message.print();
    }

    protected int onExecute(int page) {
        String prefix = CommandRegister.getCommandTrigger();

        // Remove old output
        if (!previousOuput.isEmpty()) {
            removePreviousOutput();
        }

       if (list().isEmpty()) {
           print(new ChatBuilder().withMessage(title).build());
           print(new LiteralChatMessage("Nothing added", ChatColors.GRAY));
       } else {
           // Get chunk
           List<List<ChatMessage>> chunks = getChunks();
           if (page < 0) page = 0;
           if (page >= chunks.size()) page = chunks.size() - 1;
           List<ChatMessage> chunk = chunks.get(page);

           // Send title
           print(new ChatBuilder().withMessage(title).withSpace()
                   .withText(String.format("Page (%s/%s)", page + 1, chunks.size())).withColor(ChatColors.AQUA)
                   .build());

           // Send chunk
           chunk.forEach(this::print);

           if (chunks.size() > 1) {
               // Send navigation buttons
               ChatBuilder navigationButtons = new ChatBuilder();
               if (page > 0) navigationButtons.withText("<<").withColor(ChatColors.AQUA).withClickEvent(
                       new ChatClickEvent(ChatClickEvent.EventType.RUN_COMMAND, String.format("%s%s %s", prefix, command, page))
               ).withHover("Previous page").append().withSpace();
               if (page + 1 < chunks.size()) navigationButtons.withText(">>").withColor(ChatColors.AQUA).withClickEvent(
                       new ChatClickEvent(ChatClickEvent.EventType.RUN_COMMAND, String.format("%s%s %s", prefix, command, page + 2))
               ).withHover("Next page").append();
               print(navigationButtons.build());
           }
       }

        return 1;
    }

}
