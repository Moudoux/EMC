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
		GuiInventory.drawEntityOnScreen(posX, posY, scale, 0, 0, Minecraft.getMinecraft().player);
	}

	public static boolean isAtEdge() {
		return Minecraft.getMinecraft().world.getCollisionBoxes(Minecraft.getMinecraft().player,
				Minecraft.getMinecraft().player.getEntityBoundingBox().offset(0, -0.5, 0).expand(-0.001, 0, -0.001))
				.isEmpty();
	}

	public static boolean processRightClickBlock(IBlockPos pos, IEnumFacing facing, IVec3d vec) {
		return Minecraft.getMinecraft().playerController.processRightClickBlock(Minecraft.getMinecraft().player,
				Minecraft.getMinecraft().world, pos.getPos(), IEnumFacing.getFacing(facing), vec.getVector(),
				EnumHand.MAIN_HAND) == EnumActionResult.SUCCESS;
	}

	public static void doJump() {
		Minecraft.getMinecraft().player.jump();
	}

	public static IItemStack getHeldItem(boolean offset) {
		ItemStack stack = Minecraft.getMinecraft().player.inventory.getCurrentItem();
		if (offset) {
			stack = Minecraft.getMinecraft().player.getHeldItemOffhand();
		}
		if (stack == null) {
			return null;
		}
		return new IItemStack(stack);
	}

	public static float getStepHeight() {
		return Minecraft.getMinecraft().player.stepHeight;
	}

	public static void setStepHeight(float height) {
		Minecraft.getMinecraft().player.stepHeight = height;
	}

	public static IEntity getRidingEntity() {
		if (Minecraft.getMinecraft().player.getRidingEntity() != null) {
			return new IEntity(Minecraft.getMinecraft().player.getRidingEntity());
		}
		return null;
	}

	public static int getFoodLevel() {
		return Minecraft.getMinecraft().player.getFoodStats().getFoodLevel();
	}

	public static double getLastTickPosX() {
		return Minecraft.getMinecraft().player.lastTickPosX;
	}

	public static double getLastTickPosY() {
		return Minecraft.getMinecraft().player.lastTickPosY;
	}

	public static double getLastTickPosZ() {
		return Minecraft.getMinecraft().player.lastTickPosZ;
	}

	public static IEntity clonePlayer() {
		return new IEntity(new IEntityOtherPlayerMP());
	}

	public static boolean isAirBorne() {
		return Minecraft.getMinecraft().player.isAirBorne;
	}

	public static boolean getFlag(int flag) {
		return ((IMixinEntity) Minecraft.getMinecraft().player).getAFlag(flag);
	}

	public static void setSprinting(boolean state) {
		Minecraft.getMinecraft().player.setSprinting(state);
	}

	public static float getMoveStrafing() {
		return Minecraft.getMinecraft().player.moveStrafing;
	}

	public static float getMoveForward() {
		return Minecraft.getMinecraft().player.moveForward;
	}

	public static boolean isCollidedHorizontally() {
		return Minecraft.getMinecraft().player.collidedHorizontally;
	}

	public static boolean isRidingEntityInWater() {
		return Minecraft.getMinecraft().player.getRidingEntity().isInWater();
	}

	public static double getRidingEntityMotionY() {
		return Minecraft.getMinecraft().player.getRidingEntity().motionY;
	}

	public static double getRidingEntityMotionX() {
		return Minecraft.getMinecraft().player.getRidingEntity().motionX;
	}

	public static double getRidingEntityMotionZ() {
		return Minecraft.getMinecraft().player.getRidingEntity().motionZ;
	}

	public static void ridingEntityMotionY(double y) {
		Minecraft.getMinecraft().player.getRidingEntity().motionY = y;
	}

	public static void ridingEntityMotionX(double x) {
		Minecraft.getMinecraft().player.getRidingEntity().motionX = x;
	}

	public static void ridingEntityMotionZ(double z) {
		Minecraft.getMinecraft().player.getRidingEntity().motionZ = z;
	}

	public static void ridingEntityMotionTimesY(double y) {
		Minecraft.getMinecraft().player.getRidingEntity().motionY *= y;
	}

	public static void ridingEntityMotionTimesX(double x) {
		Minecraft.getMinecraft().player.getRidingEntity().motionX *= x;
	}

	public static boolean isRidingOnGround() {
		return Minecraft.getMinecraft().player.getRidingEntity().onGround;
	}

	public static void ridingEntityMotionTimesZ(double z) {
		Minecraft.getMinecraft().player.getRidingEntity().motionZ *= z;
	}

	public static void attackEntity(IEntity entity) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().player, entity.getEntity());
		IEntityPlayer.swingArmClientSide();
	}

	public static boolean isCreative() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.isCreative();
	}

	public static void setPositionY(int y) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.setPosition(Minecraft.getMinecraft().player.posX,
				Minecraft.getMinecraft().player.posY + y, Minecraft.getMinecraft().player.posZ);
	}

	public static void setPosition(double x, double y, double z) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.setPosition(x, y, z);
	}

	public static void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.setPositionAndRotation(x, y, z, yaw, pitch);
	}

	public static void setJumpMovementFactor(float speed) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.jumpMovementFactor = speed;
	}

	public static void setJumpMovementFactorTimes(float speed) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.jumpMovementFactor *= speed;
	}

	public static void setNoClip(boolean state) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.noClip = state;
	}

	public static void setFalldistance(float distance) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.fallDistance = distance;
	}

	public static void setOnGround(boolean state) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.onGround = state;
	}

	public static double getMotionX() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.motionX;
	}

	public static double getMotionY() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.motionY;
	}

	public static double getMotionZ() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.motionZ;
	}

	public static void setMotionX(double x) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.motionX = x;
	}

	public static void setMotionY(double y) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.motionY = y;
	}

	public static void setMotionZ(double z) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.motionZ = z;
	}

	public static void setMotionTimesX(double x) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.motionX *= x;
	}

	public static void setMotionTimesY(double y) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.motionY *= y;
	}

	public static void setMotionTimesZ(double z) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.motionZ *= z;
	}

	public static void setMotionPlusX(double x) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.motionX += x;
	}

	public static void setMotionPlusY(double y) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.motionY += y;
	}

	public static void setMotionPlusZ(double z) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.motionZ += z;
	}

	public static void setMotionMinusX(double x) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.motionX -= x;
	}

	public static void setMotionMinusY(double y) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.motionY -= y;
	}

	public static void setMotionMinusZ(double z) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.motionZ -= z;
	}

	public static void respawnPlayer() {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.respawnPlayer();
	}

	public static void swingArmClientSide() {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
	}

	public static float getSaturationLevel() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.getFoodStats().getSaturationLevel();
	}

	public static void swingArmPacket() {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.connection.sendPacket(new CPacketAnimation(EnumHand.MAIN_HAND));
	}

	public static float getCooldown() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.getCooledAttackStrength(0);
	}

	public static void setRotationYaw(float yaw) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.rotationYaw = yaw;
	}

	public static void setRotationPitch(float pitch) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.rotationPitch = pitch;
	}

	public static float getRotationYaw() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.rotationYaw;
	}

	public static float getRotationPitch() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.rotationPitch;
	}

	public static double getPosX() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.posX;
	}

	public static double getPosY() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.posY;
	}

	public static double getPosZ() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.posZ;
	}

	public static double getEyeHeight() {
		return Minecraft.getMinecraft().player.getEyeHeight();
	}

	public static int getItemInUseMaxCount() {
		return Minecraft.getMinecraft().player.getItemInUseMaxCount();
	}

	public static double getPrevPosX() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.prevPosX;
	}

	public static double getPrevPosY() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.prevPosY;
	}

	public static double getPrevPosZ() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.prevPosZ;
	}

	public static float getHealth() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.getHealth();
	}

	public static float getFallDistance() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		return Minecraft.getMinecraft().player.fallDistance;
	}

	public static boolean hasPotionEffects() {
		if (!Minecraft.getMinecraft().player.getActivePotionEffects().isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isSingleplayer() {
		if (IEntityPlayer.isNull()) {
			return true;
		}
		return Minecraft.getMinecraft().isSingleplayer();
	}

	public static String getDisplayX() {
		if (IEntityPlayer.isNull()) {
			return "0";
		}
		return String.format("%.3f",
				new Object[]{Double.valueOf(Minecraft.getMinecraft().getRenderViewEntity().posX)});
	}

	public static String getDisplayY() {
		if (IEntityPlayer.isNull()) {
			return "0";
		}
		return String.format("%.5f",
				new Object[]{Double.valueOf(Minecraft.getMinecraft().getRenderViewEntity().posY)});
	}

	public static String getDisplayZ() {
		if (IEntityPlayer.isNull()) {
			return "0";
		}
		return String.format("%.3f",
				new Object[]{Double.valueOf(Minecraft.getMinecraft().getRenderViewEntity().posZ)});
	}

	public static int getPing() {
		if (IEntityPlayer.isNull()) {
			return 0;
		}
		Ordering<NetworkPlayerInfo> ENTRY_ORDERING = Ordering.from(new PlayerComparator());
		new Thread() {
			@Override
			public void run() {
				NetHandlerPlayClient nethandlerplayclient = Minecraft.getMinecraft().player.connection;
				List<NetworkPlayerInfo> list = ENTRY_ORDERING
						.<NetworkPlayerInfo>sortedCopy(nethandlerplayclient.getPlayerInfoMap());

				for (NetworkPlayerInfo networkplayerinfo : list) {
					String uuid = networkplayerinfo.getGameProfile().getId().toString();
					if (uuid.equals(Minecraft.getMinecraft().player.getUniqueID().toString())) {
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
		return Minecraft.getMinecraft().player.dimension;
	}

	public static boolean isRowingBoat() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.isRowingBoat();
	}

	public static boolean isRiding() {
		return Minecraft.getMinecraft().player.isRiding();
	}

	public static boolean isRidingHorse() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.isRidingHorse();
	}

	public static boolean isInLiquid() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.isInWater() || Minecraft.getMinecraft().player.isInLava();
	}

	public static void setFlying(boolean state) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.capabilities.isFlying = state;
	}

	public static boolean isFlying() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.capabilities.isFlying;
	}

	public static void setFlySpeed(float speed) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.capabilities.func_195931_a(speed);
	}

	public static float getFlySpeed() {
		if (IEntityPlayer.isNull()) {
			return 0F;
		}
		return Minecraft.getMinecraft().player.capabilities.getFlySpeed();
	}

	public static void setWalkSpeed(float speed) {
		if (IEntityPlayer.isNull()) {
			return;
		}
		Minecraft.getMinecraft().player.capabilities.setPlayerWalkSpeed(speed);
	}

	public static float getWalkSpeed() {
		if (IEntityPlayer.isNull()) {
			return 0F;
		}
		return Minecraft.getMinecraft().player.capabilities.getWalkSpeed();
	}

	public static String getName() {
		if (IEntityPlayer.isNull()) {
			return "";
		}
		return Minecraft.getMinecraft().player.getGameProfile().getName();
	}

	public static boolean isOnGround() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.onGround;
	}

	public static boolean isOnLadder() {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		return Minecraft.getMinecraft().player.isOnLadder();
	}

	public static boolean isNull() {
		if (Minecraft.getMinecraft().player == null) {
			return true;
		}
		return false;
	}

	public static boolean isHoldingItem(HandItem item) {
		if (IEntityPlayer.isNull()) {
			return false;
		}
		if (item.equals(HandItem.ItemBow)) {
			return Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ItemBow
					|| Minecraft.getMinecraft().player.getHeldItemOffhand().getItem() instanceof ItemBow;
		}
		return false;
	}

	public enum HandItem {
		ItemBow
	}

	public static boolean isSneaking() {
		return Minecraft.getMinecraft().player.isSneaking();
	}

	public static boolean isInAir() {
		return Minecraft.getMinecraft().player.isInFluid(new FluidTags.Wrapper(new ResourceLocation("air")));
	}

	public static boolean isTouchingLiquid() {
		Minecraft mc = Minecraft.getMinecraft();
		boolean inLiquid = false;
		int y = (int) mc.player.getEntityBoundingBox().minY;
		for (int x = IEntityPlayer.floor_double(mc.player.getEntityBoundingBox().minX); x < IEntityPlayer.floor_double(mc.player.getEntityBoundingBox().maxX) + 1; x++) {
			for (int z = IEntityPlayer.floor_double(mc.player.getEntityBoundingBox().minZ); z < IEntityPlayer.floor_double(mc.player.getEntityBoundingBox().maxZ)
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
		Set<?> activeParts = Minecraft.getMinecraft().gameSettings.getModelParts();
		for (EnumPlayerModelParts part : EnumPlayerModelParts.values()) {
			Minecraft.getMinecraft().gameSettings.setModelPartEnabled(part, !activeParts.contains(part));
		}
	}

	public static IBlockPos getPos() {
		return new IBlockPos(IEntityPlayer.getPosX(), IEntityPlayer.getPosY(), IEntityPlayer.getPosZ());
	}

	public static void setHorseJumpPower(float f) {
		((IMixinEntityPlayerSP) Minecraft.getMinecraft().player).setHorseJumpPower(f);
	}

	static class PlayerComparator implements Comparator<NetworkPlayerInfo> {
		private PlayerComparator() {
		}

		@Override
		public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
			ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
			ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
			return ComparisonChain.start().compareTrueFirst(p_compare_1_.getGameType() != GameType.SPECTATOR, p_compare_2_.getGameType() != GameType.SPECTATOR).compare(scoreplayerteam != null ? scoreplayerteam.getName() : "", scoreplayerteam1 != null ? scoreplayerteam1.getName() : "").compare(p_compare_1_.getGameProfile().getName(), p_compare_2_.getGameProfile().getName()).result();
		}
	}

}
