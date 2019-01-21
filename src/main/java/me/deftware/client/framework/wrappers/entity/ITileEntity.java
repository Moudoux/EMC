package me.deftware.client.framework.wrappers.entity;

import me.deftware.client.framework.wrappers.world.IBlockPos;
import net.minecraft.tileentity.*;

import java.awt.*;

public class ITileEntity {

    private IChestType chestType;
    private IBlockPos position;
    private Color color;

    public ITileEntity(TileEntity entity) {
        chestType = entity instanceof TileEntityChest
                ? entity instanceof TileEntityTrappedChest ? IChestType.TRAPPED_CHEST : IChestType.CHEST
                : entity instanceof TileEntityEnderChest ? IChestType.ENDER_CHEST
                : entity instanceof TileEntityShulkerBox ? IChestType.SHULKER_BOX : null;
        color = chestType.equals(IChestType.TRAPPED_CHEST) ? Color.RED
                : chestType.equals(IChestType.CHEST) ? Color.ORANGE
                : chestType.equals(IChestType.ENDER_CHEST) ? Color.BLUE : Color.PINK;
        position = new IBlockPos(entity.getPos());
    }

    public IChestType getChestType() {
        return chestType;
    }

    public IBlockPos getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public boolean isChest() {
        return chestType != null;
    }

    public static enum IChestType {
        TRAPPED_CHEST, CHEST, ENDER_CHEST, SHULKER_BOX
    }

}
