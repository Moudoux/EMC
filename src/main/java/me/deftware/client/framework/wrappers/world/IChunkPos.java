package me.deftware.client.framework.wrappers.world;

import me.deftware.client.framework.wrappers.math.IVec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.ArrayList;
import java.util.List;

public class IChunkPos {

    private double x, z;
    private IBlockPos centerPos;
    private ChunkPos pos;

    public IChunkPos(ChunkPos pos) {
        this.pos = pos;
        updateCords(false);
    }

    public IChunkPos(BlockPos pos) {
        this.pos = new ChunkPos(pos);
        updateCords(false);
    }

    public IChunkPos(double x, double z) {
        this.x = x;
        this.z = z;
        updateCords(true);
    }

    public IChunkPos(IVec3d vec) {
        this.x = vec.vector.x;
        this.z = vec.vector.z;
        updateCords(true);
    }

    public ChunkPos getPos() {
        return pos;
    }

    public IBlockPos getBlockPos() {
        return centerPos;
    }

    public double getX() {
        return x;
    }

    public double getStartX() {
        return pos.getStartX();
    }

    public double getEndX() {
        return pos.getEndX();
    }

    public double getStartZ() {
        return pos.getStartZ();
    }

    public double getEndZ() {
        return pos.getEndZ();
    }

    public void setX(double x) {
        this.x = x;
        updateCords(true);
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
        updateCords(true);
    }

    private void updateCords(boolean blockPos) {
        if (blockPos) {
            centerPos = new IBlockPos(x, 0, z);
            pos = new ChunkPos(centerPos.getPos());
        } else {
            centerPos = new IBlockPos(pos.getCenterBlockPos());
            x = pos.x;
            z = pos.z;
        }
    }

    public String toCords() {
        return pos.x + ", " + pos.z;
    }

    public boolean compareTo(IChunkPos pos2) {
        return pos.x == pos2.getX() && pos.z == pos2.getZ();
    }
}
