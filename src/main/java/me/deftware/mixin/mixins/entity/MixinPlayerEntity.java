package me.deftware.mixin.mixins.entity;

import me.deftware.client.framework.event.events.EventSneakingCheck;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
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

    @Redirect(method = "adjustMovementForSneaking", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;stepHeight:F", opcode = 180))
    private float modifyStepHeight(PlayerEntity self, Vec3d origin) {
        return self == MinecraftClient.getInstance().player ? 0.6f : self.stepHeight;
    }

    @Redirect(method = "method_30263", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;stepHeight:F", opcode = 180))
    private float modifyStepHeight(PlayerEntity self) {
        return self == MinecraftClient.getInstance().player ? 0.6f : self.stepHeight;
    }

}
