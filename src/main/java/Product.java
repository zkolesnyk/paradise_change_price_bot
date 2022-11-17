public class Product {
    private String fullName;
    private String shortName;
    private final int id;

    public boolean isBalloon() {
        return balloon;
    }

    private final boolean balloon;
    private final int price;
    private int quantity = 0;
    public int getQuantity() {
        return this.quantity;
    }
    public void setQuantity(int quantity) {
        if (quantity != 0) {
            this.fullName += ": " + this.quantity + " шт.";
        }
        this.quantity = quantity;
    }
    public String getFullName() {
        return this.fullName;
    }
    public String getShortName() {
        return this.shortName;
    }
    public int getId() {
        return this.id;
    }
    public int getPrice() {
        return this.price;
    }
    Product(int id, String name, String shortName, int price, boolean balloon) {
        this.id = id;
        this.fullName = name;
        this.shortName = shortName;
        this.price = price;
        this.balloon = balloon;
    }
}
