public class Card{
    private String number;
    private String name;
    private String expiry;
    private String cvv;
    private String brand;
    private String type;
    private boolean isActive;

    public Card(String number, String name, String expiry, String cvv, String brand, String type){
        this.number = number;
        this.name = name;
        this.expiry = expiry;
        this.cvv = cvv;
        this.brand = brand;
        this.type = type;
        this.isActive = true;
    }

    public void disable() {
        this.isActive = false;
        System.out.println("Card disabled successfully!");
    }

    public void activate() {
        this.isActive = true;
        System.out.println("Card activated successfully!");
    }

    public void update() {
        System.out.println("Card details updated successfully!");
    }

    public void remove() {
        System.out.println("Card removed successfully!");
    }
}