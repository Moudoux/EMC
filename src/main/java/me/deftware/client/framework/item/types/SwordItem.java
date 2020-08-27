package me.deftware.client.framework.item.types;

/**
 * @author Deftware
 */
public class SwordItem extends WeaponItem {

	public SwordItem(net.minecraft.item.Item item) {
		super(item);
	}

	@Override
	public float getAttackDamage() {
		return ((net.minecraft.item.SwordItem) item).getAttackDamage();
	}

}
