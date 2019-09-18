package me.deftware.client.framework.wrappers;

import me.deftware.client.framework.maps.SettingsMap;
import me.deftware.client.framework.wrappers.entity.IEntity;
import me.deftware.client.framework.wrappers.entity.IEntity.EntityType;
import me.deftware.client.framework.wrappers.gui.IGuiInventory;
import me.deftware.client.framework.wrappers.gui.IGuiScreen;
import me.deftware.client.framework.wrappers.gui.IScreens;
import me.deftware.client.framework.wrappers.gui.IScreens.Screen;
import me.deftware.client.framework.wrappers.world.IBlockPos;
import me.deftware.mixin.imp.IMixinMinecraft;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.main.Main;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.options.AoOption;
import net.minecraft.container.GenericContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.packet.ChatMessageC2SPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

import java.io.File;
import java.net.URISyntaxException;

@SuppressWarnings("All")
public class IMinecraft {

    public static IServerData lastServer = null;
    private static IServerData iServerCache = null;
    public static AoOption ao = null;

    public static File getMinecraftFile() throws URISyntaxException {
        return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
    }

    public static void clickMouse() {
        ((IMixinMinecraft) MinecraftClient.getInstance()).doClickMouse();
    }

    public synchronized static IServerData getCurrentServer() {
        if (MinecraftClient.getInstance().getCurrentServerEntry() == null) {
            return null;
        }
        if (iServerCache != null && MinecraftClient.getInstance().getCurrentServerEntry() != null) {
            if (iServerCache.address.equals(MinecraftClient.getInstance().getCurrentServerEntry().address)) {
                return iServerCache;
            }
        }
        ServerInfo sd = MinecraftClient.getInstance().getCurrentServerEntry();
        iServerCache = new IServerData(sd.name, sd.address, sd.isLocal());
        iServerCache.version = sd.version;
        return iServerCache;
    }

    public static String getRunningLocation() throws URISyntaxException {
        return new File(MinecraftClient.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath())
                .getParent();
    }

    public static double getScaleFactor() {
        return MinecraftClient.getInstance().method_22683().getScaleFactor();
    }

    public static void setScaleFactor(int factor) {
        MinecraftClient.getInstance().options.guiScale = factor;
        MinecraftClient.getInstance().onResolutionChanged();
    }

    public static IGuiScreen getIScreen() {
        if (MinecraftClient.getInstance().currentScreen != null) {
            if (MinecraftClient.getInstance().currentScreen instanceof IGuiScreen) {
                return (IGuiScreen) MinecraftClient.getInstance().currentScreen;
            }
        }
        return null;
    }

    public static long getWindowHandle() {
        return MinecraftClient.getInstance().method_22683().getHandle();
    }

    public static boolean isFocused() {
        return ((IMixinMinecraft) MinecraftClient.getInstance()).getIsWindowFocused();
    }

    public static float getRenderPartialTicks() {
        return MinecraftClient.getInstance().getTickDelta();
    }

    public static void leaveServer() {
        MinecraftClient.getInstance().player.networkHandler.sendPacket(new ChatMessageC2SPacket(new String(new char[]{167})));
    }

    public static IBlockPos getBlockOver() {
        if (!IMinecraft.isMouseOver()) {
            return null;
        }
        if (MinecraftClient.getInstance().hitResult != null && MinecraftClient.getInstance().hitResult instanceof BlockHitResult && ((BlockHitResult) MinecraftClient.getInstance().hitResult).getBlockPos() != null) {
            return new IBlockPos(((BlockHitResult) MinecraftClient.getInstance().hitResult).getBlockPos());
        }
        return null;
    }

    public static IEntity getPointedEntity() {
        Entity pointedEntity = MinecraftClient.getInstance().targetedEntity;
        if (pointedEntity != null && pointedEntity instanceof LivingEntity) {
            return new IEntity(pointedEntity);
        }
        return null;
    }

    public static boolean isEntityHit() {
        return MinecraftClient.getInstance().hitResult != null && MinecraftClient.getInstance().hitResult instanceof EntityHitResult && ((EntityHitResult) MinecraftClient.getInstance().hitResult).getEntity() != null;
    }

    public static int getFPS() {
        return MinecraftClient.getCurrentFps();
    }

    public static boolean isInGame() {
        return MinecraftClient.getInstance().currentScreen == null;
    }

    public static void reloadRenderers() {
        if (ao == null) {
            ao = MinecraftClient.getInstance().options.ao;
        }
        if (SettingsMap.isOverrideMode()) {
            ao = MinecraftClient.getInstance().options.ao;
            MinecraftClient.getInstance().options.ao = AoOption.OFF;
        } else {
            MinecraftClient.getInstance().options.ao = ao;
        }
        MinecraftClient.getInstance().worldRenderer.reload();
    }

    public static void triggerGuiRenderer() {
        MinecraftClient.getInstance().method_22683().method_4493(false);
    }

    public static void addEntityToWorld(int id, IEntity entity) {
        MinecraftClient.getInstance().world.addEntity(id, entity.getEntity());
    }

    public static void removeEntityFromWorld(int id) {
        MinecraftClient.getInstance().world.removeEntity(id);
    }

    public static void connectToServer(IServerData server) {
        MinecraftClient.getInstance().openScreen(new ConnectScreen(new MultiplayerScreen(null), MinecraftClient.getInstance(), server));
    }

    public static int thridPersonView() {
        return MinecraftClient.getInstance().options.perspective;
    }

    public static int getGuiScaleRaw() {
        return MinecraftClient.getInstance().options.guiScale;
    }

    public static int getGuiScale() {
        int factor = MinecraftClient.getInstance().method_22683().calculateScaleFactor(MinecraftClient.getInstance().options.guiScale, MinecraftClient.getInstance().forcesUnicodeFont());
        if (factor == 0) {
            factor = 4;
        }
        return factor;
    }

    public static boolean isDebugInfoShown() {
        return MinecraftClient.getInstance().options.debugEnabled;
    }

    public static IGuiScreen getCurrentScreen() {
        if (MinecraftClient.getInstance().currentScreen != null) {
            if (MinecraftClient.getInstance().currentScreen instanceof IGuiScreen) {
                return (IGuiScreen) MinecraftClient.getInstance().currentScreen;
            }
        }
        return null;
    }

    public static void setGuiScreen(IGuiScreen screen) {
        MinecraftClient.getInstance().openScreen(screen);
    }

    public static void openInventory(IGuiInventory inventory) {
        MinecraftClient.getInstance().openScreen(inventory);
    }

    public static void setGuiScreenType(Screen screen) {
        MinecraftClient.getInstance().openScreen(IScreens.translate(screen, null));
    }

    public static void shutdown() {
        MinecraftClient.getInstance().stop();
    }

    public static double getGamma() {
        return MinecraftClient.getInstance().options.gamma;
    }

    public static void setGamma(double value) {
        MinecraftClient.getInstance().options.gamma = value;
    }

    public static void setRightClickDelayTimer(int delay) {
        ((IMixinMinecraft) MinecraftClient.getInstance()).setRightClickDelayTimer(delay);
    }

    public static boolean isChatOpen() {
        if (MinecraftClient.getInstance().currentScreen != null) {
            if (MinecraftClient.getInstance().currentScreen instanceof ChatScreen) {
                return true;
            }
        }
        return false;
    }

    public static boolean isContainerOpen() {
        if (MinecraftClient.getInstance().currentScreen != null) {
            if (MinecraftClient.getInstance().currentScreen instanceof AbstractContainerScreen
                    && !(MinecraftClient.getInstance().currentScreen instanceof InventoryScreen)) {
                return true;
            }
        }
        return false;
    }

    public static IEntity getRenderViewEntity() {
        if(MinecraftClient.getInstance().getCameraEntity() == null) {
            return null;
        }
        return new IEntity(MinecraftClient.getInstance().getCameraEntity());
    }

    public static boolean isInventoryOpen() {
        if (MinecraftClient.getInstance().currentScreen != null) {
            if (MinecraftClient.getInstance().currentScreen instanceof AbstractContainerScreen
                    && (MinecraftClient.getInstance().currentScreen instanceof InventoryScreen
                    || MinecraftClient.getInstance().currentScreen instanceof CreativeInventoryScreen)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isChestOpen() {
        if (MinecraftClient.getInstance().player.container != null) {
            if (MinecraftClient.getInstance().player.container instanceof GenericContainer) {
                return true;
            }
        }
        return false;
    }

    public static String getMinecraftVersion() {
        return SharedConstants.getGameVersion().getName();
    }

    public static int getMinecraftProtocolVersion() {
        return SharedConstants.getGameVersion().getProtocolVersion();
    }

    public static boolean isMouseOver() {
        if (MinecraftClient.getInstance().hitResult != null) {
            return true;
        }
        return false;
    }

    public static IEntity getHit() {
        if (!isEntityHit()) {
            return null;
        }
        return new IEntity(((EntityHitResult) MinecraftClient.getInstance().hitResult).getEntity());
    }

    public static boolean entityHitInstanceOf(EntityType type) {
        if (!isEntityHit()) {
            return false;
        }
        if (type.equals(EntityType.ENTITY_LIVING_BASE)) {
            return ((EntityHitResult) MinecraftClient.getInstance().hitResult).getEntity() instanceof LivingEntity;
        }
        return false;
    }

}