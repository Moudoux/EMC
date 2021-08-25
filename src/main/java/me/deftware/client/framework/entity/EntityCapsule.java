package me.deftware.client.framework.entity;

import me.deftware.client.framework.chat.ChatMessage;
import me.deftware.client.framework.fonts.FontRenderer;
import me.deftware.client.framework.gui.widgets.SelectableList;
import me.deftware.client.framework.util.minecraft.MinecraftIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

/**
 * @author Deftware
 */
public class EntityCapsule implements SelectableList.ListItem {

    private final EntityType<? extends Entity> entityType;
    private Identifier texture;
    private final String id;

    public <T extends Entity> EntityCapsule(String id, EntityType<T> entityType) {
        this.entityType = entityType;
        this.id = id;
    }

    public me.deftware.client.framework.entity.Entity create() {
        return me.deftware.client.framework.entity.Entity.newInstance(entityType.create(
                Objects.requireNonNull(MinecraftClient.getInstance().world)
        ));
    }

    public String getId() {
        return id;
    }

    public EntityType<? extends Entity> getRaw() {
        return entityType;
    }

    public ChatMessage getName() {
        return new ChatMessage().fromText(entityType.getName());
    }

    public void setTexture(Identifier identifier) {
        texture = identifier;
    }

    public MinecraftIdentifier getTexture() {
        if (texture == null) return null;
        return new MinecraftIdentifier(texture);
    }

    public MinecraftIdentifier getIdentifier() {
        return new MinecraftIdentifier(Registry.ENTITY_TYPE.getId(entityType));
    }

    public String getTranslationKey() {
        return entityType.getTranslationKey();
    }

    @Override
    public void render(int index, int x, int y, int entryWidth, int entryHeight, int mouseX, int mouseY, float tickDelta) {
        FontRenderer.drawString(getName(), x + 28, y + ((entryHeight / 2) - (FontRenderer.getFontHeight() / 2)) - 3, 0xFFFFFF);
    }

}
