package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CactusBlock.class)
public class MixinBlockCactus {

    @Shadow
    @Final
    protected static VoxelShape COLLISION_SHAPE;

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public VoxelShape getCollisionShape(BlockState blockState_1, BlockView blockView_1, BlockPos blockPos_1, EntityContext verticalEntityPosition_1) {
        if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "custom_cactus_voxel", false)) {
            return VoxelShapes.fullCube();
        }
        return COLLISION_SHAPE;
    }

}
