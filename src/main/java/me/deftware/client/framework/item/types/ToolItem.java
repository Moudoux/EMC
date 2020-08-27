package me.deftware.client.framework.item.types;

import me.deftware.client.framework.item.Item;
import me.deftware.mixin.imp.IMixinItemTool;
import net.minecraft.item.MiningToolItem;

/**
 * @author Deftware
 */
public class ToolItem extends Item {

	public ToolItem(net.minecraft.item.Item item) {
		super(item);
	}

	public float getDamageVsEntity() {
		return ((IMixinItemTool) item).getAttackDamage();
	}

}
