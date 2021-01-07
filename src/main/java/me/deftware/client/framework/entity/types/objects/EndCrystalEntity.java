package me.deftware.client.framework.entity.types.objects;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.math.position.BlockPosition;
import net.minecraft.world.explosion.Explosion;

public class EndCrystalEntity extends Entity {

	public EndCrystalEntity(net.minecraft.entity.Entity entity) {
		super(entity);
	}

	public float getEntityDamage(Entity entity) {
		return Explosion.getExposure(this.entity.getPos(), entity.getMinecraftEntity());
	}
	
	public static float getExplosionExposure(BlockPosition pos, Entity entity) {
		return Explosion.getExposure(pos.getVector().getMinecraftVector(), entity.getMinecraftEntity());
	}

}
