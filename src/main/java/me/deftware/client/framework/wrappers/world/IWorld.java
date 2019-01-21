package me.deftware.client.framework.wrappers.world;

import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.client.framework.wrappers.entity.ITileEntity;
import me.deftware.client.framework.wrappers.world.blocks.IBlockCrops;
import me.deftware.client.framework.wrappers.world.blocks.IBlockNetherWart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

public class IWorld {

    public static ArrayList<IEntity> getLoadedEntities() {
        ArrayList<IEntity> entities = new ArrayList<>();
        for (Entity entity : Minecraft.getInstance().world.loadedEntityList) {
            entities.add(new IEntity(entity));
        }
        return entities;
    }

    public static ArrayList<ITileEntity> getLoadedTileEntities() {
        ArrayList<ITileEntity> entities = new ArrayList<>();
        for (TileEntity entity : Minecraft.getInstance().world.loadedTileEntityList) {
            entities.add(new ITileEntity(entity));
        }
        return entities;
    }

    public static void sendQuittingPacket() {
        if (Minecraft.getInstance().world != null) {
            Minecraft.getInstance().world.sendQuittingDisconnectingPacket();
        }
    }

    public static void leaveWorld() {
        Minecraft.getInstance().loadWorld(null);
    }

    public static IBlock getBlockFromPos(IBlockPos pos) {
        Block mBlock = Minecraft.getInstance().world.getBlockState(pos.getPos()).getBlock();
        IBlock block = new IBlock(mBlock);
        if (block.instanceOf(IBlock.IBlockTypes.BlockCrops)) {
            block = new IBlockCrops(mBlock);
        } else if (block.instanceOf(IBlock.IBlockTypes.BlockNetherWart)) {
            block = new IBlockNetherWart(mBlock);
        }
        return block;
    }

    public static IBlockState getStateFromPos(IBlockPos pos) {
        return Minecraft.getInstance().world.getBlockState(pos.getPos());
    }

}
