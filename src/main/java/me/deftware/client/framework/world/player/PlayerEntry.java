package me.deftware.client.framework.world.player;

import me.deftware.client.framework.chat.ChatMessage;

import java.util.UUID;

/**
 * @author Deftware
 */
public interface PlayerEntry {

    /**
     * @return The profile UUID
     */
    UUID _getProfileID();

    /**
     * @return The profile username
     */
    String _getName();

    /**
     * @return The name as shown in the tab view
     */
    ChatMessage _getDisplayName();

}
