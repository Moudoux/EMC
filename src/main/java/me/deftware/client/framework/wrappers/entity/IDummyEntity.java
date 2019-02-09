package me.deftware.client.framework.wrappers.entity;

import me.deftware.client.framework.wrappers.world.IBlockPos;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;

public class IDummyEntity extends IEntity {

    public IDummyEntity(IBlockPos pos) {
        super(new dummyEntity(pos));
    }

    public static class dummyEntity extends Entity {

        public dummyEntity(IBlockPos pos) {
            super(net.minecraft.entity.EntityType.PLAYER, MinecraftClient.getInstance().player.getEntityWorld());
            x = pos.getX();
            y = pos.getY();
            z = pos.getZ();
        }

        @Override
        protected void initDataTracker() {

        }

        @Override
        protected void readCustomDataFromTag(CompoundTag compoundTag) {

        }

        @Override
        protected void writeCustomDataToTag(CompoundTag compoundTag) {

        }

        @Override
        public Packet<?> createSpawnPacket() {
            return null;
        }
    }

}
