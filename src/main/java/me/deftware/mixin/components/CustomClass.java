package me.deftware.mixin.components;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.metadata.TextureResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;

public class CustomClass extends ResourceTexture {

    private Identifier customLogo;

    public CustomClass(Identifier logo) {
        super(logo);
        customLogo = logo;
    }

    protected class_4006 method_18153(ResourceManager resourceManager_1) {
        MinecraftClient minecraftClient_1 = MinecraftClient.getInstance();
        DefaultResourcePack defaultResourcePack_1 = minecraftClient_1.getResourcePackDownloader().getPack();

        try {
            InputStream inputStream_1 = defaultResourcePack_1.open(ResourceType.ASSETS, customLogo);
            Throwable var5 = null;

            class_4006 var6;
            try {
                var6 = new class_4006((TextureResourceMetadata) null, NativeImage.fromInputStream(inputStream_1));
            } catch (Throwable var16) {
                var5 = var16;
                throw var16;
            } finally {
                if (inputStream_1 != null) {
                    if (var5 != null) {
                        try {
                            inputStream_1.close();
                        } catch (Throwable var15) {
                            var5.addSuppressed(var15);
                        }
                    } else {
                        inputStream_1.close();
                    }
                }

            }

            return var6;
        } catch (IOException var18) {
            return new class_4006(var18);
        }
    }
}