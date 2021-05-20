package me.deftware.client.framework.minecraft;

import me.deftware.client.framework.conversion.ComparedConversion;
import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.entity.types.main.MainEntityPlayer;
import me.deftware.client.framework.gui.GuiScreen;
import me.deftware.client.framework.helper.ScreenHelper;
import me.deftware.client.framework.render.camera.GameCamera;
import me.deftware.client.framework.util.minecraft.BlockSwingResult;
import me.deftware.client.framework.util.minecraft.ServerConnectionInfo;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.Perspective;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import javax.annotation.Nullable;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Deftware
 */
public class Minecraft {

	public static final Queue<Runnable> RENDER_THREAD = new ConcurrentLinkedQueue<>();

	private static final ComparedConversion<ClientPlayerEntity, MainEntityPlayer> mainPlayer =
			new ComparedConversion<>(() -> MinecraftClient.getInstance().player, MainEntityPlayer::new);

	private static final ComparedConversion<net.minecraft.entity.Entity, Entity> cameraEntity =
			new ComparedConversion<>(() -> MinecraftClient.getInstance().cameraEntity, Entity::newInstance);

	private static final ComparedConversion<net.minecraft.entity.Entity, Entity> hitEntity =
			new ComparedConversion<>(() -> {
				if (MinecraftClient.getInstance().crosshairTarget != null) {
					if (MinecraftClient.getInstance().crosshairTarget.getType() == HitResult.Type.ENTITY) {
						return ((EntityHitResult) MinecraftClient.getInstance().crosshairTarget).getEntity();
					}
				}
				return null;
			}, Entity::newInstance);

	private static final ComparedConversion<HitResult, BlockSwingResult> hitBlock =
			new ComparedConversion<>(() -> {
				if (MinecraftClient.getInstance().crosshairTarget != null) {
					if (MinecraftClient.getInstance().crosshairTarget.getType() == HitResult.Type.BLOCK) {
						return MinecraftClient.getInstance().crosshairTarget;
					}
				}
				return null;
			}, BlockSwingResult::new);

	private static final ComparedConversion<ServerInfo, ServerConnectionInfo> connectedServer =
			new ComparedConversion<>(() -> MinecraftClient.getInstance().getCurrentServerEntry(), ServerConnectionInfo::new);

	public static ServerConnectionInfo lastConnectedServer = null;

	private static final GameCamera camera = new GameCamera();

	/**
	 * The main player
	 */
	@Nullable
	public static MainEntityPlayer getPlayer() {
		return mainPlayer.get();
	}

	@Nullable
	public static Entity getCameraEntity() {
		return cameraEntity.get();
	}

	public static int getMinecraftChatScaledYOffset() {
		if (ScreenHelper.isChatOpen()) {
			int chatHeight = 24, multiplier = getRealScaledMultiplier(), scaleFactor = (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
			if (scaleFactor == 0) return chatHeight;
			chatHeight *= multiplier;
			if (scaleFactor % 2 == 0) chatHeight += 5;
			return chatHeight;
		}
		return 0;
	}

	public static int getMinecraftChatScaledXOffset() {
		if (ScreenHelper.isChatOpen()) {
			int chatX = 4, multiplier = getRealScaledMultiplier(), scaleFactor = (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
			if (scaleFactor == 0) return chatX;
			chatX *= multiplier;
			return chatX;
		}
		return 4;
	}

	public static int getRealScaledMultiplier() {
		int scaleFactor = (int) MinecraftClient.getInstance().getWindow().getScaleFactor();
		if (scaleFactor != 0) {
			scaleFactor /= scaleFactor % 2 == 0 ? 2 : 1.8;
		}
		return scaleFactor;
	}

	public static void openScreen(GuiScreen screen) {
		MinecraftClient.getInstance().openScreen(screen);
	}

	public static String getRunningLocation() throws URISyntaxException {
		return new File(MinecraftClient.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
	}

	public static void connectToServer(ServerConnectionInfo server) {
		ConnectScreen.connect(new MultiplayerScreen(null), MinecraftClient.getInstance(), ServerAddress.parse(server.address), server);
	}

	public static void openChat(String originText) {
		if (MinecraftClient.getInstance().currentScreen == null && MinecraftClient.getInstance().getOverlay() == null) {
			MinecraftClient.getInstance().openScreen(new ChatScreen(originText));
		}
	}

	public static boolean isSingleplayer() {
		return MinecraftClient.getInstance().isInSingleplayer();
	}

	public static boolean isInGame() {
		return MinecraftClient.getInstance().currentScreen == null;
	}

	public static File getRunDir() {
		return MinecraftClient.getInstance().runDirectory;
	}

	public static GameCamera getCamera() {
		return camera;
	}

	@Nullable
	public static GuiScreen getScreen() {
		if (MinecraftClient.getInstance().currentScreen != null) {
			if (MinecraftClient.getInstance().currentScreen instanceof GuiScreen) {
				return (GuiScreen) MinecraftClient.getInstance().currentScreen;
			}
		}
		return null;
	}

	public static PlayerPerspective getPerspective() {
		return MinecraftClient.getInstance().options.getPerspective() == Perspective.FIRST_PERSON ?
				PlayerPerspective.FIRST_PERSON : MinecraftClient.getInstance().options.getPerspective() == Perspective.THIRD_PERSON_BACK ?
				PlayerPerspective.THIRD_PERSON_BACK : PlayerPerspective.THIRD_PERSON_FRONT;
	}

	public static void shutdown() {
		MinecraftClient.getInstance().stop();
	}

	public static String getMinecraftVersion() {
		return SharedConstants.getGameVersion().getName();
	}

	public static int getMinecraftProtocolVersion() {
		return SharedConstants.getGameVersion().getProtocolVersion();
	}

	public static boolean isMouseOver() {
		return MinecraftClient.getInstance().crosshairTarget != null;
	}

	@Nullable
	public static ServerConnectionInfo getConnectedServer() {
		return connectedServer.get();
	}
	
	public static boolean isOnRealms() {
		return MinecraftClient.getInstance().isConnectedToRealms();
	}
	
	@Nullable
	public static ServerConnectionInfo getLastConnectedServer() {
		return lastConnectedServer;
	}

	@Nullable
	public static Entity getHitEntity() {
		return hitEntity.get();
	}

	@Nullable
	public static BlockSwingResult getHitBlock() {
		return hitBlock.get();
	}

	public static float getRenderPartialTicks() {
		return MinecraftClient.getInstance().getTickDelta();
	}

}
