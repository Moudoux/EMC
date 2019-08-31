package me.deftware.client.framework.main;

import com.google.gson.*;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.command.CommandRegister;
import me.deftware.client.framework.command.commands.*;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.utils.OSUtils;
import me.deftware.client.framework.utils.Settings;
import me.deftware.client.framework.wrappers.IMinecraft;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * This class is responsible for bootstrapping (initialization) process of EMC freamwork
 * it handles loading all of the mods, connecting with event listeners and checking
 * for available updates
 */
public class Bootstrap {

    public static boolean initialized = false;
    public static Logger logger = LogManager.getLogger(String.format("EMC v%s.%s", FrameworkConstants.VERSION, FrameworkConstants.PATCH));
    public static ArrayList<JsonObject> modsInfo = new ArrayList<>();
    public static boolean isRunning = true;
    public static Settings EMCSettings;
    public static String JSON_JARNAME_NOTE = "DYNAMIC_jarname";
    public static ArrayList<Consumer> initList = new ArrayList<>();
    public static File emc_root, emc_configs;
    private static URLClassLoader modClassLoader;
    private static ConcurrentHashMap<String, EMCMod> mods = new ConcurrentHashMap<>();

    public static void init() {
        try {
            Bootstrap.logger.info(String.format("Loading EMC v%s.%s", FrameworkConstants.VERSION, FrameworkConstants.PATCH));
            emc_root = new File(OSUtils.getMCDir(true) + "libraries" + File.separator + "EMC" + File.separator + IMinecraft.getMinecraftVersion() + File.separator);

            // EMC mods are stored in .minecraft/libraries/EMC
            if (!emc_root.exists()) {
                if (!emc_root.mkdirs()) {
                    Bootstrap.logger.warn("Failed to create EMC mod dir");
                }
            }

            // Load new EMC mods that needs to be installed from json
            try {
                prepMods(emc_root);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            emc_configs = new File(emc_root.getAbsolutePath() + File.separator + "configs" + File.separator);
            if (!emc_configs.exists()) {
                if (!emc_configs.mkdirs()) {
                    Bootstrap.logger.warn("Failed to create EMC config dir");
                }
            }

            // EMC Settings
            EMCSettings = new Settings();
            EMCSettings.initialize(null);
            SettingsMap.update(SettingsMap.MapKeys.EMC_SETTINGS, "RENDER_SCALE", EMCSettings.getFloat("RENDER_SCALE", 1.0f));
            SettingsMap.update(SettingsMap.MapKeys.EMC_SETTINGS, "COMMAND_TRIGGER", EMCSettings.getString("commandtrigger", "."));

            // Load mods
            reloadMods();

            // Register default EMC commands
            registerFrameworkCommands();
        } catch (Exception ex) {
            Bootstrap.logger.warn("Failed to load EMC", ex);
        }
    }

    public static void reloadMods() {
        // Load all EMC mods
        Arrays.stream(emc_root.listFiles()).forEach((file) -> {
            if (!file.isDirectory() && file.getName().endsWith(".jar")) {
                try {
                    if (new File(file.getAbsolutePath() + ".delete").exists()) {
                        Bootstrap.logger.info("Deleting mod " + file.getName());
                        if (!new File(file.getAbsolutePath() + ".delete").delete()) {
                            Bootstrap.logger.warn("Failed to delete " + file.getName() + ".delete");
                        }
                        if (!file.delete()) {
                            Bootstrap.logger.warn("Failed to delete " + file.getName());
                        }
                    } else {
                        // Update check
                        File updateJar = new File(emc_root.getAbsolutePath() + File.separator
                                + file.getName().substring(0, file.getName().length() - ".jar".length())
                                + "_update.jar");
                        if (updateJar.exists()) {
                            Bootstrap.logger.info("Updating " + file.getName());
                            if (!file.delete()) {
                                Bootstrap.logger.warn("Failed to delete " + file.getName());
                            }
                            if (!updateJar.renameTo(file)) {
                                Bootstrap.logger.warn("Failed to rename " + updateJar.getName() + " to " + file.getName());
                            }
                        }
                        Bootstrap.loadMod(file);
                    }
                } catch (Exception ex) {
                    Bootstrap.logger.warn("Failed to load EMC mod: " + file.getName());
                    ex.printStackTrace();
                }
            }
        });
    }

    private static void prepMods(File emcRoot) throws Exception {
        File jsonFile = EMCMod.getEMCJsonFile();
        if (!jsonFile.exists()) {
            Bootstrap.logger.warn("Failed to read Minecraft json file " + jsonFile.getAbsolutePath() + " will not load additional EMC mods");
            return;
        }
        JsonObject contents = EMCMod.getJsonDataAsObject(jsonFile);
        if (contents == null || !contents.has("emcMods")) {
            Bootstrap.logger.warn("Could not find emcMods entry, will not load additional EMC mods");
            return;
        }
        Bootstrap.logger.info("Refreshing additional EMC mods");
        ModInstaller.load(contents.get("emcMods").getAsJsonArray());
    }

    /**
     * Registers framework commands
     */
    static void registerFrameworkCommands() {
        CommandRegister.registerCommand(new CommandMods());
        CommandRegister.registerCommand(new CommandUnload());
        CommandRegister.registerCommand(new CommandVersion());
        CommandRegister.registerCommand(new CommandHelp());
        CommandRegister.registerCommand(new CommandOAuth());
        CommandRegister.registerCommand(new CommandTrigger());
        CommandRegister.registerCommand(new CommandReload());
        CommandRegister.registerCommand(new CommandScale());
    }

    /**
     * Loads an EMC mod
     *
     * @param clientJar
     * @throws Exception
     */
    public static void loadMod(File clientJar) throws Exception {
        JarFile jarFile = new JarFile(clientJar);
        Bootstrap.modClassLoader = URLClassLoader.newInstance(
                new URL[]{new URL("jar", "", "file:" + clientJar.getAbsolutePath() + "!/")},
                Bootstrap.class.getClassLoader());

        // Read client.json
        BufferedReader buffer = new BufferedReader(
                new InputStreamReader(Bootstrap.modClassLoader.getResourceAsStream("client.json")));
        JsonObject jsonObject = new Gson().fromJson(buffer.lines().collect(Collectors.joining("\n")), JsonObject.class);

        // Make sure the mod isnt already loaded
        if (mods.containsKey(jsonObject.get("name").getAsString())) {
            buffer.close();
            return;
        }

        // Add the name of Jar file to the corresponding jsonObject
        jsonObject.addProperty(JSON_JARNAME_NOTE, clientJar.getName());

        Bootstrap.modsInfo.add(jsonObject);

        // Inform about the mod being loaded
        Bootstrap.logger.info("Loading mod: " + jsonObject.get("name").getAsString()
                + " [ver. " + jsonObject.get("version").getAsString() + "] " +
                "by " + jsonObject.get("author").getAsString());

        // Version check
        String[] minVersion = (jsonObject.has("minVersion") ? jsonObject.get("minVersion").getAsString() : String.format("%s.%s", FrameworkConstants.VERSION, FrameworkConstants.PATCH)).split("\\.");
        double version = Double.valueOf(String.format("%s.%s", minVersion[0], minVersion[1]));
        int patch = Integer.valueOf(minVersion[2]);
        if (version >= FrameworkConstants.VERSION && patch > FrameworkConstants.PATCH) {
            MinecraftClient.getInstance().openScreen(new GuiUpdateLoader(jsonObject));
            jarFile.close();
            return;
        }

        // Load classes
        Bootstrap.mods.put(jsonObject.get("name").getAsString(),
                (EMCMod) Bootstrap.modClassLoader.loadClass(jsonObject.get("main").getAsString()).newInstance());

        Enumeration<?> e = jarFile.entries();
        for (JarEntry je = (JarEntry) e.nextElement(); e.hasMoreElements(); je = (JarEntry) e.nextElement()) {
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }
            String className = je.getName().replace(".class", "").replace('/', '.');
            WeakReference<Class> c = new WeakReference<>(Bootstrap.modClassLoader.loadClass(className));
            Bootstrap.logger.info("Loaded class " + c.get().getName());
        }

        jarFile.close();

        Bootstrap.mods.get(jsonObject.get("name").getAsString()).init(jsonObject);
        Bootstrap.logger.info("Loaded mod");

        initList.add((arg) -> {
            Bootstrap.mods.get(jsonObject.get("name").getAsString()).postInit();
        });
    }

    /**
     * Call a function in another EMC mod from your mod, using this you can call functions across EMC mods
     *
     * @param mod    The name of the mod you want to talk with
     * @param method The method name you want to call
     * @param caller The name of your mod
     */
    public static void callMethod(String mod, String method, String caller, Object object) {
        if (Bootstrap.mods.containsKey(mod)) {
            Bootstrap.mods.get(mod).callMethod(method, caller, object);
        } else {
            Bootstrap.logger.error(String.format("EMC mod %s tried to call method %s in mod %s", caller, method, mod));
        }
    }

    /**
     * Returns a list of all loaded EMC mods
     */
    public static ConcurrentHashMap<String, EMCMod> getMods() {
        return Bootstrap.mods;
    }

    /**
     * Unloads all loaded EMC mods
     */
    public static void ejectMods() {
        Bootstrap.mods.forEach((key, mod) -> mod.onUnload());
        Bootstrap.mods.clear();

        //Remove all the commands the mods might have created
        RootCommandNode<?> root = CommandRegister.getDispatcher().getRoot();
        clearChildren(root);

        //Clear the command dispatcher
        CommandRegister.clearDispatcher();

        //Reinitialize the framework default commands
        registerFrameworkCommands();
    }

    /**
     * Loops around command nodes until all of them are removed
     * Clears the unnecessary data from the memory
     *
     * @param commandNode
     */
    private static void clearChildren(CommandNode<?> commandNode) {
        for (CommandNode<?> child : commandNode.getChildren()) {
            clearChildren(child);
        }
        commandNode.getChildren().clear();
    }

    @SuppressWarnings("Duplicates")
    public static void changeVersion(String newVersion) throws Exception {
        File jsonFile = EMCMod.getEMCJsonFile();
        if (!jsonFile.exists()) {
            System.err.println("Could not find json file!");
        } else {
            JsonElement libraries = EMCMod.lookupElementInJson(jsonFile, "libraries");
            if (libraries != null) {
                JsonArray array = libraries.getAsJsonArray();
                array.forEach(jsonElement -> {
                    JsonObject entry = jsonElement.getAsJsonObject();
                    if (entry.get("name").getAsString().contains("me.deftware:EMC")) {
                        String[] current = entry.get("name").getAsString().split(":");
                        entry.addProperty("name", "me.deftware:" + current[1] + ":" + newVersion);
                    }
                });
            }

            // Save
            if (EMCMod.getJsonDataAsObject(jsonFile) != null) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonParser jp = new JsonParser();
                JsonElement je = jp.parse(EMCMod.getJsonDataAsObject(jsonFile).toString());
                String jsonContent = gson.toJson(je);
                PrintWriter writer = new PrintWriter(jsonFile.getAbsolutePath(), "UTF-8");
                writer.println(jsonContent);
                writer.close();
            }
        }
    }

}
