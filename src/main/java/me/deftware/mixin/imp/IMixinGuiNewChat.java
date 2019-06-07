package me.deftware.mixin.imp;


import net.minecraft.text.LiteralText;

public interface IMixinGuiNewChat {

	void setTheChatLine(LiteralText chatComponent, int chatLineId, int updateCounter, boolean displayOnly);

}
