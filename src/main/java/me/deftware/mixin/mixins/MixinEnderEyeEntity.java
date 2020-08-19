package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventStructureLocation;
import me.deftware.client.framework.math.position.DoubleBlockPosition;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EyeOfEnderEntity.class)
public class MixinEnderEyeEntity {

    @Inject(method = "initTargetPos", at = @At("HEAD"))
    public void moveTowards(BlockPos pos, CallbackInfo ci) {
        EventStructureLocation event = new EventStructureLocation(DoubleBlockPosition.fromMinecraftBlockPos(pos), EventStructureLocation.StructureType.Stronghold);
        event.broadcast();
    }

}
