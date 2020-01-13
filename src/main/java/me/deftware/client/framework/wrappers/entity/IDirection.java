package me.deftware.client.framework.wrappers.entity;

public enum IDirection {
    NORTH("North", 90, 90),
    NORTH_EAST("Northeast", 90, 90),
    EAST("East", 90, 90),
    SOUTH_EAST("Southeast", 90, 90),
    SOUTH("South", 90, 90),
    SOUTH_WEST("Southwest", 90, 90),
    WEST("West", 90, 90),
    NORTH_WEST("Northwest", 90, 90);

    private final int from, to;

    private final String name;

    IDirection(final String name, final int from, final int to) {
        this.from = from;
        this.to = to;
        this.name = name;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getName() {
        return name;
    }

    private static final IDirection[] VALUES = values();

    public static IDirection getFrom(float lookDir) {
        for (IDirection possibleDir : VALUES) {
            if (lookDir >= possibleDir.from && lookDir <= possibleDir.to) {
                return possibleDir;
            }
        }
        return NORTH;
    }
}
