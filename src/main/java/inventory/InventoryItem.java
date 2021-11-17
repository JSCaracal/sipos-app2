package inventory;

public class InventoryItem {
    private String serialNumber;
    private String name;
    private double price;

    InventoryItem(String serialNumber,String name, double price){
        this.serialNumber = serialNumber;
        this.name = name;
        this.price = price;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
