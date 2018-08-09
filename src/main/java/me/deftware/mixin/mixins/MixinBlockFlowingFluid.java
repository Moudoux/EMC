package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.block.BlockFlowingFluid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ShapeUtils;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockFlowingFluid.class)
public class MixinBlockFlowingFluid {

    public VoxelShape getShapeForCollision(IBlockState p_getShapeForCollision_1_, IBlockReader p_getShapeForCollision_2_, BlockPos p_getShapeForCollision_3_) {
        return (boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "liquid_aabb_solid", false)
                ? ShapeUtils.fullCube()
                : ShapeUtils.emptyShape();
    }

}
