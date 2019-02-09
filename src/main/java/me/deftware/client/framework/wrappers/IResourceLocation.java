package me.deftware.client.framework.wrappers;

import net.minecraft.util.Identifier;

public class IResourceLocation extends Identifier {

    public IResourceLocation(String resourceName) {
        super(resourceName);
    }

    public IResourceLocation(String resourceDomainIn, String resourcePathIn) {
        super(resourceDomainIn, resourcePathIn);
    }

}
