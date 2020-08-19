package me.deftware.client.framework.world.block;

/**
 * @author Deftware
 */
public class Material {

	protected final net.minecraft.block.Material material;

	public Material(net.minecraft.block.Material material) {
		this.material = material;
	}

	public boolean isReplaceable() {
		return material.isReplaceable();
	}

	public boolean isLiquid() {
		return material.isLiquid();
	}

	public boolean isSolid() {
		return material.isSolid();
	}

	public boolean isBurnable() {
		return material.isBurnable();
	}

}
