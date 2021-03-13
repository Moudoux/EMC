package me.deftware.client.framework.world.player;

import me.deftware.client.framework.chat.ChatMessage;
import net.minecraft.client.network.PlayerListEntry;

import java.util.UUID;

public class PlayerEntry {

    private final UUID uuid;
    private final String name;
    private ChatMessage displayName;

    public PlayerEntry(PlayerListEntry entry) {
        this.uuid = entry.getProfile().getId();
        this.name = entry.getProfile().getName();
        if (entry.getDisplayName() != null)
            this.displayName = new ChatMessage().fromText(entry.getDisplayName());
    }

    public PlayerEntry(UUID uuid, String name, ChatMessage displayName) {
        this.uuid = uuid;
        this.name = name;
        this.displayName = displayName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public ChatMessage getDisplayName() {
        return displayName;
    }

}
