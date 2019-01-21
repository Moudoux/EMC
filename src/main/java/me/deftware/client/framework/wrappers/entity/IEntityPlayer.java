package me.deftware.client.framework.wrappers.entity;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import me.deftware.client.framework.wrappers.item.IItemStack;
import me.deftware.client.framework.wrappers.math.IVec3d;
import me.deftware.client.framework.wrappers.world.IBlockPos;
import me.deftware.client.framework.wrappers.world.IEnumFacing;
import me.deftware.mixin.imp.IMixinEntity;
import me.deftware.mixin.imp.IMixinEntityPlayerSP;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockFlowingFluid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameType;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class IEntityPlayer {

	private static int ping = 0;

	public static void drawPlayer(int posX, int posY, int scale) {
		GuiInventory.drawEntityOnScreen(posX, posY, scale, 0, 0, Minecraft.getInstance().player);
	}

	public static boolean isAtEdge() {
		return Minecraft.getInstance().world.func_212388_b(Minecraft.getInstance().player,
				Minecraft.getInstance().player.getBoundingBox().offset(0, -0.5, 0).expand(-0.001, 0, -0.001))
				.count() == 0;
	}

	public static boolean processRightClickBlock(IBlockPos pos, IEnumFacing facing, IVec3d vec) {
		return Minecraft.getInstance().playerController.processRightClickBlock(Minecraft.getInstance().player,
				Minecraft.getInstance().world, pos.getPos(), IEnumFacing.getFacing(facing), vec.getVector(),
				EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS;
	}

	public static void doJump() {
		Minecraft.getInstance().player.jump();
	}

	public static IItemStack getHeldItem(boolean offset) {
		ItemStack stack = Minecraft.getInstance().player.inventory.getCurrentItem();
		if (offset) {
			stack = Minecraft.getInstance().player.getHeldItemOffhand();
		}
		if (stack == null) {
			return null;
		}
		return new IItemStack(stack);
	}

	public static float getStepHeight() {
		return Minecraft.getInstance().player.stepHeight;
	}

	public static void setStepHeight(float height) {
		Minecraft.getInstance().player.stepHeight = height;
	}

	public static IEntity getRidingEntity() {
		if (Minecraft.getInstance().player.getRidingEntity() != null) {
			return new IEntity(Minecraft.getInstance().player.getRidingEntity());
		}
		return null;
	}

	public static int getFoodLevel() {
		return Minecraft.getInstance().player.getFoodStats().getFoodLevel();
	}

	public static double getLastTickPosX() {
		return Minecraft.getInstance().player.lastTickPosX;
	}

	public static double getLastTickPosY() {
		return Minecraft.getInstance().player.lastTickPosY;
	}

	public static double getLastTickPosZ() {
		return Minecraft.getInstance().player.lastTickPosZ;
	}

	public static IEntity clonePlayer() {
		return new IEntity(new IEntityOtherPlayerMP());
	}

	public static boolean isAirBorne() {
		return Minecraft.getInstance().player.isAirBorne;
	}

	public static boolean getFlag(int flag) {
		return ((IMixinEntity) Minecraft.getInstance().player).getAFlag(flag);
	}

	public static void setSprinting(boolean state) {
		Minecraft.getInstance().player.setSprinting(state);
	}

	public static float getMoveStrafing() {
		return Minecraft.getInstance().player.moveStrafing;
	}

	public static float getMoveForward() {
		return Minecraft.getInstance().player.moveForward;
	}

	public static boolean isCollidedHorizontally() {
		return Minecraft.getInstance().player.collidedHorizontally;
	}

	public static boolean isRidingEntityInWater() {
		return Minecraft.getInstance().player.getRidingEntity().isInWater();
	}

	public static double getRidingEntityMotionY() {
		return Minecraft.getInstance().player.getRidingEntity().motionY;
	}

	public static double getRidingEntityMotionX() {
		return Minecraft.getInstance().player.getRidingEntity().motionX;
	}

	public static double getRidingEntityMotionZ() {
		return Minecraft.getInstance().player.getRidingEntity().motionZ;
	}

	public static void ridingEntityMotionY(double y) {
		Minecraft.getInstance().player.getRidingEntity().motionY = y;
	}

	public static void ridingEntityMotionX(double x) {
		Minecraft.getInstance().player.getRidingEntity().motionX = x;
	}

	public static void ridingEntityMotionZ(double z) {
		Minecraft.getInstance().player.getRidingEntity().motionZ = z;
	}

	public static void ridingEntityMotionTimesY(double y) {
		Minecraft.getInstance().player.getRidingEntity().motionY *= y;
	}

	public static void ridingEntityMotionTimesX(double x) {
		Minecraft.getInstance().player.getRidingEntity().motionX *= x;
	}

	public static boolean isRidingOnGround() {
		return Minecraft.getInstance().player.getRidingEntity().onGround;
	}

	public static void ridingEntityMotionTimesZ(double z) {
		Minecraft.getInstance().player.getRidingEntity().motionZ *= z;
	}

	public static void attackEntity(IEntity entity) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().playerController.attackEntity(Minecraft.getInstance().player, entity.getEntity());
		IEntityPlayer.swingArmClientSide();
	}

	public static boolean isCreative() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getInstance().player.isCreative();
	}

	public static void setPositionY(int y) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.setPosition(Minecraft.getInstance().player.posX,
				Minecraft.getInstance().player.posY + y, Minecraft.getInstance().player.posZ);
	}

	public static void setPosition(double x, double y, double z) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.setPosition(x, y, z);
	}

	public static void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.setPositionAndRotation(x, y, z, yaw, pitch);
	}

	public static void setJumpMovementFactor(float speed) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.jumpMovementFactor = speed;
	}

	public static void setJumpMovementFactorTimes(float speed) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.jumpMovementFactor *= speed;
	}

	public static void setNoClip(boolean state) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.noClip = state;
	}

	public static void setFalldistance(float distance) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.fallDistance = distance;
	}

	public static void setOnGround(boolean state) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.onGround = state;
	}

	public static double getMotionX() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.motionX;
	}

	public static double getMotionY() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.motionY;
	}

	public static double getMotionZ() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.motionZ;
	}

	public static void setMotionX(double x) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.motionX = x;
	}

	public static void setMotionY(double y) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.motionY = y;
	}

	public static void setMotionZ(double z) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.motionZ = z;
	}

	public static void setMotionTimesX(double x) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.motionX *= x;
	}

	public static void setMotionTimesY(double y) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.motionY *= y;
	}

	public static void setMotionTimesZ(double z) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.motionZ *= z;
	}

	public static void setMotionPlusX(double x) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.motionX += x;
	}

	public static void setMotionPlusY(double y) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.motionY += y;
	}

	public static void setMotionPlusZ(double z) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.motionZ += z;
	}

	public static void setMotionMinusX(double x) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.motionX -= x;
	}

	public static void setMotionMinusY(double y) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.motionY -= y;
	}

	public static void setMotionMinusZ(double z) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.motionZ -= z;
	}

	public static void respawnPlayer() {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.respawnPlayer();
	}

	public static void swingArmClientSide() {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.swingArm(EnumHand.MAIN_HAND);
	}

	public static float getSaturationLevel() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.getFoodStats().getSaturationLevel();
	}

	public static void swingArmPacket() {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
	}

	public static float getCooldown() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.getCooledAttackStrength(0);
	}

	public static void setRotationYaw(float yaw) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.rotationYaw = yaw;
	}

	public static void setRotationPitch(float pitch) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.rotationPitch = pitch;
	}

	public static float getRotationYaw() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.rotationYaw;
	}

	public static float getRotationPitch() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.rotationPitch;
	}

	public static double getPosX() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.posX;
	}

	public static double getPosY() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.posY;
	}

	public static double getPosZ() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.posZ;
	}

	public static double getEyeHeight() {
		return Minecraft.getInstance().player.getEyeHeight();
	}

	public static int getItemInUseMaxCount() {
		return Minecraft.getInstance().player.getItemInUseMaxCount();
	}

	public static double getPrevPosX() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.prevPosX;
	}

	public static double getPrevPosY() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.prevPosY;
	}

	public static double getPrevPosZ() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.prevPosZ;
	}

	public static float getHealth() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.getHealth();
	}

	public static float getFallDistance() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.fallDistance;
	}

	public static boolean hasPotionEffects() {
		if (!Minecraft.getInstance().player.getActivePotionEffects().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isSingleplayer() {
		if (IEntityPlayer.isNull()) {
			return true;
		}
		return Minecraft.getInstance().isSingleplayer();
	}

	public static String getDisplayX() {
		if (IEntityPlayer.isNull()) {
			return "0";
		}
		return String.format("%.3f",
				new Object[]{Double.valueOf(Minecraft.getInstance().getRenderViewEntity().posX)});
	}

	public static String getDisplayY() {
		if (IEntityPlayer.isNull()) {
			return "0";
		}
		return String.format("%.5f",
				new Object[]{Double.valueOf(Minecraft.getInstance().getRenderViewEntity().posY)});
	}

	public static String getDisplayZ() {
		if (IEntityPlayer.isNull()) {
			return "0";
		}
		return String.format("%.3f",
				new Object[]{Double.valueOf(Minecraft.getInstance().getRenderViewEntity().posZ)});
	}

	public static int getPing() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		Ordering<NetworkPlayerInfo> ENTRY_ORDERING = Ordering.from(new PlayerComparator());
		new Thread() {
			@Override
			public void run() {
				NetHandlerPlayClient nethandlerplayclient = Minecraft.getInstance().player.connection;
				List<NetworkPlayerInfo> list = ENTRY_ORDERING
						.<NetworkPlayerInfo>sortedCopy(nethandlerplayclient.getPlayerInfoMap());

				for (NetworkPlayerInfo networkplayerinfo : list) {
					String uuid = networkplayerinfo.getGameProfile().getId().toString();
					if (uuid.equals(Minecraft.getInstance().player.getUniqueID().toString())) {
						IEntityPlayer.ping = networkplayerinfo.getResponseTime();
					}
				}
			}
		}.start();
		return IEntityPlayer.ping;
	}

	/**
	 * Which dimension the player is in (-1 = the Nether, 0 = normal world)
	 *
	 * @return
	 */
	public static int getDimension() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getInstance().player.dimension.getId();
	}

	public static boolean isRowingBoat() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getInstance().player.isRowingBoat();
	}

	public static boolean isRiding() {
		return Minecraft.getInstance().player.isPassenger();
	}

	public static boolean isRidingHorse() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getInstance().player.isRidingHorse();
	}

	public static boolean isInLiquid() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getInstance().player.isInWater() || Minecraft.getInstance().player.isInLava();
	}

	public static void setFlying(boolean state) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.abilities.isFlying = state;
	}

	public static boolean isFlying() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getInstance().player.abilities.isFlying;
	}

	public static void setFlySpeed(float speed) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.abilities.setFlySpeed(speed);
	}

	public static float getFlySpeed() {
		if (IEntityPlayer.isNull()) {
			return 0F;
		}
		return Minecraft.getInstance().player.abilities.getFlySpeed();
	}

	public static void setWalkSpeed(float speed) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getInstance().player.abilities.setWalkSpeed(speed);
	}

	public static float getWalkSpeed() {
		if (IEntityPlayer.isNull()) {
			return 0F;
		}
		return Minecraft.getInstance().player.abilities.getWalkSpeed();
	}

	public static String getName() {
		if (IEntityPlayer.isNull()) {
			return "";
		}
		return Minecraft.getInstance().player.getGameProfile().getName();
	}

	public static boolean isOnGround() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getInstance().player.onGround;
	}

	public static boolean isOnLadder() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getInstance().player.isOnLadder();
	}

	public static boolean isNull() {
		if (Minecraft.getInstance().player == null) {
			return true;
		}
		return false;
	}

	public static boolean isHoldingItem(HandItem item) {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		if (item.equals(HandItem.ItemBow)) {
			return Minecraft.getInstance().player.getHeldItemMainhand().getItem() instanceof ItemBow
					|| Minecraft.getInstance().player.getHeldItemOffhand().getItem() instanceof ItemBow;
		}
		return false;
	}

	public enum HandItem {
		ItemBow
	}

	public static boolean isSneaking() {
		return Minecraft.getInstance().player.isSneaking();
	}

	public static boolean isInAir() {
		return Minecraft.getInstance().player.areEyesInFluid(new FluidTags.Wrapper(new ResourceLocation("air")));
	}

	public static boolean isTouchingLiquid() {
		Minecraft mc = Minecraft.getInstance();
		boolean inLiquid = false;
		int y = (int) mc.player.getBoundingBox().minY;
		for (int x = IEntityPlayer.floor_double(mc.player.getBoundingBox().minX); x < IEntityPlayer.floor_double(mc.player.getBoundingBox().maxX) + 1; x++) {
			for (int z = IEntityPlayer.floor_double(mc.player.getBoundingBox().minZ); z < IEntityPlayer.floor_double(mc.player.getBoundingBox().maxZ)
					+ 1; z++) {
				net.minecraft.block.Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
				if ((block != null) && (!(block instanceof BlockAir))) {
					if (!(block instanceof BlockFlowingFluid)) {
						return false;
					}
					inLiquid = true;
				}
			}
		}
		return inLiquid;
	}

	public static int floor_double(double value) {
		int i = (int) value;
		return value < i ? i - 1 : i;
	}

	public static void toggleSkinLayers() {
		Set<?> activeParts = Minecraft.getInstance().gameSettings.getModelParts();
		for (EnumPlayerModelParts part : EnumPlayerModelParts.values()) {
			Minecraft.getInstance().gameSettings.setModelPartEnabled(part, !activeParts.contains(part));
		}
	}

	public static IBlockPos getPos() {
		return new IBlockPos(IEntityPlayer.getPosX(), IEntityPlayer.getPosY(), IEntityPlayer.getPosZ());
	}

	public static void setHorseJumpPower(float f) {
		((IMixinEntityPlayerSP) Minecraft.getInstance().player).setHorseJumpPower(f);
	}

	static class PlayerComparator implements Comparator<NetworkPlayerInfo> {

		@Override
		public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
			ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
			ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
			return ComparisonChain.start().compareTrueFirst(p_compare_1_.getGameType() != GameType.SPECTATOR, p_compare_2_.getGameType() != GameType.SPECTATOR).compare(scoreplayerteam != null ? scoreplayerteam.getName() : "", scoreplayerteam1 != null ? scoreplayerteam1.getName() : "").compare(p_compare_1_.getGameProfile().getName(), p_compare_2_.getGameProfile().getName()).result();
		}

	}

}
