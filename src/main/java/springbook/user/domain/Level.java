package springbook.user.domain;

/**
 * Created by Hobbit-Klaus on 2017-04-23.
 */
public enum Level {
    BASIC(1), SILVER(2), GOLD(3);

    private final int value;

    Level(int value) {
        this.value = value;
    }

    public static Level valueOf(int value) {
        switch (value) {
            case 1:
                return BASIC;
            case 2:
                return SILVER;
            case 3:
                return GOLD;
            default:
                throw new AssertionError("Unknown value: " + value);
        }
    }

    public int intValue() {
        return value;
    }
}
