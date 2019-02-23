package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSetModelVisibilities;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static org.spongepowered.asm.lib.Opcodes.GETFIELD;

@Mixin(PlayerEntityRenderer.class)
public class MixinRenderPlayer {

    @Redirect(method = "setModelPose", at = @At(value = "INVOKE", target = "net/minecraft/client/network/AbstractClientPlayerEntity.isSpectator()Z", opcode = GETFIELD))
    private boolean setModelVisibilities_isSpectator(AbstractClientPlayerEntity self) {
        EventSetModelVisibilities event = new EventSetModelVisibilities(self.isSpectator());
        event.broadcast();
        return event.isSpectator();
    }

}
