package me.deftware.mixin.mixins;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.mixin.imp.IMixinWorldClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ClientWorld.class)
public class MixinWorldClient implements IMixinWorldClient {

    @Shadow
    @Final
    private Int2ObjectMap<Entity> regularEntities;

    @ModifyVariable(method = "randomBlockDisplayTick(IIIILjava/util/Random;ZLnet/minecraft/util/math/BlockPos$Mutable;)V", at = @At("HEAD"))
    public boolean randomBlockDisplayTick(boolean p_animateTick_6_) {
        if ((boolean) SettingsMap.getValue(SettingsMap.MapKeys.BLOCKS, "render_barrier_blocks", false)) {
            return true;
        }
        return p_animateTick_6_;
    }

    @Override
    public Int2ObjectMap<Entity> getLoadedEntities() {
        return regularEntities;
    }

}
