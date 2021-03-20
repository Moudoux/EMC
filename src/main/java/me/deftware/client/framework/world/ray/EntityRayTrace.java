package me.deftware.client.framework.world.ray;

import me.deftware.client.framework.entity.Entity;
import me.deftware.client.framework.math.vector.Vector3d;
import me.deftware.client.framework.util.minecraft.EntitySwingResult;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

/**
 * @author Deftware
 */
public class EntityRayTrace extends RayTrace<EntitySwingResult> {

    private final double maxDistance;
    private final Vector3d rotation;

    public EntityRayTrace(Vector3d start, Vector3d end, Vector3d rotation, double distance, RayProfile profile) {
        super(start, end, profile);
        this.maxDistance = distance;
        this.rotation = rotation;
    }

    @Override
    public EntitySwingResult run(Entity in) {

        net.minecraft.entity.Entity entity = in.getMinecraftEntity();

        HitResult hitResult = raycast(start, end, entity);

        double distance = maxDistance * maxDistance;
        if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK)
            distance = hitResult.getPos().squaredDistanceTo(start.getMinecraftVector());

        Box box = entity.getBoundingBox().stretch(rotation.getMinecraftVector().multiply(maxDistance)).expand(1.0D, 1.0D, 1.0D);

        EntityHitResult result = ProjectileUtil.raycast(entity, start.getMinecraftVector(), end.getMinecraftVector(), box, e -> (!e.isSpectator() && e.collides()), distance);

        if (result != null) {
            double g = start.getMinecraftVector().squaredDistanceTo(result.getPos());
            if (g < distance) {
                return new EntitySwingResult(result);
            }
        }

        return null;
    }

    public HitResult raycast(Vector3d start, Vector3d end, net.minecraft.entity.Entity entity) {
        return MinecraftClient.getInstance().world.raycast(new RaycastContext(start.getMinecraftVector(), end.getMinecraftVector(), RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, entity));
    }

}
