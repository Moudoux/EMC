package me.deftware.mixin.mixins.entity;

import me.deftware.client.framework.event.events.EventBlockBreakingSpeed;
import me.deftware.client.framework.event.events.EventSneakingCheck;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {

    @Shadow @Final private PlayerAbilities abilities;

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

    @Inject(method = "getBlockBreakingSpeed", at = @At(value = "RETURN"), cancellable = true)
    public void onGetBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir) {
        EventBlockBreakingSpeed event = new EventBlockBreakingSpeed().broadcast();
        cir.setReturnValue(cir.getReturnValue() * event.getMultiplier());
    }

}
