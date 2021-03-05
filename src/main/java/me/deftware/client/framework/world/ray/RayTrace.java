package me.deftware.client.framework.world.ray;

import me.deftware.client.framework.math.vector.Vector3d;
import me.deftware.client.framework.util.hitresult.CrosshairResult;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.RaycastContext;

import java.util.Objects;

/**
 * @author Deftware
 */
public abstract class RayTrace<T extends CrosshairResult> {

    protected final Vector3d start, end;
    protected final RayProfile profile;

    public RayTrace(Vector3d start, Vector3d end, RayProfile profile) {
        this.start = start;
        this.end = end;
        this.profile = profile;
    }

    protected RaycastContext getContext() {
        return new RaycastContext(
            start.getMinecraftVector(), end.getMinecraftVector(), profile.getShape(), profile.getFluidHandling(), Objects.requireNonNull(MinecraftClient.getInstance().player)
        );
    }

    public abstract T run();

}
