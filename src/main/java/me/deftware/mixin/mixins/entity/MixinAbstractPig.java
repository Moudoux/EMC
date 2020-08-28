package me.deftware.mixin.mixins.entity;

import me.deftware.client.framework.event.events.EventSaddleCheck;
import net.minecraft.entity.passive.PigEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigEntity.class)
public abstract class MixinAbstractPig {

    @Inject(method = "isSaddled", at = @At(value = "TAIL"), cancellable = true)
    private void onIsSaddled(CallbackInfoReturnable<Boolean> cir) {
        EventSaddleCheck event = new EventSaddleCheck(cir.getReturnValue());
        event.broadcast();
        cir.setReturnValue(event.isState());
    }

    @Inject(method = "canBeControlledByRider", at = @At("TAIL"), cancellable = true)
    public void canBeControlled(CallbackInfoReturnable<Boolean> cir) {
        EventSaddleCheck event = new EventSaddleCheck(cir.getReturnValue());
        event.broadcast();
        cir.setReturnValue(event.isState());
    }
}
