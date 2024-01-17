package model.repositories;

import model.entities.ImportedProduct;
import model.entities.Product;
import model.entities.UsedProduct;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProductsRepository {
    private static ProductsRepository productsRepository;

    private List<Product> products;

    private ProductsRepository(){
        products = new ArrayList<>();
    }

    public static ProductsRepository getInstance(){
        if(productsRepository == null){
            productsRepository = new ProductsRepository();
        }

        return productsRepository;
    }

    public List<Product> getProducts(){
        return products;
    }

    public void addProductToList(Product product){
        products.add(product);
    }

    public void addProductToList(String name, Double price){
        addProductToList(new Product(name, price));
    }

    public void addProductToList(String name, Double price, Double customsFee){
        addProductToList(new ImportedProduct(name, price, customsFee));
    }

    public void addProductToList(String name, Double price, LocalDate manufactureDate){
        addProductToList(new UsedProduct(name, price, manufactureDate));
    }

    public void addProductToList(String name, Double price, String manufactureDate){
        addProductToList(new UsedProduct(name, price, LocalDate.parse(manufactureDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
    }

    public void removeProductFromList(Product product){
        products.remove(product);
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();

        for(Product product : products){
            result.append(product.priceTag());
        }

        return result.toString();
    }
}
