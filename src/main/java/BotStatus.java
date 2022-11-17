public enum BotStatus {
    DEFAULT(0),
    GET_SKU(1),
    GET_NAME(2),
    GET_QUANTITY(3),
    CREATE(4);

    private final int value;

    public int get() {
        return value;
    }
    BotStatus(int value) {
        this.value = value;
    }

}
