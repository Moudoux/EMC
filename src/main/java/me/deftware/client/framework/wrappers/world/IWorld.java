package me.deftware.client.framework.wrappers.world;

import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.client.framework.wrappers.entity.ITileEntity;
import me.deftware.client.framework.wrappers.math.IAxisAlignedBB;
import me.deftware.client.framework.wrappers.world.blocks.IBlockCrops;
import me.deftware.client.framework.wrappers.world.blocks.IBlockNetherWart;
import me.deftware.mixin.imp.IMixinWorldClient;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import java.util.ArrayList;

public class IWorld {

    private final World world;

    public IWorld(World world) {
        this.world = world;
    }

    public static ArrayList<IEntity> getLoadedEntities() {
        ArrayList<IEntity> entities = new ArrayList<>();
        if (Bootstrap.CRASHED || MinecraftClient.getInstance().world == null || ((IMixinWorldClient) MinecraftClient.getInstance().world).getLoadedEntities() == null) {
            return entities;
        }
        for (Entity entity : ((IMixinWorldClient) MinecraftClient.getInstance().world).getLoadedEntities().values()) {
            entities.add(new IEntity(entity));
        }
        return entities;
    }

    public static ArrayList<ITileEntity> getLoadedTileEntities() {
        ArrayList<ITileEntity> entities = new ArrayList<>();
        if (MinecraftClient.getInstance().world == null) {
            return entities;
        }
        for (BlockEntity entity : MinecraftClient.getInstance().world.blockEntities) {
            entities.add(new ITileEntity(entity));
        }
        return entities;
    }

    public static long getWorldTime() {
        if (MinecraftClient.getInstance().world == null) {
            return 0L;
        }
        return MinecraftClient.getInstance().world.getTime();
    }

    public static void sendQuittingPacket() {
        if (MinecraftClient.getInstance().world != null) {
            MinecraftClient.getInstance().world.disconnect();
        }
    }

    public static void leaveWorld() {
        MinecraftClient.getInstance().joinWorld(null);
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

    public static boolean isNull() {
        return MinecraftClient.getInstance().world == null;
    }

    public int getActualHeight() {
        return world.getEffectiveHeight();
    }

    public IChunk getChunk(IBlockPos pos) {
        return new IChunk(world.getChunk(pos.getPos()));
    }

    public World getWorld() {
        return world;
    }

    public boolean containsAnyLiquid(IAxisAlignedBB aabb) {
        return world.containsBlockWithMaterial(aabb.getAABB(), Material.WATER) ||
                world.containsBlockWithMaterial(aabb.getAABB(), Material.LAVA);
    }


}
