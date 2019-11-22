package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSneakingCheck;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {

    @Redirect(method = "adjustMovementForSneaking", at = @At(value = "INVOKE", target = "net/minecraft/entity/player/PlayerEntity.method_21825()Z", opcode = 180))
    private boolean sneakingCheck(PlayerEntity self) {
        EventSneakingCheck event = new EventSneakingCheck(((PlayerEntity) (Object) this).isSneaking());
        event.broadcast();
        return event.isSneaking();
    }

}
