package me.deftware.client.framework.wrappers.entity;

public enum IDirection {
    NORTH("North", 247.5f, 292.5f),
    NORTH_EAST("Northeast", 292.5f, 337.5f),
    EAST("East", 337.5f, 360.0f, 0.0f, 22.5f),
    SOUTH_EAST("Southeast", 22.5f, 67.5f),
    SOUTH("South", 67.5f, 112.5f),
    SOUTH_WEST("Southwest", 112.5f, 157.5f),
    WEST("West", 157.5f, 202.5f),
    NORTH_WEST("Northwest", 202.5f, 247.5f);

    private final float leftDegree, rightDegree;

    private final float optionalLeftDegree, optionalRightDegree;

    private final String name;

    IDirection(final String name, final float leftDegree, final float rightDegree) {
        this.leftDegree = leftDegree;
        this.rightDegree = rightDegree;

        this.optionalLeftDegree = leftDegree;
        this.optionalRightDegree = rightDegree;

        this.name = name;
    }

    IDirection(final String name, final float leftDegree, final float rightDegree, final float optionalLeftDegree, final float optionalRightDegree) {
        this.leftDegree = leftDegree;
        this.rightDegree = rightDegree;

        this.optionalLeftDegree = optionalLeftDegree;
        this.optionalRightDegree = optionalRightDegree;

        this.name = name;
    }

    public float getCenter() {
        return (leftDegree + rightDegree) / 2;
    }

    public float getLeftDegree() {
        return leftDegree;
    }

    public float getRightDegree() {
        return rightDegree;
    }

    public float getOptionalLeftDegree() {
        return optionalLeftDegree;
    }

    public float getOptionalRightDegree() {
        return optionalRightDegree;
    }

    public String getName() {
        return name;
    }

    private static final IDirection[] VALUES = values();

    public static IDirection getFrom(float lookDir) {
        for (IDirection possibleDir : VALUES) {
            if ((lookDir > possibleDir.leftDegree && lookDir < possibleDir.rightDegree) || (lookDir > possibleDir.optionalLeftDegree && lookDir < possibleDir.optionalRightDegree)) {
                return possibleDir;
            }
        }
        return NORTH;
    }
}
