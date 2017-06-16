package me.deftware.client.framework.Wrappers.Objects;

import me.deftware.client.framework.Wrappers.Entity.IEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public class IDummieEntity extends IEntity {

	public IDummieEntity(IBlockPos pos) {
		super(new dummieEntity(pos));
	}

	private static class dummieEntity extends Entity {

		public dummieEntity(IBlockPos pos) {
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
