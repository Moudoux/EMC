package me.deftware.client.framework.wrappers.entity;

public enum IDirection {
    SOUTH("South", 0),
    SOUTH_WEST("Southwest", 45),
    WEST("West", 90),
    NORTH_WEST("Northwest", 135),
    NORTH("North", 180),
    NORTH_EAST("Northeast", 225),
    EAST("East", 270),
    SOUTH_EAST("Southeast", 315);

    private final int centerDegree;

    private final String name;

    IDirection(final String name, final int centerDegree) {
        this.centerDegree = centerDegree;

        this.name = name;
    }

    public float getCenter() {
        return centerDegree;
    }

    public String getName() {
        return name;
    }

    private static final IDirection[] VALUES = values();

    public static IDirection getFrom(float lookDir) {
        for (IDirection possibleDir : VALUES) {
            if (lookDir >= (possibleDir.centerDegree - 22.5f) && lookDir <= (possibleDir.centerDegree + 22.5f)) {
                return possibleDir;
            }
        }
        return NORTH;
    }
}
