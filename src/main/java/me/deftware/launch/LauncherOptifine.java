package me.deftware.launch;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class LauncherOptifine extends Launcher {

	@Override
	public final void acceptOptions(List<String> args, File gameDir, File assetsDir, String profile) {
		this.list = new ArrayList<>();
	}

}
