package me.deftware.client.framework.Wrappers.Objects;

import net.minecraft.util.ResourceLocation;

public class IResourceLocation extends ResourceLocation {

	public IResourceLocation(String resourceName) {
		super(resourceName);
	}

	public IResourceLocation(String resourceDomainIn, String resourcePathIn) {
		super(resourceDomainIn, resourcePathIn);
	}

}
