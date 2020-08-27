package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSneakingCheck;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {

    @Redirect(method = "adjustMovementForSneaking", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;clipAtLedge()Z", opcode = 180))
    private boolean sneakingCheck(PlayerEntity self) {
        if (self == MinecraftClient.getInstance().player) {
            EventSneakingCheck event = new EventSneakingCheck(self.isSneaking());
            event.broadcast();
            return event.isSneaking();
        }
        return self.isSneaking();
    }

}
