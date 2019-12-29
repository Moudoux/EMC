package me.deftware.client.framework.compatability;

import com.google.gson.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.path.OSUtils;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class JsonConverter {

    private static String manualJsonLocation;
    private static boolean isFileDialogOpen = false;

    public JsonConverter() {
        File jsonFile = getEMCJsonFile();
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
            jvmArguments.add("-DemcModDefined=true");
            arguments.add("jvm", jvmArguments);
            json.add("arguments", arguments);
            for (JsonElement e : json.get("libraries").getAsJsonArray()) {
                JsonObject o = e.getAsJsonObject();
                if (o.get("name").getAsString().equals("me.deftware:subsystem:0.7.1")) {
                    o.addProperty("name", "me.deftware:subsystem:0.7.2");
                }
            }
            json.addProperty("mainClass", "me.deftware.client.framework.main.Main");
            PrintWriter writer = new PrintWriter(jsonFile.getAbsolutePath(), "UTF-8");
            writer.println(json.toString());
            writer.close();
            Bootstrap.logger.info("Converted Json file");
        } catch (Exception ex) {
            ex.printStackTrace();
            Bootstrap.logger.error("Could not convert Json file {}", jsonFile.getAbsolutePath());
        }
    }

    public static File getEMCJsonFile() {
        String defaultJsonName = getRunningFolder() + getVersion() + ".json";
        File jsonFile = new File(Bootstrap.EMCSettings != null ? Bootstrap.EMCSettings.getString("EMC_JSON_LOCATION", defaultJsonName) : defaultJsonName);

        if (!jsonFile.exists()) {
            if (manualJsonLocation != null && new File(manualJsonLocation).exists()) {
                return new File(manualJsonLocation);
            } else {
                System.out.println("Opening File Open Dialog, as JSON Cannot be found...");

                try {
                    JFXPanel frame = new JFXPanel(); // Initialize JavaFX Environment
                    isFileDialogOpen = true;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.setTitle("Open EMC Json File (Required)");
                            fileChooser.getExtensionFilters().addAll(
                                    new FileChooser.ExtensionFilter("Json", "*.json")
                            );

                            File resultFile = fileChooser.showOpenDialog(null);

                            if (resultFile != null) {
                                manualJsonLocation = resultFile.getAbsolutePath();
                            } else {
                                System.out.println("JSON not found, things will break if other addons are using this!");
                            }
                            isFileDialogOpen = false;
                        }
                    });
                } catch (Exception | Error ex) {
                    System.out.println("Error: EMC Json File Open Dialog failed to open, please Input your EMC Json Location manually in your EMC Config @ emcJsonLocation");
                    ex.printStackTrace();
                }
            }
        } else {
            return jsonFile;
        }

        while (isFileDialogOpen) {
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Save as New Json Location if Settings Available
        if (Bootstrap.EMCSettings != null) {
            Bootstrap.EMCSettings.saveString("EMC_JSON_LOCATION", manualJsonLocation);
            Bootstrap.EMCSettings.saveConfig();
        }
        return new File(manualJsonLocation);
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
