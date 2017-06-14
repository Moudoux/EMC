package me.deftware.client.framework.Wrappers.Entity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class IPlayer {

	private EntityPlayer player;
	
	public IPlayer() { }
	
	public IPlayer(EntityPlayer player) {
		this.player = player;
	}

	public EntityPlayer getPlayer() {
		return player;
	}
	
	public float getHealth() {
		return player.getHealth();
	}
	
	public String getName() {
		return player.getName();
	}
	
	public String getFormattedDisplayName() {
		return player.getDisplayName().getFormattedText();
	}
	
	public float getNametagSize() {
		return Minecraft.getMinecraft().player.getDistanceToEntity(player) / 2.5F <= 1.5F ? 2.0F
				: Minecraft.getMinecraft().player.getDistanceToEntity(player) / 2.5F;
	}

	public boolean isSelf() {
		if (player == Minecraft.getMinecraft().player
				|| player.getName().equals(Minecraft.getMinecraft().session.getUsername())) {
			return true;
		}
		return false;
	}
	
	public double getPosX() {
		return player.posX;
	}
	
	public double getPosY() {
		return player.posY;
	}
	
	public double getPosZ() {
		return player.posZ;
	}
	
	public double getLastTickPosX() {
		return player.lastTickPosX;
	}
	
	public double getLastTickPosY() {
		return player.lastTickPosY;
	}
	
	public double getLastTickPosZ() {
		return player.lastTickPosZ;
	}
	
	public boolean isCreative() {
		return player.isCreative();
	}
	
	public float getHeight() {
		return player.height;
	}

}

