package me.deftware.client.framework.wrappers.entity;


import me.deftware.client.framework.wrappers.item.IItemStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class IPlayer {

	private EntityPlayer player;

	public IPlayer() {
	}

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
		return player.getGameProfile().getName();
	}

	public String getFormattedDisplayName() {
		return player.getDisplayName().getFormattedText();
	}

	public float getNametagSize() {
		return Minecraft.getInstance().player.getDistance(player) / 2.5F <= 1.5F ? 2.0F
				: Minecraft.getInstance().player.getDistance(player) / 2.5F;
	}

	public boolean isSelf() {
		if (player == Minecraft.getInstance().player
				|| player.getName().equals(Minecraft.getInstance().getSession().getUsername())) {
			return true;
		}
		return false;
	}

	public IItemStack getHeldItem() {
		if (player.inventory.getCurrentItem() != null) {
			return new IItemStack(player.inventory.getCurrentItem());
		}
		return null;
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

	public void setGlowing(boolean state) {
		player.setGlowing(state);
	}

}