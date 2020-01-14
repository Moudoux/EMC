package me.deftware.client.framework.main.bootstrap.discovery;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.wrappers.IMinecraft;
import net.minecraft.client.main.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClasspathModDiscovery extends AbstractModDiscovery {

	@Override
	public void discover() {
		try {
			List<URL> urls = Collections.list(Main.class.getClassLoader().getResources("emc-mod-data.json"));
			for (URL url : urls) {
				BufferedReader buffer = new BufferedReader(new InputStreamReader(url.openStream()));
				JsonArray json = new Gson().fromJson(buffer.lines().collect(Collectors.joining("\n")), JsonArray.class);
				int start = 25;
				for (JsonElement e : json) {
					JsonObject data = e.getAsJsonObject();
					System.setProperty("emcMod" + start, data.get("id").getAsString() + "," + data.get("name").getAsString().replace("%mc%", IMinecraft.getMinecraftVersion()) + "," + data.get("maven").getAsString());
					start++;
				}
			}
			Bootstrap.logger.info("ClasspathModDiscovery found {} entries", urls.size());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}