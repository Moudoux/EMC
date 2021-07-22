package me.deftware.client.framework.resource;

import com.google.common.collect.Sets;
import me.deftware.client.framework.main.EMCMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Deftware
 */
public class ModResourceManager implements ResourceManager {

    private final Set<String> namespaces = Sets.newLinkedHashSet();
    private final ZipFile zipFile;
    private final String type;

    private BiFunction<String, InputStream, InputStream> transformer = (path, stream) -> stream;

    public ModResourceManager(EMCMod mod, String type) throws IOException {
        this.zipFile = getZipFile(mod);
        this.namespaces.add(mod.getMeta().getName().toLowerCase());
        this.type = type;
    }

    public void setTransformer(BiFunction<String, InputStream, InputStream> transformer) {
        this.transformer = transformer;
    }

    public ZipFile getZipFile(EMCMod mod) throws IOException {
        return new ZipFile(mod.physicalFile);
    }

    public ZipEntry getEntry(String name) {
        return zipFile.getEntry(type + "/" + name);
    }

    @Override
    public Set<String> getAllNamespaces() {
        return namespaces;
    }

    @Override
    public boolean containsResource(Identifier id) {
        return getEntry(id.getPath()) != null;
    }

    @Override
    public List<Resource> getAllResources(Identifier id) throws IOException {
        return Collections.singletonList(getResource(id));
    }

    @Override
    public Resource getResource(Identifier id) throws IOException {
        ZipEntry entry = getEntry(id.getPath());
        if (entry == null) {
            return MinecraftClient.getInstance().getResourceManager().getResource(id);
        }
        return new ModResource(transformer.apply(id.getPath(), zipFile.getInputStream(entry)), id);
    }

    @Override
    public Collection<Identifier> findResources(String startingPath, Predicate<String> pathPredicate) {
        return null;
    }

    @Override
    public Stream<ResourcePack> streamResourcePacks() {
        return null;
    }

}
