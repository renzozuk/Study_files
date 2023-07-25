package entities;

public class ImportedProduct extends Product{
    private double CustomsFee;

    public ImportedProduct(String name, double price, double CustomsFee){
        super(name, price);
        this.CustomsFee = CustomsFee;
    }

    public double getTotalCost(){
        return this.getPrice() + this.CustomsFee;
    }

    public double getCustomsFee(){
        return this.CustomsFee;
    }

    @Override
    public String priceTag(){
        return "Name: " + getName() + "\nPrice: $" + String.format("%.2f", getTotalCost()) + " (Customs fee: $" + String.format("%.2f", getCustomsFee()) + ")";
    }
}
