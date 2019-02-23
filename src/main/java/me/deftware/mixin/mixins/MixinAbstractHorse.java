package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventSaddleCheck;
import net.minecraft.entity.passive.HorseBaseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(HorseBaseEntity.class)
public abstract class MixinAbstractHorse {

    @Shadow
    protected abstract boolean getHorseFlag(int int_19);

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public boolean isSaddled() {
        EventSaddleCheck event = new EventSaddleCheck(this.getHorseFlag(4));
        event.broadcast();
        return event.isState();
    }

}
