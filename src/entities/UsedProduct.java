package entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UsedProduct extends Product{
    private LocalDate manufactureDate;

    public UsedProduct(String name, Double price, LocalDate manufactureDate){
        super(name, price);
        this.manufactureDate = manufactureDate;
    }

    public LocalDate getManufactureDate(){
        return this.manufactureDate;
    }

    @Override
    public String priceTag(){
        return "Name: " + getName() + " (used)\nPrice: $" + String.format("%.2f", getPrice()) + "\nDate of manufacturing: " + manufactureDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
