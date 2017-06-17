package me.deftware.client.framework.Wrappers.Objects;

import me.deftware.client.framework.Wrappers.Entity.IEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public class IDummyEntity extends IEntity {

	public IDummyEntity(IBlockPos pos) {
		super(new dummyEntity(pos));
	}

	public static class dummyEntity extends Entity {

		public dummyEntity(IBlockPos pos) {
			super(Minecraft.getMinecraft().player.getEntityWorld());
			this.posX = pos.getX();
			this.posY = pos.getY();
			this.posZ = pos.getZ();
		}

		@Override
		protected void entityInit() {

		}

		@Override
		protected void readEntityFromNBT(NBTTagCompound compound) {

		}

		@Override
		protected void writeEntityToNBT(NBTTagCompound compound) {

		}

	}

}
