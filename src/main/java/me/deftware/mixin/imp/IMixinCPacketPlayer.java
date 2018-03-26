package me.deftware.mixin.imp;

public interface IMixinCPacketPlayer {

	boolean isOnGround();

	boolean isMoving();

	void setOnGround(boolean state);

	void setMoving(boolean state);

}
