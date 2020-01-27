package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.entity.EntityContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CactusBlock.class)
public class MixinBlockCactus {

    @Inject(method = "getCollisionShape", at = @At(value = "TAIL"), cancellable = true)
    private void onGetCollisionShape(BlockState state, BlockView view, BlockPos pos, EntityContext ePos, CallbackInfoReturnable<VoxelShape> cir) {
        if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "custom_cactus_voxel", false)) {
            cir.setReturnValue(VoxelShapes.fullCube());
        }
    }
}
