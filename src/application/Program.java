package application;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

import entities.Product;
import entities.UsedProduct;
import entities.ImportedProduct;

public class Program {
    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<Product> products = new ArrayList<Product>();
        int choice; char type = '0';
        File file = new File("products.txt"); FileWriter writer = new FileWriter("products.txt");
        do{
            do{
                System.out.print("Insert a command (1 to add a product or 2 to see the products): ");
                choice = sc.nextInt();
            }while(choice > 2 || choice < 0);
            switch(choice){
                case 1:
                    do{
                        System.out.print("Choose the type of the product (c for common, u for used or i for imported): ");
                        type = sc.next().charAt(0);
                    }while(type != 'c' && type != 'u' && type != 'i');
                    sc.nextLine();
                    System.out.print("Choose the name: ");
                    String name = sc.nextLine();
                    System.out.print("Choose the price: ");
                    double price = sc.nextDouble();
                    switch(type){
                        case 'c':
                            products.add(new Product(name, price));
                            break;
                        case 'u':
                            System.out.print("Type the date of manufacturing: ");
                            LocalDate date = LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            products.add(new UsedProduct(name, price, date));
                            break;
                        case 'i':
                            System.out.print("Type the customs fee: ");
                            double customsfee = sc.nextDouble();
                            products.add(new ImportedProduct(name, price, customsfee));
                            break;
                    }
                    break;
                case 2:
                    if(products.size() == 0){
                        System.out.println("There are no products in the system.");
                    }
                    for(Product product: products){
                        System.out.println(product.priceTag());
                        System.out.println();
                    }
                    break;
            }
        }while(choice != 0);
        if(file.exists()){
            file.delete();
        }
        writer.write(products.size() + "\n");
        for (Product product : products) {
            writer.write(product.getClass().getSimpleName() + "\n");
            writer.write(product.getName() + "\n");
            writer.write(product.getPrice() + "\n");
            if(product instanceof ImportedProduct){
                writer.write(String.format("%.2f", ((ImportedProduct )product).getCustomsFee()) + "\n");
            }else if(product instanceof UsedProduct){
                writer.write(((UsedProduct) product).getManufactureDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
            }
        }
        //file.close();
        writer.close();
        sc.close();
    }

}
