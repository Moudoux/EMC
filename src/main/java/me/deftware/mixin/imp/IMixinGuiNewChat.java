package me.deftware.mixin.imp;

import net.minecraft.util.text.ITextComponent;

public interface IMixinGuiNewChat {

	void setTheChatLine(ITextComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly);

}
