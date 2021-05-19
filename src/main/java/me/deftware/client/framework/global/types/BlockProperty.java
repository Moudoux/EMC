package me.deftware.client.framework.global.types;

import me.deftware.client.framework.math.box.VoxelShape;
import me.deftware.client.framework.world.block.Block;

public class BlockProperty implements IProperty {

    private final int id;

    private int luminance = 0;
    private boolean translucent = false, render = true;

    private VoxelShape voxelShape;

    public BlockProperty(Block block) {
        this(block.getID());
    }

    public BlockProperty(int blockId) {
        this.id = blockId;
    }

    @Override
    public int getId() {
        return id;
    }

    public BlockProperty setVoxelShape(VoxelShape voxelShape) {
        this.voxelShape = voxelShape;
        return this;
    }

    public int getLuminance() {
        return luminance;
    }

    public BlockProperty setLuminance(int luminance) {
        this.luminance = luminance;
        return this;
    }

    public boolean isTranslucent() {
        return translucent;
    }

    public BlockProperty setTranslucent(boolean translucent) {
        this.translucent = translucent;
        return this;
    }

    public boolean isRender() {
        return render;
    }

    public BlockProperty setRender(boolean render) {
        this.render = render;
        return this;
    }

    public VoxelShape getVoxelShape() {
        return voxelShape;
    }

}
