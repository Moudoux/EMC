package me.deftware.client.framework.compatability;

import com.google.gson.*;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.path.OSUtils;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class JsonConverter {

    public JsonConverter() {
        File jsonFile = new File(getRunningFolder() + getVersion() + ".json");
        if (!jsonFile.exists()) {
            Bootstrap.logger.error("Could not find Json file, is this a custom launcher?");
            return;
        }
        try {
            Bootstrap.logger.info("Found Json file {}", jsonFile.getAbsolutePath());
            JsonObject json = new Gson().fromJson(FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8), JsonObject.class);
            if (!json.has("emcMods")) {
                throw new Exception("Could not find emcMods entry");
            }
            JsonArray mods = json.get("emcMods").getAsJsonArray();
            json.remove("arguments");
            JsonObject arguments = new JsonObject();
            JsonArray jvmArguments = new JsonArray();
            int i = 0;
            for (JsonElement e : mods) {
                JsonObject o = e.getAsJsonObject();
                String data = o.get("name").getAsString() + "," + o.get("maven").getAsString() + "," + o.get("url").getAsString();
                jvmArguments.add("-DemcMod" + i + "\u003d" + data);
                System.setProperty("emcMod" + i, data);
                i++;
            }
            arguments.add("jvm", jvmArguments);
            json.add("arguments", arguments);
            PrintWriter writer = new PrintWriter(jsonFile.getAbsolutePath(), "UTF-8");
            writer.println(json.toString());
            writer.close();
            Bootstrap.logger.info("Converted Json file");
        } catch (Exception ex) {
            ex.printStackTrace();
            Bootstrap.logger.error("Could not convert Json file {}", jsonFile.getAbsolutePath());
        }
    }

    public static String getVersion() {
        return MinecraftClient.getInstance().getGameVersion();
    }

    public static String getRunningFolder() {
        if (new File(OSUtils.getMCDir() + "versions").exists()) {
            return OSUtils.getMCDir() + "versions" + File.separator + getVersion() + File.separator;
        } else {
            return MinecraftClient.getInstance().runDirectory.getAbsolutePath() + File.separator;
        }
    }


}
