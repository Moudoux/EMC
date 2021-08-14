package me.deftware.mixin.mixins.network;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.world.player.PlayerEntry;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;

import java.util.UUID;

/**
 * @author Deftware
 */
@Mixin(PlayerListEntry.class)
public class MixinPlayerListEntry implements PlayerEntry {

	@Override
	public UUID _getProfileID() {
		return ((PlayerListEntry) (Object) this).getProfile().getId();
	}

	@Override
	public String _getName() {
		return ((PlayerListEntry) (Object) this).getProfile().getName();
	}

	@Override
	public ChatMessage _getDisplayName() {
		Text displayName = ((PlayerListEntry) (Object) this).getDisplayName();
		if (displayName != null) {
			return new ChatMessage().fromText(displayName);
		}
		return null;
	}

}
