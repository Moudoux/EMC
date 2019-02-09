package me.deftware.client.framework.wrappers.world;

import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.client.framework.wrappers.entity.ITileEntity;
import me.deftware.client.framework.wrappers.world.blocks.IBlockCrops;
import me.deftware.client.framework.wrappers.world.blocks.IBlockNetherWart;
import me.deftware.mixin.imp.IMixinWorldClient;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

import java.util.ArrayList;

public class IWorld {

    public static ArrayList<IEntity> getLoadedEntities() {
        ArrayList<IEntity> entities = new ArrayList<>();
        for (Entity entity : ((IMixinWorldClient) MinecraftClient.getInstance().world).getLoadedEntities().values()) {
            entities.add(new IEntity(entity));
        }
        return entities;
    }

    public static ArrayList<ITileEntity> getLoadedTileEntities() {
        ArrayList<ITileEntity> entities = new ArrayList<>();
        for (BlockEntity entity : MinecraftClient.getInstance().world.blockEntities) {
            entities.add(new ITileEntity(entity));
        }
        return entities;
    }

    public static void sendQuittingPacket() {
        if (MinecraftClient.getInstance().world != null) {
            MinecraftClient.getInstance().world.method_8525();
        }
    }

    public static void leaveWorld() {
        MinecraftClient.getInstance().method_1481(null);
    }

    public static IBlock getBlockFromPos(IBlockPos pos) {
        Block mBlock = MinecraftClient.getInstance().world.getBlockState(pos.getPos()).getBlock();
        IBlock block = new IBlock(mBlock);
        if (block.instanceOf(IBlock.IBlockTypes.BlockCrops)) {
            block = new IBlockCrops(mBlock);
        } else if (block.instanceOf(IBlock.IBlockTypes.BlockNetherWart)) {
            block = new IBlockNetherWart(mBlock);
        }
        return block;
    }

    public static BlockState getStateFromPos(IBlockPos pos) {
        return MinecraftClient.getInstance().world.getBlockState(pos.getPos());
    }

}
