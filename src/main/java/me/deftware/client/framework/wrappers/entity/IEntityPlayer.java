package me.deftware.client.framework.wrappers.entity;

import me.deftware.client.framework.wrappers.item.IItemStack;
import me.deftware.client.framework.wrappers.math.IVec3d;
import me.deftware.client.framework.wrappers.world.IBlockPos;
import me.deftware.client.framework.wrappers.world.IEnumFacing;
import me.deftware.mixin.imp.IMixinEntity;
import me.deftware.mixin.imp.IMixinEntityPlayerSP;
import net.minecraft.block.AirBlock;
import net.minecraft.block.FluidBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.packet.HandSwingC2SPacket;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

import java.util.Collections;
import java.util.Set;

@SuppressWarnings("All")
public class IEntityPlayer {

    public static void drawPlayer(int posX, int posY, int scale) {
        InventoryScreen.drawEntity(posX, posY, scale, 0, 0, MinecraftClient.getInstance().player);
    }

    public static boolean isAtEdge() {
        // TODO: Is this right?
        return MinecraftClient.getInstance().world.getCollisionShapes(MinecraftClient.getInstance().player, MinecraftClient.getInstance().player.getBoundingBox().offset(0, -0.5, 0).expand(-0.001, 0, -0.001), Collections.emptySet()).count() == 0;
    }

    public static boolean processRightClickBlock(IBlockPos pos, IEnumFacing facing, IVec3d vec) {
        BlockHitResult customHitResult = new BlockHitResult(vec.getVector(), IEnumFacing.getFacing(facing), pos.getPos(), false);
        return MinecraftClient.getInstance().interactionManager.interactBlock(MinecraftClient.getInstance().player,
                MinecraftClient.getInstance().world, Hand.MAIN_HAND, customHitResult) == ActionResult.SUCCESS;
    }

    public static void doJump() {
        MinecraftClient.getInstance().player.jump();
    }

    public static IItemStack getHeldItem(boolean offset) {
        ItemStack stack = MinecraftClient.getInstance().player.inventory.getMainHandStack();
        if (offset) {
            stack = MinecraftClient.getInstance().player.getOffHandStack();
        }
        if (stack == null) {
            return null;
        }
        return new IItemStack(stack);
    }

    public static float getStepHeight() {
        return MinecraftClient.getInstance().player.stepHeight;
    }

    public static void setStepHeight(float height) {
        MinecraftClient.getInstance().player.stepHeight = height;
    }

    public static IEntity getRidingEntity() {
        if (MinecraftClient.getInstance().player.getVehicle() != null) {
            return new IEntity(MinecraftClient.getInstance().player.getVehicle());
        }
        return null;
    }

    public static int getFoodLevel() {
        return MinecraftClient.getInstance().player.getHungerManager().getFoodLevel();
    }

    public static double getLastTickPosX() {
        return MinecraftClient.getInstance().player.prevRenderX;
    }

    public static double getLastTickPosY() {
        return MinecraftClient.getInstance().player.prevRenderY;
    }

    public static double getLastTickPosZ() {
        return MinecraftClient.getInstance().player.prevRenderZ;
    }

    public static IEntity clonePlayer() {
        return new IEntity(new IEntityOtherPlayerMP());
    }

    public static boolean isAirBorne() {
        return MinecraftClient.getInstance().player.velocityDirty;
    }

    public static boolean getFlag(int flag) {
        return ((IMixinEntity) MinecraftClient.getInstance().player).getAFlag(flag);
    }

    public static void setSprinting(boolean state) {
        MinecraftClient.getInstance().player.setSprinting(state);
    }

    public static float getMoveStrafing() {
        return MinecraftClient.getInstance().player.sidewaysSpeed;
    }

    public static float getMoveForward() {
        return MinecraftClient.getInstance().player.upwardSpeed;
    }

    public static boolean isCollidedHorizontally() {
        return MinecraftClient.getInstance().player.horizontalCollision;
    }

    public static boolean isRidingEntityInWater() {
        return MinecraftClient.getInstance().player.getVehicle().isInsideWater();
    }

    public static double getRidingEntityMotionX() {
        return MinecraftClient.getInstance().player.getVehicle().getVelocity().x;
    }

    public static double getRidingEntityMotionY() {
        return MinecraftClient.getInstance().player.getVehicle().getVelocity().y;
    }

    public static double getRidingEntityMotionZ() {
        return MinecraftClient.getInstance().player.getVehicle().getVelocity().z;
    }

    public static void ridingEntityMotionY(double y) {
        MinecraftClient.getInstance().player.getVehicle().setVelocity(
                MinecraftClient.getInstance().player.getVehicle().getVelocity().x,
                y,
                MinecraftClient.getInstance().player.getVehicle().getVelocity().z
        );
    }

    public static void ridingEntityMotionX(double x) {
        MinecraftClient.getInstance().player.getVehicle().setVelocity(
                x,
                MinecraftClient.getInstance().player.getVehicle().getVelocity().y,
                MinecraftClient.getInstance().player.getVehicle().getVelocity().z
        );
    }

    public static void ridingEntityMotionZ(double z) {
        MinecraftClient.getInstance().player.getVehicle().setVelocity(
                MinecraftClient.getInstance().player.getVehicle().getVelocity().x,
                MinecraftClient.getInstance().player.getVehicle().getVelocity().y,
                z
        );
    }

    public static void ridingEntityMotionTimesY(double y) {
        MinecraftClient.getInstance().player.getVehicle().setVelocity(
                MinecraftClient.getInstance().player.getVehicle().getVelocity().multiply(1, y, 1)
        );
    }

    public static void ridingEntityMotionTimesX(double x) {
        MinecraftClient.getInstance().player.getVehicle().setVelocity(
                MinecraftClient.getInstance().player.getVehicle().getVelocity().multiply(x, 1, 1)
        );
    }

    public static void ridingEntityMotionTimesZ(double z) {
        MinecraftClient.getInstance().player.getVehicle().setVelocity(
                MinecraftClient.getInstance().player.getVehicle().getVelocity().multiply(1, 1, z)
        );
    }

    public static boolean isRidingOnGround() {
        return MinecraftClient.getInstance().player.getVehicle().onGround;
    }

    public static void attackEntity(IEntity entity) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().interactionManager.attackEntity(MinecraftClient.getInstance().player, entity.getEntity());
        IEntityPlayer.swingArmClientSide();
    }

    public static boolean isCreative() {
        if (IEntityPlayer.isNull()) {
            return false;
        }
        return MinecraftClient.getInstance().player.isCreative();
    }

    public static void setPositionY(int y) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setPosition(MinecraftClient.getInstance().player.x,
                MinecraftClient.getInstance().player.y + y, MinecraftClient.getInstance().player.z);
    }

    public static void setPosition(double x, double y, double z) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setPosition(x, y, z);
    }

    public static void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setPositionAndAngles(x, y, z, yaw, pitch);
    }

    public static void setJumpMovementFactor(float speed) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.flyingSpeed = speed;
    }

    public static void setJumpMovementFactorTimes(float speed) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.flyingSpeed *= speed;
    }

    public static void setNoClip(boolean state) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.noClip = state;
    }

    public static void setFalldistance(float distance) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.fallDistance = distance;
    }

    public static double getMotionX() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.getVelocity().x;
    }

    public static void setMotionX(double x) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(
                x,
                MinecraftClient.getInstance().player.getVelocity().y,
                MinecraftClient.getInstance().player.getVelocity().z
        );
    }

    public static double getMotionY() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.getVelocity().y;
    }

    public static void setMotionY(double y) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(
                MinecraftClient.getInstance().player.getVelocity().x,
                y,
                MinecraftClient.getInstance().player.getVelocity().z
        );
    }

    public static double getMotionZ() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.getVelocity().z;
    }

    public static void setMotionZ(double z) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(
                MinecraftClient.getInstance().player.getVelocity().x,
                MinecraftClient.getInstance().player.getVelocity().y,
                z
        );
    }

    public static void setMotionTimesX(double x) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(
                MinecraftClient.getInstance().player.getVelocity().multiply(x, 1, 1)
        );
    }

    public static void setMotionTimesY(double y) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(
                MinecraftClient.getInstance().player.getVelocity().multiply(1, y, 1)
        );
    }

    public static void setMotionTimesZ(double z) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(
                MinecraftClient.getInstance().player.getVelocity().multiply(1, 1, z)
        );
    }

    public static void setMotionPlusX(double x) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(
                MinecraftClient.getInstance().player.getVelocity().add(x, 0, 0)
        );
    }

    public static void setMotionPlusY(double y) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(
                MinecraftClient.getInstance().player.getVelocity().add(0, y, 0)
        );
    }

    public static void setMotionPlusZ(double z) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(
                MinecraftClient.getInstance().player.getVelocity().add(0, 0, z)
        );
    }

    public static void setMotionMinusX(double x) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(
                MinecraftClient.getInstance().player.getVelocity().subtract(x, 0, 0)
        );
    }

    public static void setMotionMinusY(double y) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(
                MinecraftClient.getInstance().player.getVelocity().subtract(0, y, 0)
        );
    }

    public static void setMotionMinusZ(double z) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.setVelocity(
                MinecraftClient.getInstance().player.getVelocity().subtract(0, 0, z)
        );
    }

    public static void respawnPlayer() {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.requestRespawn();
    }

    public static void swingArmClientSide() {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.swingHand(Hand.MAIN_HAND);
    }

    public static float getSaturationLevel() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.getHungerManager().getSaturationLevel();
    }

    public static void swingArmPacket() {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.networkHandler.sendPacket(new HandSwingC2SPacket(Hand.MAIN_HAND));
    }

    public static float getCooldown() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.getAttackCooldownProgress(0);
    }

    public static float getRotationYaw() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.yaw;
    }

    public static void setRotationYaw(float yaw) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.yaw = yaw;
    }

    public static float getRotationPitch() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.pitch;
    }

    public static void setRotationPitch(float pitch) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.pitch = pitch;
    }

    public static double getPosX() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.x;
    }

    public static double getPosY() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.y;
    }

    public static double getPosZ() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.z;
    }

    public static double getEyeHeight() {
        // TODO: Can we get the current pose?
        return MinecraftClient.getInstance().player.getEyeHeight(EntityPose.STANDING);
    }

    public static int getItemInUseMaxCount() {
        return MinecraftClient.getInstance().player.getItemUseTimeLeft();
    }

    public static double getPrevPosX() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.prevX;
    }

    public static double getPrevPosY() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.prevY;
    }

    public static double getPrevPosZ() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.prevZ;
    }

    public static float getHealth() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.getHealth();
    }

    public static float getFallDistance() {
        if (IEntityPlayer.isNull()) {
            return 0;
        }
        return MinecraftClient.getInstance().player.fallDistance;
    }

    public static boolean hasPotionEffects() {
        if (!MinecraftClient.getInstance().player.getStatusEffects().isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isSingleplayer() {
        if (IEntityPlayer.isNull()) {
            return true;
        }
        return MinecraftClient.getInstance().isInSingleplayer();
    }

    public static String getDisplayX() {
        if (IEntityPlayer.isNull()) {
            return "0";
        }
        return String.format("%.3f",
                new Object[]{Double.valueOf(MinecraftClient.getInstance().getCameraEntity().x)});
    }

    public static String getDisplayY() {
        if (IEntityPlayer.isNull()) {
            return "0";
        }
        return String.format("%.5f",
                new Object[]{Double.valueOf(MinecraftClient.getInstance().getCameraEntity().y)});
    }

    public static String getDisplayZ() {
        if (IEntityPlayer.isNull()) {
            return "0";
        }
        return String.format("%.3f",
                new Object[]{Double.valueOf(MinecraftClient.getInstance().getCameraEntity().z)});
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
        return MinecraftClient.getInstance().player.dimension.getRawId();
    }

    public static boolean isRowingBoat() {
        if (IEntityPlayer.isNull()) {
            return false;
        } else if (MinecraftClient.getInstance().player.getVehicle() instanceof BoatEntity) {
            return true;
        }
        return false;
    }

    public static boolean isRiding() {
        return MinecraftClient.getInstance().player.hasVehicle();
    }

    public static boolean isRidingHorse() {
        if (IEntityPlayer.isNull()) {
            return false;
        }
        return MinecraftClient.getInstance().player.hasVehicle() && MinecraftClient.getInstance().player.getVehicle() instanceof HorseEntity;
    }

    public static boolean isInLiquid() {
        if (IEntityPlayer.isNull()) {
            return false;
        }
        return MinecraftClient.getInstance().player.isInsideWater() || MinecraftClient.getInstance().player.isInLava();
    }

    public static boolean isFlying() {
        if (IEntityPlayer.isNull()) {
            return false;
        }
        return MinecraftClient.getInstance().player.abilities.flying;
    }

    public static void setFlying(boolean state) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.abilities.flying = state;
    }

    public static float getFlySpeed() {
        if (IEntityPlayer.isNull()) {
            return 0F;
        }
        return MinecraftClient.getInstance().player.abilities.getFlySpeed();
    }

    public static void setFlySpeed(float speed) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.abilities.setFlySpeed(speed);
    }

    public static float getWalkSpeed() {
        if (IEntityPlayer.isNull()) {
            return 0F;
        }
        return MinecraftClient.getInstance().player.abilities.getWalkSpeed();
    }

    public static void setWalkSpeed(float speed) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.abilities.setWalkSpeed(speed);
    }

    public static String getName() {
        if (IEntityPlayer.isNull()) {
            return "";
        }
        return MinecraftClient.getInstance().player.getGameProfile().getName();
    }

    public static boolean isOnGround() {
        if (IEntityPlayer.isNull()) {
            return false;
        }
        return MinecraftClient.getInstance().player.onGround;
    }

    public static void setOnGround(boolean state) {
        if (IEntityPlayer.isNull()) {
            return;
        }
        MinecraftClient.getInstance().player.onGround = state;
    }

    public static boolean isOnLadder() {
        if (IEntityPlayer.isNull()) {
            return false;
        }
        return MinecraftClient.getInstance().player.isClimbing();
    }

    public static boolean isNull() {
        if (MinecraftClient.getInstance().player == null) {
            return true;
        }
        return false;
    }

    public static boolean isHoldingItem(HandItem item) {
        if (IEntityPlayer.isNull()) {
            return false;
        }
        if (item.equals(HandItem.ItemBow)) {
            return MinecraftClient.getInstance().player.getMainHandStack().getItem() instanceof BowItem
                    || MinecraftClient.getInstance().player.getOffHandStack().getItem() instanceof BowItem;
        }
        return false;
    }

    public static boolean isSneaking() {
        return MinecraftClient.getInstance().player.isSneaking();
    }

    public static boolean isInAir() {
        return MinecraftClient.getInstance().player.isInFluid(new FluidTags.CachingTag(new Identifier("air")));
    }

    public static boolean isTouchingLiquid() {
        MinecraftClient mc = MinecraftClient.getInstance();
        boolean inLiquid = false;
        int y = (int) mc.player.getBoundingBox().minY;
        for (int x = IEntityPlayer.floor_double(mc.player.getBoundingBox().minX); x < IEntityPlayer.floor_double(mc.player.getBoundingBox().maxX) + 1; x++) {
            for (int z = IEntityPlayer.floor_double(mc.player.getBoundingBox().minZ); z < IEntityPlayer.floor_double(mc.player.getBoundingBox().maxZ)
                    + 1; z++) {
                net.minecraft.block.Block block = mc.world.getBlockState(new BlockPos(x, y, z)).getBlock();
                if ((block != null) && (!(block instanceof AirBlock))) {
                    if (!(block instanceof FluidBlock)) {
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
        Set<?> activeParts = MinecraftClient.getInstance().options.getEnabledPlayerModelParts();
        for (PlayerModelPart part : PlayerModelPart.values()) {
            MinecraftClient.getInstance().options.setPlayerModelPart(part, !activeParts.contains(part));
        }
    }

    public static IBlockPos getPos() {
        return new IBlockPos(IEntityPlayer.getPosX(), IEntityPlayer.getPosY(), IEntityPlayer.getPosZ());
    }

    public static void setHorseJumpPower(float f) {
        ((IMixinEntityPlayerSP) MinecraftClient.getInstance().player).setHorseJumpPower(f);
    }

    public enum HandItem {
        ItemBow
    }

}
