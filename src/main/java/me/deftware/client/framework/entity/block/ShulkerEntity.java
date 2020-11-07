package me.deftware.client.framework.entity.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;

import java.awt.*;

/**
 * @author Deftware
 */
public class ShulkerEntity extends StorageEntity {

	private Color color;

	public ShulkerEntity(BlockEntity entity) {
		super(entity);
	}

	@Override
	public ShulkerBoxBlockEntity getMinecraftEntity() {
		return (ShulkerBoxBlockEntity) entity;
	}

	public Color getColor() {
		if (color == null) {
			if (getMinecraftEntity().getColor() == null) {
				color = Color.pink;
			} else {
				color = new Color(getMinecraftEntity().getColor().getMapColor().getRenderColor(0)); // TODO: Verify getMapColor
			}
		}
		return color;
	}

}
