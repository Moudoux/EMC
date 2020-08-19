package me.deftware.mixin.mixins;

import me.deftware.client.framework.event.events.EventStructureLocation;
import me.deftware.client.framework.math.position.DoubleBlockPosition;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FilledMapItem.class)
public class MixinFilledMapItem {
    @Inject(method = "fillExplorationMap", at = @At("TAIL"))
    private static void fillMap_after(ServerWorld world, ItemStack stack, CallbackInfo ci) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null && compoundTag.contains("Decorations", 9)) {
            // Try and Get Decoration X and Z
            EventStructureLocation event = new EventStructureLocation(new DoubleBlockPosition(compoundTag.getDouble("x"), 0, compoundTag.getDouble("z")), EventStructureLocation.StructureType.BuriedTreasure);
            event.broadcast();
        }
    }
}
