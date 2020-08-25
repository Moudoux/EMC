package me.deftware.client.framework.item;

import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.util.math.BlockPos;

public class ThrowData {
    private final EyeOfEnderEntity entity;
    public double x;
    public double z;
    public double posX;
    public double posZ;
    private double count = 1;

    public ThrowData(EyeOfEnderEntity entity, double posX, double posZ, double x, double z) {
        this.entity = entity;
        this.posX = posX;
        this.posZ = posZ;
        this.x = x;
        this.z = z;
    }

    public void addVec(double x, double z) {
        this.x = this.x * count + x / (count + 1);
        this.z = this.z * count + z / (++count);
    }

    /**
     * X = ( - z1*x1p*x2 + x1*z1p*x2 + x1*z2*x2p - x1*x2*z2p ) / (x1*z2 - z1*x2)
     * Z = ( y2/x2 ) * X + y2p - ( y2/x2 )*x2p
     * 1 = d
     * 2 = this
     */
    public BlockPos calculateIntersection(ThrowData d) {
        double x = (-d.z * d.posX * this.x + d.x * d.posZ * this.x + d.x * this.z * this.posX + d.x * -this.x * this.posZ)
                / (d.x * this.z - d.z * this.x);
        double z = (this.z / this.x) * x + this.posZ - (this.z / this.x) * this.posX;

        return new BlockPos(x, 36, z);
    }

    public boolean sameEntity(EyeOfEnderEntity e) {
        return entity == e;
    }
}
