package model.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class UsedProduct extends Product{
    private LocalDate manufactureDate;

    public UsedProduct(String name, Double price, String manufactureDate){
        super(name, price);
        this.manufactureDate = LocalDate.parse(manufactureDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public UsedProduct(String name, Double price, LocalDate manufactureDate){
        super(name, price);
        this.manufactureDate = manufactureDate;
    }

    public LocalDate getManufactureDate(){
        return manufactureDate;
    }

    @Override
    public String priceTag(){
        return String.format("[Name: %s] [Price: $%.2f] [Manufacture date: %s]%n", super.getName(), super.getPrice(), manufactureDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UsedProduct that = (UsedProduct) o;
        return Objects.equals(manufactureDate, that.manufactureDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), manufactureDate);
    }
}
