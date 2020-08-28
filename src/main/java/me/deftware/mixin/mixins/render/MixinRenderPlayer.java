package me.deftware.mixin.mixins.render;

import me.deftware.client.framework.event.events.EventSetModelVisibilities;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntityRenderer.class)
public class MixinRenderPlayer {

    @Redirect(method = "setModelPose", at = @At(value = "INVOKE", target = "net/minecraft/client/network/AbstractClientPlayerEntity.isSpectator()Z", opcode = 180))
    private boolean setModelVisibilities_isSpectator(AbstractClientPlayerEntity self) {
        EventSetModelVisibilities event = new EventSetModelVisibilities(self.isSpectator());
        event.broadcast();
        return event.isSpectator();
    }

}
