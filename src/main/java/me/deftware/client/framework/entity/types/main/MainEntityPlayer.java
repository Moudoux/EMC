package me.deftware.client.framework.entity.types.main;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.math.vector.Vector3d;
import me.deftware.client.framework.world.EnumFacing;
import me.deftware.mixin.imp.IMixinEntityPlayerSP;
import me.deftware.mixin.imp.IMixinEntityRenderer;
import me.deftware.mixin.imp.IMixinMinecraft;
import me.deftware.mixin.imp.IMixinPlayerControllerMP;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Objects;
import java.util.Set;

/**
 * The main Minecraft player
 *
 * @author Deftware
 */
public class MainEntityPlayer extends RotationLogic {

	public MainEntityPlayer(PlayerEntity entity) {
		super(entity);
	}

	@Override
	public ClientPlayerEntity getMinecraftEntity() {
		return (ClientPlayerEntity) entity;
	}

	public boolean processRightClickBlock(BlockPosition pos, EnumFacing facing, Vector3d vector3d) {
		BlockHitResult customHitResult = new BlockHitResult(vector3d.getMinecraftVector(), facing.getFacing(), pos.getMinecraftBlockPos(), false);
		return Objects.requireNonNull(MinecraftClient.getInstance().interactionManager).interactBlock(MinecraftClient.getInstance().player,
				MinecraftClient.getInstance().world, Hand.MAIN_HAND, customHitResult) == ActionResult.SUCCESS;
	}

	public void swapHands() {
		Objects.requireNonNull(MinecraftClient.getInstance().player).networkHandler.sendPacket(new PlayerActionC2SPacket(
				PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
	}

	public void setRightClickDelayTimer(int delay) {
		((IMixinMinecraft) MinecraftClient.getInstance()).setRightClickDelayTimer(delay);
	}

	public void processRightClick(boolean offhand) {
		Objects.requireNonNull(MinecraftClient.getInstance().interactionManager).interactItem(MinecraftClient.getInstance().player, MinecraftClient.getInstance().world, offhand ? Hand.OFF_HAND : Hand.MAIN_HAND);
	}

	public void resetBlockRemoving() {
		Objects.requireNonNull(MinecraftClient.getInstance().interactionManager).cancelBlockBreaking();
	}

	public void setPlayerHittingBlock(boolean state) {
		((IMixinPlayerControllerMP) Objects.requireNonNull(MinecraftClient.getInstance().interactionManager)).setPlayerHittingBlock(state);
	}

	public float getPlayerFovMultiplier() {
		return ((IMixinEntityRenderer) MinecraftClient.getInstance().gameRenderer).getFovMultiplier();
	}

	public void updatePlayerFovMultiplier(float newValue) {
		((IMixinEntityRenderer) MinecraftClient.getInstance().gameRenderer).updateFovMultiplier(newValue);
	}

	public void swingArmClientSide() {
		getMinecraftEntity().swingHand(Hand.MAIN_HAND);
	}

	public void attackEntity(Entity entity) {
		Objects.requireNonNull(MinecraftClient.getInstance().interactionManager)
				.attackEntity(getMinecraftEntity(), entity.getMinecraftEntity());
		swingArmClientSide();
	}

	public void setHorseJumpPower(float f) {
		Objects.requireNonNull(((IMixinEntityPlayerSP) MinecraftClient.getInstance().player))
				.setHorseJumpPower(f);
	}

	private Input getInput() {
		return getMinecraftEntity().input;
	}

	public double getForward() {
		return getInput().movementForward;
	}

	public double getStrafe() {
		return getInput().movementSideways;
	}

	public void toggleSkinLayers() {
		Set<?> activeParts = MinecraftClient.getInstance().options.getEnabledPlayerModelParts();
		for (PlayerModelPart part : PlayerModelPart.values()) {
			MinecraftClient.getInstance().options.setPlayerModelPart(part, !activeParts.contains(part));
		}
	}

	public void closeHandledScreen() {
		getMinecraftEntity().closeHandledScreen();
	}

}
