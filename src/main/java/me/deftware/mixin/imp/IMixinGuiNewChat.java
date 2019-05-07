package me.deftware.mixin.imp;


import net.minecraft.network.chat.TextComponent;

public interface IMixinGuiNewChat {

	void setTheChatLine(TextComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly);

}
