package me.deftware.client.framework.global.types;

public class BlockPropertyManager extends PropertyManager<BlockProperty> {

    /**
     * If enabled, only blocks touching air will be rendered (From selected blocks)
     */
    private boolean exposedOnly = false;

    /**
     * If enabled, non selected blocks touching cave air will not be rendered
     */
    private boolean disableCaveRendering = false;

    /**
     * Render non selected blocks with the selected opacity
     */
    private boolean opacityMode = true;

    /**
     * Opacity of which to render non selected blocks
     */
    private float opacity = 100f;

    public boolean isExposedOnly() {
        return exposedOnly;
    }

    public void setExposedOnly(boolean exposedOnly) {
        this.exposedOnly = exposedOnly;
    }

    public boolean isDisableCaveRendering() {
        return disableCaveRendering;
    }

    public void setDisableCaveRendering(boolean disableCaveRendering) {
        this.disableCaveRendering = disableCaveRendering;
    }

    public boolean isOpacityMode() {
        return opacityMode;
    }

    public void setOpacityMode(boolean opacityMode) {
        this.opacityMode = opacityMode;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

}
