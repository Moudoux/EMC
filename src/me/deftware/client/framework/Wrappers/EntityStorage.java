package me.deftware.client.framework.Wrappers;

import me.deftware.client.framework.Wrappers.Entity.IEntity;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;

public class EntityStorage {

	private static EntityPlayerSP espInstance;
	private static EntityPlayer eInstance;

	public static IEntity getEspInstance() {
		if (espInstance == null) {
			return null;
		}
		return new IEntity(espInstance);
	}

	public static void setEspInstance(EntityPlayerSP espInstance) {
		EntityStorage.espInstance = espInstance;
	}

	public static IEntity geteInstance() {
		if (eInstance == null) {
			return null;
		}
		return new IEntity(eInstance);
	}

	public static void seteInstance(EntityPlayer eInstance) {
		EntityStorage.eInstance = eInstance;
	}

}
