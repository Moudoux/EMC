package me.deftware.client.framework.wrappers.world;

import me.deftware.client.framework.utils.ChatProcessor;
import me.deftware.client.framework.wrappers.math.IVoxelShape;
import net.minecraft.block.*;
import net.minecraft.fluid.WaterFluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class IBlock {

    protected Block block;

    protected IBlockPos pos;

    public IBlock(String name) {
        block = getBlockFromName(name);
    }

    public IBlock(Block block) {
        this.block = block;
    }

    public IBlock(String name, BlockPos pos) {
        block = getBlockFromName(name);
        this.pos = new IBlockPos(pos);
    }

    public IBlock(Block block, BlockPos pos) {
        this.block = block;
        this.pos = new IBlockPos(pos);
    }

    public IBlockPos getBlockPos() {
        return pos;
    }

    public static boolean isValidBlock(String name) {
        return getBlockFromName(name) != null;
    }

    public static boolean isReplaceable(IBlockPos pos) {
        return IWorld.getStateFromPos(pos).getMaterial().isReplaceable();
    }

    private static Block getBlockFromName(String p_getBlockFromName_0_) {
        Identifier lvt_1_1_ = new Identifier(p_getBlockFromName_0_);
        if (Registry.BLOCK.containsId(lvt_1_1_)) {
            return Registry.BLOCK.get(lvt_1_1_);
        }
        return null;
    }

    public static IVoxelShape makeCuboidShape(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new IVoxelShape(Block.createCuboidShape(x1, y1, z1, x2, y2, z2));
    }

    public boolean isValidBlock() {
        return block != null;
    }

    public boolean isAir() {
        return block == Blocks.AIR;
    }

    public boolean isLiquid() {
        return block == Blocks.WATER || block == Blocks.LAVA || block instanceof FluidBlock;
    }

    public boolean isCaveAir() {
        return block == Blocks.CAVE_AIR;
    }

    public Block getBlock() {
        return block;
    }

    public int getID() {
        return Registry.BLOCK.getRawId(block);
    }

    public String getLocalizedName() {
        return ChatProcessor.getStringFromText(block.getName());
    }

    public String getBlockKey() {
        return getTranslationKey().substring("block.minecraft.".length());
    }

    public String getTranslationKey() {
        return block.getTranslationKey();
    }

    public boolean instanceOf(IBlockTypes type) {
        if (type.equals(IBlockTypes.BlockContainer)) {
            return block instanceof BlockWithEntity;
        } else if (type.equals(IBlockTypes.BlockCrops)) {
            return block instanceof CropBlock;
        } else if (type.equals(IBlockTypes.BlockPumpkin)) {
            return block instanceof PumpkinBlock;
        } else if (type.equals(IBlockTypes.BlockMelon)) {
            return block instanceof MelonBlock;
        } else if (type.equals(IBlockTypes.BlockReed)) {
            return block instanceof SugarCaneBlock;
        } else if (type.equals(IBlockTypes.BlockCactus)) {
            return block instanceof CactusBlock;
        } else if (type.equals(IBlockTypes.BlockNetherWart)) {
            return block instanceof NetherWartBlock;
        } else if (type.equals(IBlockTypes.BlockFarmland)) {
            return block instanceof FarmlandBlock;
        } else if (type.equals(IBlockTypes.BlockSoulSand)) {
            return block instanceof SoulSandBlock;
        } else if (type.equals(IBlockTypes.FluidBlock)) {
            return block instanceof FluidBlock;
        }
        return false;
    }

    /*
     * Block specific functions
     */

    public enum IBlockTypes {
        // Types
        BlockContainer, BlockCrops, FluidBlock,

        // Specific blocks
        BlockPumpkin, BlockMelon, BlockReed, BlockCactus, BlockNetherWart, BlockFarmland, BlockSoulSand
    }

}
