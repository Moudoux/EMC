package me.deftware.mixin.mixins;

import me.deftware.client.framework.maps.SettingsMap;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(DebugHud.class)
public class MixinDebugHud {
    @Inject(method = "getLeftText", at = @At("TAIL"), cancellable = true)
    protected void retrieveLeftText(CallbackInfoReturnable<List<String>> cir) {
        cir.setReturnValue(getModifiedList(cir.getReturnValue(), "left_exclude"));
    }

    @Inject(method = "getRightText", at = @At("TAIL"), cancellable = true)
    protected void retrieveRightText(CallbackInfoReturnable<List<String>> cir) {
        cir.setReturnValue(getModifiedList(cir.getReturnValue(), "right_exclude"));
    }

    private List<String> getModifiedList(List<String> stringData, String excludeKey) {
        int lineIndex = 0;
        List<String> newString = new ArrayList<>();

        for (String line : stringData) {
            boolean excludeRender = false;

            if (SettingsMap.isOverwriteMode()) {
                if (SettingsMap.hasValue(lineIndex, excludeKey)) {
                    excludeRender = (boolean) SettingsMap.getValue(lineIndex, excludeKey, false);
                } else {
                    for (Map.Entry<String, Object> mapValue : SettingsMap.getMap().get(SettingsMap.MapKeys.GAME_SETTINGS).entrySet()) {
                        String mapKey = mapValue.getKey();
                        if (mapKey.startsWith("excludeLine_") && line.toLowerCase().contains(mapKey.replaceAll("excludeLine_", "").replaceAll("_", " "))) {
                            excludeRender = (boolean) SettingsMap.getValue(SettingsMap.MapKeys.GAME_SETTINGS, mapKey, false);
                            break;
                        }
                    }
                }
            }

            if (!excludeRender) {
                newString.add(line);
            }

            lineIndex++;
        }

        return newString;
    }
}
