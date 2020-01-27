package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSaddleCheck;
import net.minecraft.entity.passive.PigEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigEntity.class)
public abstract class MixinAbstractPig {


    @Inject(method = "isSaddled", at = @At("RETURN"), cancellable = true)
    public void isSaddled(CallbackInfoReturnable<Boolean> cir) {
        EventSaddleCheck event = new EventSaddleCheck(cir.getReturnValue());
        event.broadcast();
        cir.setReturnValue(event.isState());
    }
}
