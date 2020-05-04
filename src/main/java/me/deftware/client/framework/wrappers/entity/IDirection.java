package me.deftware.client.framework.wrappers.entity;

public enum IDirection {
    NORTH("North", 337.5f, 22.5f), // Middle: 0
    NORTH_EAST("Northeast", 22.5f, 67.5f), // Middle: 45
    EAST("East", 67.5f, 112.5f), // Middle: 90
    SOUTH_EAST("Southeast", 112.5f, 157.5f), // Middle: 135
    SOUTH("South", 157.5f, 202.5f), // Middle: 180
    SOUTH_WEST("Southwest", 202.5f, 247.5f), // Middle: 225
    WEST("West", 247.5f, 292.5f), // Middle: 270
    NORTH_WEST("Northwest", 292.5f, 337.5f); // Middle: 315

    private final float leftDegree, rightDegree;

    private final String name;

    IDirection(final String name, final float leftDegree, final float rightDegree) {
        this.leftDegree = leftDegree;
        this.rightDegree = rightDegree;

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

    public String getName() {
        return name;
    }

    private static final IDirection[] VALUES = values();

    public static IDirection getFrom(float lookDir) {
        for (IDirection possibleDir : VALUES) {
            if (lookDir > possibleDir.leftDegree && lookDir < possibleDir.rightDegree) {
                return possibleDir;
            }
        }
        return NORTH;
    }
}
