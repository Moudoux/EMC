package me.deftware.client.framework.global;

/**
 * @author Deftware
 */
public enum GameKeys implements IGameKey {

    WORLD_DEPTH, // gl depth flag when rendering world
    CROSSHAIR, // Rendering of the default crosshair
    EFFECT_OVERLAY, // status effect overlays in the top-right corner
    RAINBOW_ITEM_GLINT,
    NOCLIP,
    LEVITATION,
    JUMP_HEIGHT,
    EMC_MAIN_MENU_OVERLAY,

    FLIP_USERNAMES,
    DEADMAU_EARS,

    BYPASS_REACH_LIMIT,
    BLOCK_REACH_DISTANCE,
    EXTENDED_REACH,

    FULL_LIQUID_VOXEL,
    FULL_BERRY_VOXEL,
    FULL_CACTUS_VOXEL,

    FULL_BARRIER_TEXTURE,
    FULL_LIGHT_TEXTURE,

    RENDER_FLUIDS,

    MARKETPLACE_ESC_BUTTON(GameCategory.External);

    private final String key;
    private final GameCategory category;

    GameKeys() {
        this(GameCategory.Default, null);
    }

    GameKeys(GameCategory category) {
        this(category, null);
    }

    GameKeys(GameCategory category, String key) {
        this.key = key == null ? name() : key;
        this.category = category;
    }

    public String getKey() {
        return this.key;
    }

    public GameCategory getCategory() {
        return this.category;
    }

}
