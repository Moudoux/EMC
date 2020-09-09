package me.deftware.mixin.mixins.render;

import me.deftware.mixin.imp.IMixinOutlineVertexConsumerProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.OutlineVertexConsumerProvider;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(OutlineVertexConsumerProvider.class)
public class MixinOutlineVertexConsumerProvider implements IMixinOutlineVertexConsumerProvider {


}
