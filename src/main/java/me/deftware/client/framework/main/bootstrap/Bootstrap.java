package me.deftware.client.framework.main.bootstrap;

import com.google.gson.*;
import com.mojang.brigadier.tree.CommandNode;
import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.command.CommandRegister;
import me.deftware.client.framework.command.commands.*;
import me.deftware.client.framework.compatability.JsonConverter;
import me.deftware.client.framework.event.EventBus;
import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.main.bootstrap.discovery.AbstractModDiscovery;
import me.deftware.client.framework.main.bootstrap.discovery.DirectoryModDiscovery;
import me.deftware.client.framework.main.bootstrap.discovery.JVMModDiscovery;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.path.LocationUtil;
import me.deftware.client.framework.path.OSUtils;
import me.deftware.client.framework.utils.Settings;
import me.deftware.client.framework.utils.exception.EMCCrashScreen;
import me.deftware.client.framework.wrappers.IMinecraft;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * This class is responsible for bootstrapping (initialization) process of EMC framework
 * it handles loading all of the mods, connecting with event listeners and checking
 * for available updates
 */
public class Bootstrap {

    public static int CRASH_COUNT = 0;
    public static boolean initialized = false, CRASHED = false;
    public static Logger logger = LogManager.getLogger(String.format("EMC v%s.%s", FrameworkConstants.VERSION, FrameworkConstants.PATCH));
    public static ArrayList<JsonObject> modsInfo = new ArrayList<>();
    public static boolean isRunning = true;
    public static Settings EMCSettings;
    public static File EMC_ROOT, EMC_CONFIGS;
    private static ConcurrentHashMap<String, EMCMod> mods = new ConcurrentHashMap<>();
    private static List<AbstractModDiscovery> modDiscoveries = new ArrayList<>(Arrays.asList(new JVMModDiscovery(), new DirectoryModDiscovery()));

    public static void init() {
        try {
            File emcJar = LocationUtil.getEMC().toFile(), mcDir = LocationUtil.getMinecraftDir().toFile();
            if (System.getProperty("EMCDir", "null").equalsIgnoreCase("null")) {
                System.setProperty("EMCDir", emcJar != null ? emcJar.getParentFile().getAbsolutePath() : "null");
            }
            if (System.getProperty("MCDir", "null").equalsIgnoreCase("null")) {
                System.setProperty("MCDir", mcDir != null ? mcDir.getAbsolutePath() : "null");
            }
            Bootstrap.logger.info(String.format("Loading EMC v%s.%s", FrameworkConstants.VERSION, FrameworkConstants.PATCH));
            EMC_ROOT = new File(OSUtils.getMCDir() + "libraries" + File.separator + "EMC" + File.separator + IMinecraft.getMinecraftVersion() + File.separator);
            EMC_CONFIGS = new File(EMC_ROOT.getAbsolutePath() + File.separator + "configs" + File.separator);
            if (!EMC_ROOT.exists()) {
                if (!EMC_ROOT.mkdirs()) {
                    Bootstrap.logger.warn("Failed to create EMC directories");
                }
            }
            if (!EMC_CONFIGS.exists()) {
                if (!EMC_CONFIGS.mkdirs()) {
                    Bootstrap.logger.warn("Failed to create EMC config dir");
                }
            }
            Bootstrap.logger.debug("EMC root is {}", EMC_ROOT.getAbsolutePath());
            EMCSettings = new Settings();
            EMCSettings.initialize(null);
            SettingsMap.update(SettingsMap.MapKeys.EMC_SETTINGS, "RENDER_SCALE", EMCSettings.getFloat("RENDER_SCALE", 1.0f));
            SettingsMap.update(SettingsMap.MapKeys.EMC_SETTINGS, "COMMAND_TRIGGER", EMCSettings.getString("commandtrigger", "."));
            if (!System.getProperty("emcModDefined", "false").equalsIgnoreCase("true") && CRASH_COUNT == 0) {
                Bootstrap.logger.warn("Converting old Json emcMods entry to JVM arguments");
                new JsonConverter();
            }
            modDiscoveries.forEach(discovery -> {
                discovery.discover();
                Bootstrap.logger.info("{} found {} mod{}", discovery.getClass().getSimpleName(), discovery.getMods().size(), discovery.getMods().size() != 1 ? "s" : "");
            });
            registerFrameworkCommands();
            modDiscoveries.forEach(discovery -> {
                discovery.getMods().forEach(mod -> {
                    mod.init();
                    try {
                        loadMod(mod.file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Bootstrap.logger.error("Failed to load {}", mod.file.getName());
                    }
                });
            });
        } catch (Exception ex) {
            Bootstrap.logger.warn("Failed to load EMC", ex);
        }
    }

    public static void reset() {
        try {
            ejectMods();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        SettingsMap.reset();
        modsInfo.clear();
        mods.clear();
        modDiscoveries.clear();
        modDiscoveries = new ArrayList<>(Arrays.asList(new JVMModDiscovery(), new DirectoryModDiscovery()));
    }

    /**
     * Registers framework commands
     */
    private static void registerFrameworkCommands() {
        Bootstrap.logger.debug("Loading EMC commands");
        clearChildren(CommandRegister.getDispatcher().getRoot());
        CommandRegister.clearDispatcher();
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
        URLClassLoader currentModClassLoader = URLClassLoader.newInstance(
                new URL[]{new URL("jar", "", "file:" + clientJar.getAbsolutePath() + "!/")},
                Bootstrap.class.getClassLoader());
        BufferedReader buffer = new BufferedReader(new InputStreamReader(Objects.requireNonNull(currentModClassLoader.getResourceAsStream("client.json"))));
        JsonObject jsonObject = new Gson().fromJson(buffer.lines().collect(Collectors.joining("\n")), JsonObject.class);
        boolean remove = false;
        if (!jsonObject.has("scheme")) {
            remove = true;
        } else if (jsonObject.get("scheme").getAsInt() < 2) {
            remove = true;
        }
        if (remove) {
            currentModClassLoader.close();
            buffer.close();
            jarFile.close();
            Bootstrap.logger.warn("Uninstalling unsupported mod {}", clientJar.getName());
            if (!clientJar.delete()) {
                Bootstrap.logger.error("Failed to delete {}", clientJar.getName());
            }
            return;
        }
        if (mods.containsKey(jsonObject.get("name").getAsString())) {
            buffer.close();
            return;
        }
        Bootstrap.modsInfo.add(jsonObject);
        Bootstrap.logger.debug("Loading mod: " + jsonObject.get("name").getAsString()
                + " [ver. " + jsonObject.get("version").getAsString() + "] " +
                "by " + jsonObject.get("author").getAsString());
        String[] minVersion = (jsonObject.has("minVersion") ? jsonObject.get("minVersion").getAsString() : String.format("%s.%s", FrameworkConstants.VERSION, FrameworkConstants.PATCH)).split("\\.");
        double version = Double.parseDouble(String.format("%s.%s", minVersion[0], minVersion[1]));
        int patch = Integer.parseInt(minVersion[2]);
        if (version >= FrameworkConstants.VERSION && patch > FrameworkConstants.PATCH) {
            Bootstrap.logger.error("Could not load {}, required EMC version mismatch", jsonObject.get("name").getAsString());
            jarFile.close();
            return;
        }
        Bootstrap.mods.put(jsonObject.get("name").getAsString(),
                (EMCMod) currentModClassLoader.loadClass(jsonObject.get("main").getAsString()).newInstance());
            Enumeration<?> e = jarFile.entries();
            for (JarEntry je = (JarEntry) e.nextElement(); e.hasMoreElements(); je = (JarEntry) e.nextElement()) {
                if (je.isDirectory() || !je.getName().endsWith(".class")) {
                    continue;
                }
                String className = je.getName().replace(".class", "").replace('/', '.');
                WeakReference<Class> c = new WeakReference<>(currentModClassLoader.loadClass(className));
                Bootstrap.logger.debug("Loaded class " + Objects.requireNonNull(c.get()).getName());
            }
        buffer.close();
        jarFile.close();
        Bootstrap.mods.get(jsonObject.get("name").getAsString()).classLoader = currentModClassLoader;
        Bootstrap.mods.get(jsonObject.get("name").getAsString()).init(jsonObject);
        Bootstrap.logger.info("Loaded {}", jsonObject.get("name").getAsString());
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
            Bootstrap.logger.debug("Mod {} calling {} in mod {}", caller, method, mod);
            Bootstrap.mods.get(mod).callMethod(method, caller, object);
        } else {
            Bootstrap.logger.error("EMC mod {} tried to call method {} in mod {}", caller, method, mod);
        }
    }

    public static ConcurrentHashMap<String, EMCMod> getMods() {
        return Bootstrap.mods;
    }

    public static void ejectMods() {
        EventBus.clearEvents();
        Bootstrap.logger.warn("Ejecting all loaded mods");
        for (EMCMod mod : mods.values()) {
            try {
                mod.onUnload();
            } catch (NullPointerException ignored) { }
            try {
                mod.classLoader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Bootstrap.mods.clear();
        registerFrameworkCommands();
        MinecraftClient.getInstance().options.gamma = 0.5F;
        EMCSettings = null;
        System.gc();
    }

    private static void clearChildren(CommandNode<?> commandNode) {
        for (CommandNode<?> child : commandNode.getChildren()) {
            clearChildren(child);
        }
        commandNode.getChildren().clear();
    }

}
