public enum Product {
    LATEX(1, "Латексна гелієва кулька без малюнка"),
    LATEX_WITH_PICTURE(2, "Латексна гелієва кулька з малюнком"),
    CHROME(3, "Латексна гелієва кулька хром"),
    AGATE(4, "Латексна гелієва кулька агат"),
    CONFETTI(5, "Латексна гелієва кулька з конфетті"),
    FOIL(6, "Фольгована гелієва кулька 18д без малюнка"),
    FOIL_WITH_PICTURE(7, "Фольгована гелієва кулька з малюнком"),
    FOIL_WITH_INDIVIDUAL_PRINT(8, "Фольгована гелієва кулька з індивідуальним принтом"),
    SURPRISE_BOX(9, "Коробка-сюрприз з індивідуальним написом");

    private String name;
    private final int id;
    private int quantity = 0;
    public int getQuantity() {
        return this.quantity;
    }
    public void setQuantity(int quantity) {
        if (quantity != 0) {
            this.name += ": " + this.quantity + " шт.";
        }
        this.quantity = quantity;
    }
    public String getName() {
        return this.name;
    }
    public int getValue() {
        return this.id;
    }
    Product(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
