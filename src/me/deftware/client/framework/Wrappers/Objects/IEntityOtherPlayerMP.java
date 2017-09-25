package me.deftware.client.framework.Wrappers.Objects;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.world.World;

public class IEntityOtherPlayerMP extends EntityOtherPlayerMP {

	
	public IEntityOtherPlayerMP() {
		super(Minecraft.getMinecraft().world, Minecraft.getMinecraft().player.getGameProfile());
		clonePlayer(Minecraft.getMinecraft().player, true);
		copyLocationAndAnglesFrom(Minecraft.getMinecraft().player);
		rotationYawHead = Minecraft.getMinecraft().player.rotationYawHead;
	}

	public IEntityOtherPlayerMP(World worldIn, GameProfile gameProfileIn) {
		super(worldIn, gameProfileIn);
	}

}
