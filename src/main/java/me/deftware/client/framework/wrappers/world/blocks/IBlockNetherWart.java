package me.deftware.client.framework.wrappers.world.blocks;

import me.deftware.client.framework.wrappers.world.IBlock;
import me.deftware.client.framework.wrappers.world.IBlockPos;
import me.deftware.client.framework.wrappers.world.IWorld;
import net.minecraft.block.Block;
import net.minecraft.block.NetherWartBlock;
import net.minecraft.util.math.BlockPos;

public class IBlockNetherWart extends IBlock {

    public IBlockNetherWart(String name) {
        super(name);
    }

    public IBlockNetherWart(Block block) {
        super(block);
    }

    public IBlockNetherWart(Block mBlock, BlockPos pos) {
        super(mBlock, pos);
    }

    public int getAge(IBlockPos pos) {
        return IWorld.getStateFromPos(pos).get(NetherWartBlock.AGE);
    }

}
