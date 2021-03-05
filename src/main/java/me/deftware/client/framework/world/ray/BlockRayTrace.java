package me.deftware.client.framework.world.ray;

import me.deftware.client.framework.math.vector.Vector3d;
import me.deftware.client.framework.util.minecraft.BlockSwingResult;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

import java.util.Objects;

public class BlockRayTrace extends RayTrace<BlockSwingResult> {

    public BlockRayTrace(Vector3d start, Vector3d end, RayProfile profile) {
        super(start, end, profile);
    }

    @Override
    public BlockSwingResult run() {
        BlockHitResult result = Objects.requireNonNull(MinecraftClient.getInstance().world).raycast(getContext());
        if (result != null && result.getType() == HitResult.Type.BLOCK)
            return new BlockSwingResult(result);
        return null;
    }

}
