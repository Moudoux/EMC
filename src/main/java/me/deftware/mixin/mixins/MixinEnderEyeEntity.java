package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventStructureLocation;
import me.deftware.client.framework.wrappers.world.IBlockPos;
import net.minecraft.entity.EnderEyeEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderEyeEntity.class)
public class MixinEnderEyeEntity {
    @Inject(method = "moveTowards", at = @At("HEAD"))
    public void moveTowards(BlockPos pos, CallbackInfo ci) {
        EventStructureLocation event = new EventStructureLocation(new IBlockPos(pos), EventStructureLocation.StructureType.Stronghold);
        event.broadcast();
    }
}
