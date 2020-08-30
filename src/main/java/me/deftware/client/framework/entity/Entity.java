package me.deftware.client.framework.entity;

import com.google.common.collect.Iterables;
import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.conversion.ComparedConversion;
import me.deftware.client.framework.conversion.ConvertedList;
import me.deftware.client.framework.entity.types.EntityPlayer;
import me.deftware.client.framework.entity.types.animals.HorseEntity;
import me.deftware.client.framework.entity.types.animals.MobEntity;
import me.deftware.client.framework.entity.types.animals.WaterEntity;
import me.deftware.client.framework.entity.types.animals.WolfEntity;
import me.deftware.client.framework.entity.types.objects.BoatEntity;
import me.deftware.client.framework.entity.types.objects.ItemEntity;
import me.deftware.client.framework.item.ItemStack;
import me.deftware.client.framework.math.box.BoundingBox;
import me.deftware.client.framework.math.position.BlockPosition;
import me.deftware.client.framework.math.position.ChunkBlockPosition;
import me.deftware.client.framework.math.vector.Vector3d;
import me.deftware.client.framework.nbt.NbtCompound;
import me.deftware.mixin.imp.IMixinAbstractClientPlayer;
import me.deftware.mixin.imp.IMixinEntity;
import me.deftware.mixin.imp.IMixinNetworkPlayerInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.List;
import java.util.Objects;

/**
 * @author Deftware
 */
public class Entity {

	protected final ConvertedList<ItemStack, net.minecraft.item.ItemStack> armourItems;
	protected final ConvertedList<ItemStack, net.minecraft.item.ItemStack> heldItems;
	protected final ComparedConversion<net.minecraft.entity.Entity, Entity> vehicle;
	protected final net.minecraft.entity.Entity entity;
	protected final BlockPosition blockPosition;
	protected final BoundingBox boundingBox;

	public static Entity newInstance(net.minecraft.entity.Entity entity) {
		if (entity instanceof PlayerEntity) {
			return new EntityPlayer((PlayerEntity) entity);
		} else if (entity instanceof net.minecraft.entity.passive.HorseEntity) {
			return new HorseEntity(entity);
		} else if (entity instanceof net.minecraft.entity.vehicle.BoatEntity) {
			return new BoatEntity(entity);
		} else if (entity instanceof net.minecraft.entity.passive.WolfEntity) {
			return new WolfEntity(entity);
		} else if (entity instanceof net.minecraft.entity.mob.WaterCreatureEntity) {
			return new WaterEntity(entity);
		} else if (entity instanceof net.minecraft.entity.mob.MobEntity) {
			return new MobEntity(entity);
		} else if (entity instanceof net.minecraft.entity.ItemEntity) {
			return new ItemEntity(entity);
		} else if (entity instanceof net.minecraft.entity.LivingEntity) {
			return new me.deftware.client.framework.entity.types.LivingEntity(entity);
		}
		return new Entity(entity);
	}

	protected Entity(net.minecraft.entity.Entity entity) {
		this.entity = entity;
		this.boundingBox = new BoundingBox(entity);
		this.blockPosition = new BlockPosition(entity);
		this.vehicle = new ComparedConversion<>(entity::getVehicle, Entity::newInstance);
		this.heldItems = new ConvertedList<>(entity::getItemsHand, pair ->
				pair.getLeft().getMinecraftItemStack() == Iterables.get(entity.getItemsHand(), pair.getRight())
				, ItemStack::new);
		this.armourItems = new ConvertedList<>(entity::getArmorItems, pair ->
				pair.getLeft().getMinecraftItemStack() == Iterables.get(entity.getArmorItems(), pair.getRight())
				, ItemStack::new);
	}

	public BlockPosition getBlockPosition() {
		return blockPosition;
	}

	public net.minecraft.entity.Entity getMinecraftEntity() {
		return entity;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public boolean isSpectating() {
		return entity.isSpectator();
	}

	public boolean isRiding() {
		return entity.hasVehicle();
	}

	public boolean isAirBorne() {
		return entity.velocityDirty; // TODO
	}

	public float getFallDistance() {
		return entity.fallDistance;
	}

	public Entity getVehicle() {
		return vehicle.get();
	}

	public boolean isTouchingWater() {
		return entity.isTouchingWater();
	}

	public me.deftware.client.framework.item.ItemStack getEntityHeldItem(boolean offhand) {
		List<me.deftware.client.framework.item.ItemStack> stackList = heldItems.poll();
		if (!stackList.isEmpty()) {
			if (!offhand) return stackList.get(0);
			else if (stackList.size() > 1) return stackList.get(1);
		}
		return me.deftware.client.framework.item.ItemStack.EMPTY;
	}

	public List<ItemStack> getArmourInventory() {
		return armourItems.poll();
	}

	public void setInPortal(boolean inPortal) {
		((IMixinEntity) entity).setInPortal(inPortal);
	}

	public void reloadSkin() {
		if (entity instanceof AbstractClientPlayerEntity) {
			AbstractClientPlayerEntity abstractEntity = (AbstractClientPlayerEntity) entity;
			if (abstractEntity.canRenderCapeTexture()) {
				((IMixinNetworkPlayerInfo) ((IMixinAbstractClientPlayer) abstractEntity).getPlayerNetworkInfo()).reloadTextures();
			}
		}
	}

	public boolean isCollidedHorizontally() {
		return entity.horizontalCollision;
	}

	public boolean isCollidedVertically() {
		return entity.verticalCollision;
	}

	public int getResponseTime() {
		if (MinecraftClient.getInstance().getNetworkHandler() != null) {
			ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
			if (networkHandler.getPlayerListEntry(entity.getUuid()) != null) {
				return Objects.requireNonNull(networkHandler.getPlayerListEntry(entity.getUuid())).getLatency();
			}
		}
		return -1;
	}

	public boolean hasNbt() {
		return entity.toTag(new CompoundTag()) != null && !entity.toTag(new CompoundTag()).isEmpty();
	}

	public NbtCompound getNbt() {
		return new NbtCompound(entity.toTag(new CompoundTag()));
	}

	public int getTicksExisted() {
		return entity.age;
	}

	public boolean isSneaking() {
		return entity.isSneaking();
	}

	public boolean isInLiquid() {
		return entity.isTouchingWater() || entity.isInLava();
	}

	public boolean isSelf() {
		return entity == MinecraftClient.getInstance().player;
	}

	public int getEntityId() {
		return entity.getEntityId();
	}

	public float getHeight() {
		return entity.getHeight();
	}

	public void setGlowing(boolean state) {
		entity.setGlowing(state);
	}

	public boolean isOnGround() {
		return entity.isOnGround();
	}

	public void setOnGround(boolean flag) {
		entity.setOnGround(flag);
	}

	public boolean isOnFire() {
		return entity.isOnFire();
	}

	public void setOnFire(int seconds) {
		entity.setOnFireFor(seconds);
	}

	public float getStepHeight() {
		return entity.stepHeight;
	}

	public void setStepHeight(float height) {
		entity.stepHeight = height;
	}

	public boolean isWithinChunk(ChunkBlockPosition chunkPos) {
		return getPosX() >= chunkPos.getStartX() && getPosX() <= chunkPos.getEndX() && getPosZ() >= chunkPos.getStartZ() && getPosZ() <= chunkPos.getEndZ();
	}

	public boolean isHostile() {
		if (entity instanceof ChickenEntity) {
			return ((ChickenEntity) entity).jockey;
		}
		return entity instanceof HostileEntity;
	}

	public boolean isAlive() {
		return entity.isAlive();
	}

	public boolean instanceOf(EntityType type) {
		return EntityType.isInstance(this, type);
	}

	public boolean isInvisible() {
		return entity.isInvisible();
	}

	public double getEyeHeight() {
		return entity.getEyeHeight(entity.getPose());
	}

	public double getStandingEyeHeight() {
		return entity.getEyeHeight(EntityPose.STANDING);
	}

	public boolean canBeSeenBy(EntityPlayer entity) {
		return !this.entity.isInvisibleTo(entity.getMinecraftEntity());
	}

	public float distanceToEntity(Entity entity) {
		return this.entity.distanceTo(entity.getMinecraftEntity());
	}

	public ChatMessage getName() {
		return new ChatMessage().fromText(entity.getDisplayName());
	}

	public String getEntityTypeName() {
		return new ChatMessage().fromText(entity.getType().getName()).toString(false);
	}

	public void setNoClip(boolean state) {
		entity.noClip = state;
	}

	public void setFallDistance(float distance) {
		entity.fallDistance = distance;
	}

	public String getEntityName() {
		return entity.getEntityName();
	}

	public double getLastTickPosX() {
		return entity.lastRenderX;
	}

	public double getLastTickPosY() {
		return entity.lastRenderY;
	}

	public double getLastTickPosZ() {
		return entity.lastRenderZ;
	}

	public double getPosX() {
		return entity.getX();
	}

	public double getPosY() {
		return entity.getY();
	}

	public double getPosZ() {
		return entity.getZ();
	}

	public double getPrevPosX() {
		return entity.prevX;
	}

	public double getPrevPosY() {
		return entity.prevY;
	}

	public double getPrevPosZ() {
		return entity.prevZ;
	}

	public float getRotationYaw() {
		return entity.yaw;
	}

	public float getRotationPitch() {
		return entity.pitch;
	}

	public void setRotationYaw(float yaw) {
		entity.yaw = yaw;
	}

	public void setRotationPitch(float pitch) {
		entity.pitch = pitch;
	}

	public void setPosition(double x, double y, double z) {
		entity.updatePosition(x, y, z);
	}

	public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
		entity.refreshPositionAndAngles(x, y, z, yaw, pitch);
	}

	public Vector3d getEyesPos() {
		return new Vector3d(entity.getX(), entity.getY() + entity.getEyeHeight(entity.getPose()), entity.getZ());
	}

	public boolean getFlag(int id) {
		return ((IMixinEntity) entity).getAFlag(id);
	}

	public void setVelocity(double x, double y, double z) {
		entity.setVelocity(x, y, z);
	}

	public void setVelocity(Vector3d vector3d) {
		entity.setVelocity(vector3d.getMinecraftVector());
	}

	public Vector3d getVelocity() {
		return new Vector3d(entity.getVelocity());
	}

}
