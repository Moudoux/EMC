package me.deftware.launch;

import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.tools.obfuscation.mcp.ObfuscationServiceMCP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LauncherForge extends Launcher {

	@Override
	public final void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
		list = new ArrayList<>();
	}

	@Override
	public final void injectIntoClassLoader(LaunchClassLoader classLoader) {
		super.injectIntoClassLoader(classLoader);
		MixinEnvironment.getDefaultEnvironment().setObfuscationContext(ObfuscationServiceMCP.SEARGE);
	}

}
