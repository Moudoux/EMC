package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSaddleCheck;
import me.deftware.mixin.imp.IMixinEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.PigEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PigEntity.class)
public abstract class MixinAbstractPig {

    @Shadow
    @Final
    private static TrackedData<Boolean> SADDLED;

    /**
     * @author Deftware
     * @reason
     */
    @Overwrite
    public boolean isSaddled() {
        EventSaddleCheck event = new EventSaddleCheck(((IMixinEntity) this).getTracker().get(SADDLED));
        event.broadcast();
        return event.isState();
    }
}
