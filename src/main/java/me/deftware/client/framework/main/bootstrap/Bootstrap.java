package me.deftware.client.framework.main.bootstrap;

import com.google.gson.JsonObject;
import com.mojang.brigadier.tree.CommandNode;
import me.deftware.client.framework.FrameworkConstants;
import me.deftware.client.framework.command.CommandRegister;
import me.deftware.client.framework.command.commands.*;
import me.deftware.client.framework.config.Settings;
import me.deftware.client.framework.event.EventBus;
import me.deftware.client.framework.input.Keyboard;
import me.deftware.client.framework.main.EMCMod;
import me.deftware.client.framework.main.bootstrap.discovery.AbstractModDiscovery;
import me.deftware.client.framework.main.bootstrap.discovery.ClasspathModDiscovery;
import me.deftware.client.framework.main.bootstrap.discovery.DirectoryModDiscovery;
import me.deftware.client.framework.main.bootstrap.discovery.JVMModDiscovery;
import me.deftware.client.framework.main.validation.Validator;
import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.minecraft.Minecraft;
import me.deftware.client.framework.util.path.LocationUtil;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is responsible for bootstrapping (initialization) process of EMC framework
 * it handles loading all of the mods, connecting with event listeners and checking
 * for available updates
 *
 * @author Deftware
 */
public class Bootstrap {

    public static boolean initialized = false;
    public static Logger logger = LogManager.getLogger(String.format("EMC v%s.%s", FrameworkConstants.VERSION, FrameworkConstants.PATCH));
    public static ArrayList<JsonObject> modsInfo = new ArrayList<>();
    public static boolean isRunning = true;
    public static Settings EMCSettings;
    public static File EMC_ROOT, EMC_CONFIGS;

    private static final ConcurrentHashMap<String, EMCMod> mods = new ConcurrentHashMap<>();

    /**
     * ClasspathModDiscovery should always be the first item
     */
    private static List<AbstractModDiscovery> modDiscoveries = new ArrayList<>(Arrays.asList(new ClasspathModDiscovery(), new JVMModDiscovery(), new DirectoryModDiscovery()));

    public static void init() {
        try {
            Keyboard.populateCodePoints();
            for (int i = 0; i < 200; i++) {
                if (System.getProperty("logging" + i, "null").equalsIgnoreCase("null")) break;
                logger.debug(System.getProperty("logging" + i));
            }
            File capesCache = new File(Minecraft.getRunDir(), "libraries/EMC/capes/");
            if (!capesCache.exists()) {
                if (!capesCache.mkdirs()) {
                    Bootstrap.logger.warn("Failed to create EMC capes dir");
                }
            }
            logger.info("Loading EMC v{}.{}", FrameworkConstants.VERSION, FrameworkConstants.PATCH);
            EMC_ROOT = new File(Minecraft.getRunDir(), "libraries" + File.separator + "EMC" + File.separator + Minecraft.getMinecraftVersion() + File.separator);
            logger.info("EMC root dir is {}", EMC_ROOT.getAbsolutePath());
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
            FrameworkConstants.VALID_EMC_INSTANCE = Validator.isValidInstance();
            if (!FrameworkConstants.VALID_EMC_INSTANCE) {
                Bootstrap.logger.warn("EMC instance is not up to date! This may cause instability or crashes.");
            }
            FrameworkConstants.SUBSYSTEM_IN_USE = System.getProperty("SUBSYSTEM", "false").equalsIgnoreCase("true");
            EMCSettings = new Settings("EMC");
            EMCSettings.setupShutdownHook();
            SettingsMap.update(SettingsMap.MapKeys.EMC_SETTINGS, "RENDER_SCALE", EMCSettings.getPrimitive("RENDER_SCALE", 1.0f));
            SettingsMap.update(SettingsMap.MapKeys.EMC_SETTINGS, "RENDER_FONT_SHADOWS", EMCSettings.getPrimitive("RENDER_FONT_SHADOWS", true));
            SettingsMap.update(SettingsMap.MapKeys.EMC_SETTINGS, "COMMAND_TRIGGER", EMCSettings.getPrimitive("commandtrigger", "."));
            modDiscoveries.forEach(discovery -> {
                discovery.discover();
                if (discovery.getSize() != 0) {
                    Bootstrap.logger.info("{} found {} mod{}", discovery.getClass().getSimpleName(), discovery.getSize(), discovery.getSize() != 1 ? "s" : "");
                }
                discovery.getMods().forEach(AbstractModDiscovery.AbstractModEntry::init);
            });
            registerFrameworkCommands();
            modDiscoveries.forEach(discovery -> discovery.getMods().forEach(mod -> {
                try {
                    loadMod(mod);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Bootstrap.logger.error("Failed to load {}", mod.getFile().getName());
                }
            }));
        } catch (Exception ex) {
            Bootstrap.logger.warn("Failed to load EMC", ex);
        }
    }

    public File getEMCJar() {
        return LocationUtil.getEMC().toFile();
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

    public static synchronized void loadMod(AbstractModDiscovery.AbstractModEntry entry) throws Exception {
        EMCMod mod = entry.toInstance();
        if (mod == null) {
            return;
        }
        // Check compatibility
        String[] minVersion = (entry.getJson().has("minVersion") ? entry.getJson().get("minVersion").getAsString() : String.format("%s.%s", FrameworkConstants.VERSION, FrameworkConstants.PATCH)).split("\\.");
        if (Double.parseDouble(String.format("%s.%s", minVersion[0], minVersion[1])) >= FrameworkConstants.VERSION && Integer.parseInt(minVersion[2]) > FrameworkConstants.PATCH) {
            Bootstrap.logger.warn("Will not load {}, unsupported EMC version", entry.getFile().getName());
            return;
        } else if (!entry.getJson().has("scheme") || entry.getJson().get("scheme").getAsInt() < FrameworkConstants.SCHEME) {
            Bootstrap.logger.warn("Will not load unsupported mod {}, unsupported scheme", entry.getFile().getName());
            return;
        }
        // Ensure only one instance is loaded
        if (Bootstrap.mods.containsKey(entry.getJson().get("name").getAsString())) {
            return;
        }
        Bootstrap.logger.debug("Loading {} v{} by {}", entry.getJson().get("name").getAsString(), entry.getJson().get("version").getAsString(), entry.getJson().get("author").getAsString());
        Bootstrap.mods.put(entry.getJson().get("name").getAsString(), mod);
        Bootstrap.mods.get(entry.getJson().get("name").getAsString()).init(entry.getJson());
        Bootstrap.logger.info("Loaded {}", entry.getJson().get("name").getAsString());
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
        System.gc();
    }

    private static void clearChildren(CommandNode<?> commandNode) {
        for (CommandNode<?> child : commandNode.getChildren()) {
            clearChildren(child);
        }
        commandNode.getChildren().clear();
    }

}
