package me.deftware.mixin.mixins.game;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.event.events.EventSound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSystem.class)
public class MixinSoundSystem {

    @Shadow
    @Final
    private SoundManager loader;

    /*
    * inject after checks to make sure it's a real sound and is playable,
    * this also means we don't have to worry about getSoundSet being null.
    */
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundInstance;getVolume()F"), method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", cancellable = true)
    public void onPlay(SoundInstance instance, CallbackInfo info) {
        Text soundName = instance.getSoundSet(loader).getSubtitle();
        EventSound event = new EventSound(instance, soundName == null ? null : new ChatMessage().fromText(soundName));
        event.broadcast();
        if (event.isCanceled()) {
            info.cancel();
        }
    }

}
