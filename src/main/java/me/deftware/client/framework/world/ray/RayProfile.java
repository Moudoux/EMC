package me.deftware.client.framework.world.ray;

import net.minecraft.world.RaycastContext;

public enum RayProfile {

    Block(
            RaycastContext.ShapeType.OUTLINE,
            RaycastContext.FluidHandling.NONE
    ),

    IncludeFluid(
            RaycastContext.ShapeType.OUTLINE,
            RaycastContext.FluidHandling.ANY
    );

    private final RaycastContext.ShapeType shape;
    private final RaycastContext.FluidHandling fluidHandling;

    RayProfile(RaycastContext.ShapeType shape, RaycastContext.FluidHandling fluidHandling) {
        this.shape = shape;
        this.fluidHandling = fluidHandling;
    }

    public RaycastContext.ShapeType getShape() {
        return shape;
    }

    public RaycastContext.FluidHandling getFluidHandling() {
        return fluidHandling;
    }

}
