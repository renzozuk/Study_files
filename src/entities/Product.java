package entities;

public class Product {
    private String name;
    private Double price;

    public Product(String name, Double price){
        this.name = name;
        this.price = price;
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public String priceTag(){
        return "Name: " + getName() + "\nPrice: $" + String.format("%.2f", getPrice());
    }
}
