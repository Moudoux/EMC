package me.deftware.mixin.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import me.deftware.client.framework.event.events.EventDisconnected;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.wrappers.IResourceLocation;
import net.minecraft.class_766;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.SplashScreen;
import net.minecraft.resource.ResourceReloadHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.SystemUtil;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(SplashScreen.class)
public abstract class MixinSplashScreen {

    @Shadow
    @Final
    private static Identifier LOGO = new Identifier("textures/gui/title/mojang.png");

    private Identifier customLogo = null;

    @Shadow
    @Final
    private ResourceReloadHandler field_17767;

    @Shadow
    @Final
    private Consumer<SplashScreen> splashScreenConsumer;

    @Shadow
    private class_766 field_17769;

    @Shadow
    private float field_17770;

    @Shadow
    private long field_17771;

    @Shadow
    public abstract void renderProgressBar(int int_1, int int_2, int int_3, int int_4, float float_1, float float_2);

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        if (SettingsMap.hasValue(SettingsMap.MapKeys.GAME_SETTINGS, "CUSTOM_LOGO_TEXTURE")) {
            customLogo = new IResourceLocation((String) SettingsMap.getValue(SettingsMap.MapKeys.GAME_SETTINGS, "CUSTOM_LOGO_TEXTURE", ""));
        }
    }

    @Overwrite
    public void draw(int int_1, int int_2, float float_1) {
        Drawable.drawRect(0, 0, ((Screen) (Object) this).width, ((Screen) (Object) this).height, -1);
        long long_1 = SystemUtil.getMeasuringTimeMs();
        float float_2 = this.field_17771 > -1L ? (float) (long_1 - this.field_17771) / 1000.0F : 0.0F;
        if (float_2 >= 1.0F) {
            this.field_17769.method_3317(float_1, MathHelper.clamp(float_2 - 1.0F, 0.0F, 1.0F));
        }

        int int_3 = (MinecraftClient.getInstance().window.getScaledWidth() - 256) / 2;
        int int_4 = (MinecraftClient.getInstance().window.getScaledHeight() - 256) / 2;
        MinecraftClient.getInstance().getTextureManager().bindTexture(customLogo == null ? LOGO : customLogo);
        GlStateManager.enableBlend();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F - MathHelper.clamp(float_2 - 1.0F, 0.0F, 1.0F));
        ((Screen) (Object) this).drawTexturedRect(int_3, int_4, 0, 0, 256, 256);
        if (float_2 >= 1.0F) {
            if (!SettingsMap.hasValue(SettingsMap.MapKeys.GAME_SETTINGS, "CUSTOM_SPLASHSCREEN_TEXTURE")) {
                MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("textures/gui/title/background/panorama_overlay.png"));
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, (float) MathHelper.ceil(MathHelper.clamp(float_2 - 1.0F, 0.0F, 1.0F)));
                ((Screen) (Object) this).drawTexturedRect(0, 0, 0.0F, 0.0F, 16, 128, ((Screen) (Object) this).width, ((Screen) (Object) this).height, 16.0F, 128.0F);
            } else {
                MinecraftClient.getInstance().getTextureManager().bindTexture(new IResourceLocation((String) SettingsMap.getValue(SettingsMap.MapKeys.GAME_SETTINGS, "CUSTOM_SPLASHSCREEN_TEXTURE", "")));
                GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                Screen.drawTexturedRect(0, 0, 0, 0, ((Screen) (Object) this).width, ((Screen) (Object) this).height, ((Screen) (Object) this).width, ((Screen) (Object) this).height);
            }
        }

        float float_3 = this.field_17767.getProgress();
        this.field_17770 = this.field_17770 * 0.95F + float_3 * 0.050000012F;
        if (float_2 < 1.0F) {
            this.renderProgressBar(((Screen) (Object) this).width / 2 - 150, ((Screen) (Object) this).height / 4 * 3, ((Screen) (Object) this).width / 2 + 150, ((Screen) (Object) this).height / 4 * 3 + 10, this.field_17770, 1.0F - MathHelper.clamp(float_2, 0.0F, 1.0F));
        }

        if (float_2 >= 1.0F && !SettingsMap.getValue(SettingsMap.MapKeys.GAME_SETTINGS, "CUSTOM_SPLASHSCREEN_TEXTURE", "").equals("")) {
            done();
        } else if (float_2 >= 2.0F) {
            done();
        }

        if (this.field_17771 == -1L && this.field_17767.whenComplete().isDone()) {
            this.field_17771 = SystemUtil.getMeasuringTimeMs();
        }
    }

    public void done() {
        this.splashScreenConsumer.accept((SplashScreen) (Object) this);
        Bootstrap.initList.forEach(mod -> mod.accept("arg"));
    }

}
