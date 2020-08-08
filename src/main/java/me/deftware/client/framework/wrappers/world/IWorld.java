package me.deftware.client.framework.wrappers.world;

import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.client.framework.wrappers.entity.IEntityPlayer;
import me.deftware.client.framework.wrappers.entity.ITileEntity;
import me.deftware.client.framework.wrappers.math.IAxisAlignedBB;
import me.deftware.client.framework.wrappers.world.blocks.IBlockCrops;
import me.deftware.client.framework.wrappers.world.blocks.IBlockNetherWart;
import me.deftware.mixin.imp.IMixinWorld;
import me.deftware.mixin.imp.IMixinWorldClient;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.function.MaterialPredicate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class IWorld {

    private final World world;

    public IWorld(World world) {
        this.world = world;
    }

    @SuppressWarnings("ConstantConditions")
    public static HashMap<Integer, IEntity> getLoadedEntities() {
        return ((IMixinWorldClient) MinecraftClient.getInstance().world).getIEntities();
    }

    @SuppressWarnings("ConstantConditions")
    public static Collection<ITileEntity> getLoadedTileEntities() {
        return ((IMixinWorld) MinecraftClient.getInstance().world).getEmcTileEntities();
    }

    public static ArrayList<IBlock> getLoadedBlocks() {
        ArrayList<IBlock> blocks = new ArrayList<>();

        if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().world != null) {
            WorldChunk currentChunk = MinecraftClient.getInstance().world.getWorldChunk(MinecraftClient.getInstance().player.getBlockPos());
            for (BlockPos pos : currentChunk.getBlockEntityPositions()) {
                blocks.add(new IBlock(currentChunk.getBlockState(pos).getBlock(), pos));
            }
        }

        return blocks;
    }

    public static ArrayList<IBlock> getLoadedBlocks(int rangeFromPlayer) {
        ArrayList<IBlock> blocks = new ArrayList<>();

        if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().world != null) {
            for (int xRange = -rangeFromPlayer; xRange <= rangeFromPlayer; xRange++) {
                for (int yRange = -rangeFromPlayer; yRange <= rangeFromPlayer; yRange++) {
                    for (int zRange = -rangeFromPlayer; zRange <= rangeFromPlayer; zRange++) {
                        int posX = (int) (IEntityPlayer.getPosX() + xRange);
                        int posY = (int) MathHelper.clamp(IEntityPlayer.getPosY() + yRange, 0, 256);
                        int posZ = (int) (IEntityPlayer.getPosZ() + zRange);

                        IBlockPos pos = new IBlockPos(posX, posY, posZ);
                        IBlock block = IWorld.getBlockFromPos(pos);

                        blocks.add(block);
                    }
                }
            }
        }

        return blocks;
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
        IBlock block = new IBlock(mBlock, pos.getPos());
        if (block.instanceOf(IBlock.IBlockTypes.BlockCrops)) {
            block = new IBlockCrops(mBlock, pos.getPos());
        } else if (block.instanceOf(IBlock.IBlockTypes.BlockNetherWart)) {
            block = new IBlockNetherWart(mBlock, pos.getPos());
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
        return world.getHeight();
    }

    public IChunk getChunk(IBlockPos pos) {
        return new IChunk(world.getChunk(pos.getPos()));
    }

    public World getWorld() {
        return world;
    }

    public boolean containsAnyLiquid(IAxisAlignedBB aabb) {
        MaterialPredicate waterPredicate = MaterialPredicate.create(Material.WATER);
        MaterialPredicate lavaPredicate = MaterialPredicate.create(Material.LAVA);

        return world.method_29546(aabb.getAABB()).anyMatch((blockState) -> waterPredicate.test(blockState) || lavaPredicate.test(blockState));
    }


}
