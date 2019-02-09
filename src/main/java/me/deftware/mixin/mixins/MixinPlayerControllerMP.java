package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.mixin.imp.IMixinPlayerControllerMP;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ClientPlayerInteractionManager.class)
public class MixinPlayerControllerMP implements IMixinPlayerControllerMP {

    @Shadow
    private GameMode gameMode;

    @Shadow
    private boolean breakingBlock;

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public float getReachDistance() {
        return (float) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "BLOCK_REACH_DISTANCE", gameMode.isCreative() ? 5.0F : 4.5F);
    }

    /**
     * @Author Deftware
     * @reason
     */
    @Overwrite
    public boolean hasExtendedReach() {
        return (boolean) SettingsMap.getValue(SettingsMap.MapKeys.ENTITY_SETTINGS, "EXTENDED_REACH", gameMode.isCreative());
    }

    @Override
    public void setPlayerHittingBlock(boolean state) {
        this.breakingBlock = state;
    }
}
