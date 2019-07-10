package me.deftware.mixin.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import me.deftware.client.framework.main.Bootstrap;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.wrappers.IResourceLocation;
import me.deftware.mixin.components.CustomClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.client.gui.screen.SplashScreen;
import net.minecraft.resource.ResourceReloadMonitor;
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

@Mixin(SplashScreen.class)
public abstract class MixinSplashScreen {

    @Final
    @Shadow
    public static Identifier LOGO = new Identifier("textures/gui/title/mojang.png");

    @Final
    @Shadow
    public MinecraftClient client;

    @Final
    @Shadow
    public ResourceReloadMonitor reloadMonitor;

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

    @Overwrite
    public static void method_18819(MinecraftClient minecraftClient_1) {
        Identifier customLogo = null;
        if (SettingsMap.hasValue(SettingsMap.MapKeys.GAME_SETTINGS, "CUSTOM_LOGO_TEXTURE")) {
            customLogo = new IResourceLocation((String) SettingsMap.getValue(SettingsMap.MapKeys.GAME_SETTINGS, "CUSTOM_LOGO_TEXTURE", ""));
        }
        MinecraftClient.getInstance().getTextureManager().registerTexture(customLogo == null ? LOGO : customLogo, new CustomClass(customLogo == null ? LOGO : customLogo));
    }

    @Shadow
    public abstract void renderProgressBar(int int_1, int int_2, int int_3, int int_4, float float_1, float float_2);

    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onConstructed(CallbackInfo ci) {
        if (SettingsMap.hasValue(SettingsMap.MapKeys.GAME_SETTINGS, "CUSTOM_LOGO_TEXTURE")) {
            customLogo = new IResourceLocation((String) SettingsMap.getValue(SettingsMap.MapKeys.GAME_SETTINGS, "CUSTOM_LOGO_TEXTURE", ""));
        }
    }

    @Overwrite
    public void render(int int_1, int int_2, float float_1) {
        int int_3 = this.client.window.getScaledWidth();
        int int_4 = this.client.window.getScaledHeight();
        long long_1 = SystemUtil.getMeasuringTimeMs();
        if (this.field_18219 && (this.reloadMonitor.isLoadStageComplete() || this.client.currentScreen != null) && this.field_18220 == -1L) {
            this.field_18220 = long_1;
        }

        float float_2 = this.field_17771 > -1L ? (float)(long_1 - this.field_17771) / 1000.0F : -1.0F;
        float float_3 = this.field_18220 > -1L ? (float)(long_1 - this.field_18220) / 500.0F : -1.0F;
        float float_6;
        int int_6;
        if (float_2 >= 1.0F) {
            if (this.client.currentScreen != null) {
                this.client.currentScreen.render(0, 0, float_1);
            }

            int_6 = MathHelper.ceil((1.0F - MathHelper.clamp(float_2 - 1.0F, 0.0F, 1.0F)) * 255.0F);
            DrawableHelper.fill(0, 0, int_3, int_4, 16777215 | int_6 << 24);
            float_6 = 1.0F - MathHelper.clamp(float_2 - 1.0F, 0.0F, 1.0F);
        } else if (this.field_18219) {
            if (this.client.currentScreen != null && float_3 < 1.0F) {
                this.client.currentScreen.render(int_1, int_2, float_1);
            }

            int_6 = MathHelper.ceil(MathHelper.clamp((double)float_3, 0.15D, 1.0D) * 255.0D);
            DrawableHelper.fill(0, 0, int_3, int_4, 16777215 | int_6 << 24);
            float_6 = MathHelper.clamp(float_3, 0.0F, 1.0F);
        } else {
            DrawableHelper.fill(0, 0, int_3, int_4, -1);
            float_6 = 1.0F;
        }

        int_6 = (this.client.window.getScaledWidth() - 256) / 2;
        int int_8 = (this.client.window.getScaledHeight() - 256) / 2;
        this.client.getTextureManager().bindTexture(LOGO);
        GlStateManager.enableBlend();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, float_6);
        ((SplashScreen) (Object) this).blit(int_6, int_8, 0, 0, 256, 256);
        float float_7 = this.reloadMonitor.getProgress();
        this.field_17770 = this.field_17770 * 0.95F + float_7 * 0.050000012F;
        if (float_2 < 1.0F) {
            this.renderProgressBar(int_3 / 2 - 150, int_4 / 4 * 3, int_3 / 2 + 150, int_4 / 4 * 3 + 10, this.field_17770, 1.0F - MathHelper.clamp(float_2, 0.0F, 1.0F));
        }

        if (float_2 >= 2.0F) {
            this.client.setOverlay((Overlay)null);
        }

        if (this.field_17771 == -1L && this.reloadMonitor.isApplyStageComplete() && (!this.field_18219 || float_3 >= 2.0F)) {
            this.reloadMonitor.throwExceptions();
            this.field_17771 = SystemUtil.getMeasuringTimeMs();
            done();
            if (this.client.currentScreen != null) {
                this.client.currentScreen.init(this.client, this.client.window.getScaledWidth(), this.client.window.getScaledHeight());
            }
        }

    }


    public void done() {
        this.field_18218.run();
        if (!Bootstrap.initialized) {
            Bootstrap.initialized = true;
            Bootstrap.initList.forEach(mod -> mod.accept("arg"));
        }
    }

}
