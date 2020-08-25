package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventStructureLocation;
import me.deftware.client.framework.math.position.DoubleBlockPosition;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class MixinHeldItemRenderer {

    @Unique
    private static ItemStack i = null;

    @Unique
    private static EventStructureLocation.StructureType getStructure(String name) {
        if (name.equals("{\"translate\":\"filled_map.buried_treasure\"}")) {
            return EventStructureLocation.StructureType.BuriedTreasure;
        }
        if (name.equals("{\"translate\":\"filled_map.monument\"}")) {
            return EventStructureLocation.StructureType.OceanMonument;
        }
        if (name.equals("{\"translate\":\"filled_map.mansion\"}")) {
            return EventStructureLocation.StructureType.WoodlandMansion;
        }
        return EventStructureLocation.StructureType.OtherMapIcon;
    }
    
    @Inject(method = "renderFirstPersonMap", at = @At("HEAD"))
    private void renderFirstPersonMap(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int swingProgress,
        ItemStack stack, CallbackInfo info) {
        if (i != null && ItemStack.areTagsEqual(i, stack)) return;
        i = stack.copy();
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null && compoundTag.contains("Decorations", 9)) {
            // Try and Get Decoration X and Z
            String mapName = compoundTag.getCompound("display").getString("Name");
            final EventStructureLocation.StructureType structure = getStructure(mapName);
            ListTag icons = compoundTag.getList("Decorations", 10);
           
            icons.forEach((icon) -> {
                if (icon instanceof CompoundTag) {
                    EventStructureLocation event = new EventStructureLocation(
                        new DoubleBlockPosition(((CompoundTag) icon).getDouble("x"), 0,
                            ((CompoundTag) icon).getDouble("z")),
                        structure);
                    event.broadcast();
                }
            });
        }
    }
}
