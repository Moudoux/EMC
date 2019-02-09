package me.deftware.mixin.imp;


import net.minecraft.text.TextComponent;

public interface IMixinGuiNewChat {

	void setTheChatLine(TextComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly);

}
