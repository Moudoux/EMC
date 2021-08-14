package me.deftware.mixin.imp;

import me.deftware.client.framework.chat.hud.HudLine;
import net.minecraft.text.LiteralText;

import java.util.List;

public interface IMixinGuiNewChat {

	void setTheChatLine(LiteralText chatComponent, int chatLineId, int updateCounter, boolean displayOnly);

	List<HudLine> getLines();

	void removeMessage(HudLine line);

	void removeLine(int index);

}
