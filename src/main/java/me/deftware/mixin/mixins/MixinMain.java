package me.deftware.mixin.mixins;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import me.deftware.client.framework.main.Bootstrap;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(Main.class)
public class MixinMain {

	@Inject(method = "main", at = @At("HEAD"), remap = false)
	private static void main(String[] args, CallbackInfo ci) {
		OptionParser optionParser = new OptionParser();
		optionParser.allowsUnrecognizedOptions();
		OptionSpec<String> modClassOption = optionParser.accepts("emcMod").withRequiredArg();
		OptionSet options = optionParser.parse(args);
		Bootstrap.internalModClassNames = new ArrayList<>(options.valuesOf(modClassOption));
		Bootstrap.internalModClassNames.forEach((clazz) -> System.out.println(String.format("Found EMC mod class %s", clazz)));
	}

}
