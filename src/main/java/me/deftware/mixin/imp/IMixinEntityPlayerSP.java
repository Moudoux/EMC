package me.deftware.mixin.imp;

public interface IMixinEntityPlayerSP {

	void setHorseJumpPower(float height);

	void sendChatMessageWithSender(String message, Class<?> sender);

}
