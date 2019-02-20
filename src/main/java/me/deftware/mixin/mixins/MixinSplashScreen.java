package me.deftware.mixin.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.wrappers.IResourceLocation;
import me.deftware.mixin.components.CustomClass;
import net.minecraft.class_4071;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.SplashScreen;
import net.minecraft.client.resource.metadata.TextureResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloadHandler;
import net.minecraft.resource.ResourceType;
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

import java.io.IOException;
import java.io.InputStream;

@Mixin(SplashScreen.class)
public abstract class MixinSplashScreen {

    @Final
    @Shadow
    public static Identifier LOGO = new Identifier("textures/gui/title/mojang.png");
    @Final
    @Shadow
    public MinecraftClient field_18217;
    @Final
    @Shadow
    public ResourceReloadHandler field_17767;
    @Shadow
    @Final
    public Runnable field_18218;
    @Shadow
    @Final
    public boolean field_18219;
    @Shadow
    public float field_17770;
    @Shadow
    public long field_17771 = -1L;
    @Shadow
    public long field_18220 = -1L;
    private Identifier customLogo = null;

    @Shadow
    public abstract void renderProgressBar(int int_1, int int_2, int int_3, int int_4, float float_1, float float_2);

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        if (SettingsMap.hasValue(SettingsMap.MapKeys.GAME_SETTINGS, "CUSTOM_LOGO_TEXTURE")) {
            customLogo = new IResourceLocation((String) SettingsMap.getValue(SettingsMap.MapKeys.GAME_SETTINGS, "CUSTOM_LOGO_TEXTURE", ""));
        }
    }

    @Overwrite
    public void method_18326(int int_1, int int_2, float float_1) {
        this.field_18217.getTextureManager().registerTexture(customLogo == null ? LOGO : customLogo, new CustomClass(customLogo == null ? LOGO : customLogo));
        int int_3 = this.field_18217.window.getScaledWidth();
        int int_4 = this.field_18217.window.getScaledHeight();
        long long_1 = SystemUtil.getMeasuringTimeMs();
        if (this.field_18219 && (this.field_17767.method_18786() || this.field_18217.currentScreen != null) && this.field_18220 == -1L) {
            this.field_18220 = long_1;
        }

        float float_2 = this.field_17771 > -1L ? (float) (long_1 - this.field_17771) / 1000.0F : -1.0F;
        float float_3 = this.field_18220 > -1L ? (float) (long_1 - this.field_18220) / 500.0F : -1.0F;
        float float_6;
        int int_6;
        if (float_2 >= 1.0F) {
            if (this.field_18217.currentScreen != null) {
                this.field_18217.currentScreen.method_18326(0, 0, float_1);
            }

            int_6 = MathHelper.ceil((1.0F - MathHelper.clamp(float_2 - 1.0F, 0.0F, 1.0F)) * 255.0F);
            Drawable.drawRect(0, 0, int_3, int_4, 16777215 | int_6 << 24);
            float_6 = 1.0F - MathHelper.clamp(float_2 - 1.0F, 0.0F, 1.0F);
        } else if (this.field_18219) {
            if (this.field_18217.currentScreen != null && float_3 < 1.0F) {
                this.field_18217.currentScreen.method_18326(int_1, int_2, float_1);
            }

            int_6 = MathHelper.ceil(MathHelper.clamp((double) float_3, 0.15D, 1.0D) * 255.0D);
            Drawable.drawRect(0, 0, int_3, int_4, 16777215 | int_6 << 24);
            float_6 = MathHelper.clamp(float_3, 0.0F, 1.0F);
        } else {
            Drawable.drawRect(0, 0, int_3, int_4, -1);
            float_6 = 1.0F;
        }

        int_6 = (this.field_18217.window.getScaledWidth() - 256) / 2;
        int int_8 = (this.field_18217.window.getScaledHeight() - 256) / 2;
        this.field_18217.getTextureManager().bindTexture(customLogo == null ? LOGO : customLogo);
        GlStateManager.enableBlend();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, float_6);
        ((SplashScreen) (Object) this).drawTexturedRect(int_6, int_8, 0, 0, 256, 256);
        float float_7 = this.field_17767.getProgress();
        this.field_17770 = this.field_17770 * 0.95F + float_7 * 0.050000012F;
        if (float_2 < 1.0F) {
            this.renderProgressBar(int_3 / 2 - 150, int_4 / 4 * 3, int_3 / 2 + 150, int_4 / 4 * 3 + 10, this.field_17770, 1.0F - MathHelper.clamp(float_2, 0.0F, 1.0F));
        }

        if (float_2 >= 2.0F) {
            this.field_18217.method_18502((class_4071) null);
        }

        if (this.field_17771 == -1L && this.field_17767.method_18787() && (!this.field_18219 || float_3 >= 2.0F)) {
            this.field_17771 = SystemUtil.getMeasuringTimeMs();
            done();
            if (this.field_18217.currentScreen != null) {
                this.field_18217.currentScreen.initialize(this.field_18217, this.field_18217.window.getScaledWidth(), this.field_18217.window.getScaledHeight());
            }
        }

    }

    public void done() {
        this.field_18218.run();
        Bootstrap.initList.forEach(mod -> mod.accept("arg"));
    }

}
