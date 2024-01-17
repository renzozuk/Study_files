package model.entities;

import java.util.Objects;

public class ImportedProduct extends Product{
    private double customsFee;

    public ImportedProduct(String name, double price){
        super(name, price);
        customsFee = 0.0;
    }

    public ImportedProduct(String name, double price, double customsFee){
        super(name, price);
        this.customsFee = customsFee;
    }

    public double getTotalCost(){
        return this.getPrice() + customsFee;
    }

    public double getCustomsFee(){
        return customsFee;
    }

    @Override
    public String priceTag(){
        return String.format("[Name: %s] [Price: $%.2f] [Customs fee: %.2f]%n", super.getName(), getTotalCost(), customsFee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ImportedProduct that = (ImportedProduct) o;
        return Double.compare(customsFee, that.customsFee) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customsFee);
    }
}
