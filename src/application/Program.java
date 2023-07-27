package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import entities.Product;
import entities.UsedProduct;
import entities.ImportedProduct;

public class Program {
    static void clrsrc(){
        try{
            if(System.getProperty("os.name").contains("Windows")){
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }else{
                Runtime.getRuntime().exec("clear");
            }
        }catch(IOException | InterruptedException ex) {}
    }

    public static void main(String[] args) throws IOException {
        clrsrc();
        Locale.setDefault(Locale.US);
        List<Product> products = new ArrayList<Product>();
        File file = new File("products.txt");
        if(file.exists()){
            Scanner reader = new Scanner(new File("products.txt"));
            int quant = Integer.parseInt(String.valueOf(reader.nextLine()));
            for(int i = 0; i < quant; i++){
                String type = String.valueOf(reader.nextLine());
                if(type.equals("Product")){
                    String name = String.valueOf(reader.nextLine());
                    Double price = Double.parseDouble(String.valueOf(reader.nextLine()));
                    products.add(new Product(name, price));
                }else if(type.equals("UsedProduct")){
                    String name = String.valueOf(reader.nextLine());
                    Double price = Double.parseDouble(String.valueOf(reader.nextLine()));
                    LocalDate date = LocalDate.parse(String.valueOf(reader.nextLine()), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    products.add(new UsedProduct(name, price, date));
                }else if(type.equals("ImportedProduct")){
                    String name = String.valueOf(reader.nextLine());
                    Double price = Double.parseDouble(String.valueOf(reader.nextLine()));
                    Double customsfee = Double.parseDouble(String.valueOf(reader.nextLine()));
                    products.add(new ImportedProduct(name, price, customsfee));
                }
            }
            reader.close();
        }
        int choice = 0; char type = '0'; Scanner sc = new Scanner(System.in);
        do{
            do{
                try{
                    System.out.print("Insert a command (1 to add a product or 2 to see the products): ");
                    choice = sc.nextInt();
                }catch(InputMismatchException e){
                    System.out.println("You must put an integer (0, 1 or 2) as a command. Any other command will not work as well.");
                }
            }while(choice > 2 || choice < 0);
            clrsrc();
            switch(choice){
                case 1:
                    do{
                        System.out.print("Choose the type of the product (c for common, u for used or i for imported): ");
                        type = sc.next().charAt(0);
                    }while(type != 'c' && type != 'u' && type != 'i');
                    sc.nextLine();
                    System.out.print("Choose the name: ");
                    String name = sc.nextLine();
                    try{
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
                    }catch(InputMismatchException e){
                        sc.nextLine();
                        System.out.println("The cost of the product must be a number.");
                    }catch(DateTimeParseException e){
                        System.out.println("You didn't put a valid date. The valid date format is: \"dd/MM/yyyy\"");
                    }
                    System.out.println();
                    break;
                case 2:
                    if(products.size() == 0){
                        System.out.print("There are no products in the system.\n\n");
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
        FileWriter writer = new FileWriter("products.txt");
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
