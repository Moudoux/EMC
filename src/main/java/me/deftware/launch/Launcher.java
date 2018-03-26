package me.deftware.launch;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.tools.obfuscation.mcp.ObfuscationServiceMCP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Launcher implements ITweaker {

	protected ArrayList<String> list = new ArrayList<>();

	@Override
	public void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
		list.addAll(args);
		if (!args.contains("--version") && profile != null) {
			list.add("--version");
			list.add(profile);
		}
		if (!args.contains("--assetDir") && assetsDir != null) {
			list.add("--assetDir");
			list.add(assetsDir.getAbsolutePath());
		}
		if (!args.contains("--gameDir") && gameDir != null) {
			list.add("--gameDir");
			list.add(gameDir.getAbsolutePath());
		}
	}

	@Override
	public void injectIntoClassLoader(LaunchClassLoader classLoader) {
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.client.json");
		MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
		MixinEnvironment.getDefaultEnvironment().setObfuscationContext(ObfuscationServiceMCP.NOTCH);
	}

	@Override
	public String getLaunchTarget() {
		return "net.minecraft.client.main.Main";
	}

	@Override
	public String[] getLaunchArguments() {
		return list.toArray(new String[list.size()]);
	}
}
