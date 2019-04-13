package me.deftware.client.framework.main;

import com.google.gson.*;
import me.deftware.client.framework.apis.oauth.OAuth;
import me.deftware.client.framework.utils.OSUtils;
import me.deftware.client.framework.wrappers.IMinecraft;
import net.minecraft.client.gui.GuiScreen;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class GuiInstallMods extends GuiScreen {

    private JsonArray mods;
    private String status = "Please wait...", subStatus = "";
    private Iterator it;
    private boolean done = false;

    public GuiInstallMods(JsonArray mods) {
        this.mods = mods;
        it = mods.iterator();
        iterate();
    }

    private void iterate() {
        if (it.hasNext()) {
            JsonObject element = ((JsonElement) it.next()).getAsJsonObject();
            status = "Installing " + element.get("name").getAsString() + "...";
            subStatus = "Preparing download...";
            if (element.has("maven")) {
                String[] name = element.get("maven").getAsString().split(":");
                element.addProperty("url", element.get("url").getAsString() + name[0].replace(".", "/") + "/" + name[1] + "/" + name[2] + "/" + name[1] + "-" + name[2] + ".jar");
            }
            if (element.get("oauth").getAsBoolean()) {
                subStatus = "Getting oAuth token...";
                OAuth.oAuth((success, code, time) -> {
                    element.addProperty("url", element.get("url").getAsString().replace("%oauth%", code));
                    processMod(element);
                });
            } else {
                processMod(element);
            }
        } else {
            done = true;
            try {
                save();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void processMod(JsonObject element) {
        new Thread(() -> {
            File emc_root = new File(OSUtils.getMCDir() + "libraries" + File.separator + "EMC" + File.separator + IMinecraft.getMinecraftVersion() + File.separator);
            String url = element.get("url").getAsString();
            File file = new File(emc_root.getAbsolutePath() + File.separator + element.get("name").getAsString() + ".jar");
            subStatus = "Downloading...";
            downloadFile(url, file);
            subStatus = "Done";
            iterate();
        }).start();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        drawDefaultBackground();
        drawCenteredString(fontRenderer, "Installing EMC mods...", width / 2, 21, 16777215);
        if (!done) {
            drawCenteredString(fontRenderer, status, width / 2, 60, 16777215);
            drawCenteredString(fontRenderer, subStatus, width / 2, 70, 16777215);
        } else {
            drawCenteredString(fontRenderer, "Please restart Minecraft", width / 2, 60, 16777215);
            /*
            IMinecraft.setGuiScreen(null);
            for (JsonElement jsonElement : mods) {
                File file = new File(Bootstrap.emc_root.getAbsolutePath() + File.separator + jsonElement.getAsJsonObject().get("name").getAsString() + ".jar");
                try {
                    Bootstrap.loadMod(file);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            Bootstrap.initList.forEach(mod -> mod.accept("arg"));
            */
        }
    }

    @SuppressWarnings("Duplicates")
    public static void save() throws Exception {
        File jsonFile = new File(OSUtils.getRunningFolder() + OSUtils.getVersion() + ".json");
        if (!jsonFile.exists()) {
            System.err.println("Could not find json file, this should not be possible!");
        } else {
            JsonObject jsonObject = new Gson().fromJson(String.join("", Files.readAllLines(Paths.get(jsonFile.getAbsolutePath()), StandardCharsets.UTF_8)), JsonObject.class);
            JsonArray array = jsonObject.get("emcMods").getAsJsonArray();
            array.forEach(jsonElement -> {
                JsonObject element = jsonElement.getAsJsonObject();
                element.addProperty("installed", true);
            });
            // Save
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(jsonObject.toString());
            String jsonContent = gson.toJson(je);
            PrintWriter writer = new PrintWriter(jsonFile.getAbsolutePath(), "UTF-8");
            writer.println(jsonContent);
            writer.close();
        }
    }

    private void downloadFile(String uri, File file) {
        try {
            URL url = new URL(uri);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
            connection.setRequestMethod("GET");
            FileOutputStream out = new FileOutputStream(file.getAbsolutePath());
            InputStream in = connection.getInputStream();
            int read;
            byte[] buffer = new byte[4096];
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
