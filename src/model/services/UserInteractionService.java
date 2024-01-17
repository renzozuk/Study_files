package model.services;

import model.entities.Product;
import model.repositories.ProductsRepository;
import model.util.Utilities;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class UserInteractionService {
    private static ProductsRepository productsRepository;

    private static void addProduct(Scanner sc){
        char type;

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
                    productsRepository.addProductToList(name, price);
                    break;
                case 'u':
                    System.out.print("Type the date of manufacturing: ");
                    String date = sc.next();
                    productsRepository.addProductToList(name, price, date);
                    break;
                case 'i':
                    System.out.print("Type the customs fee: ");
                    double customsfee = sc.nextDouble();
                    productsRepository.addProductToList(name, price, customsfee);
                    break;
            }
            System.out.printf("%n%s was added successfully.%n", name);
        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.println("The cost of the product must be a number.");
        }catch(DateTimeParseException e){
            System.out.println("You didn't put a valid date. The valid date format is: \"dd/MM/yyyy\"");
        }
        System.out.println();
    }

    private static void removeProduct(Scanner sc){
        if(productsRepository.getProducts().isEmpty()){
            System.out.print("There are no products in the system.\n\n");
            return;
        }

        int choice;

        try{
            do{
                int idx = 1;

                for(Product product : productsRepository.getProducts()){
                    System.out.printf("[%d] %s", idx, product.priceTag());

                    idx++;
                }

                System.out.printf("%nSelect a product to remove: ");

                choice = sc.nextInt();
            }while(choice < 1 || choice > productsRepository.getProducts().size());

            System.out.printf("%n%s was removed successfully.%n%n", productsRepository.getProducts().get(choice - 1).getName());
            productsRepository.removeProductFromList(productsRepository.getProducts().get(choice - 1));
        }catch(InputMismatchException e){
            sc.nextLine();
            System.out.printf("%nYou should put a number (an integer) between 1 and %d as a command to remove a product. Any other command will not work as well.%n", productsRepository.getProducts().size());
        }
    }

    private static void listProducts(){
        if(productsRepository.getProducts().isEmpty()){
            System.out.print("There are no products in the system.\n\n");
            return;
        }

        System.out.println(productsRepository);
    }

    public static void start() throws IOException {
        Utilities.clearScreen();

        Locale.setDefault(Locale.US);

        productsRepository = ProductsRepository.getInstance();

        int choice;

        Scanner sc = new Scanner(System.in);

        do{
            do{
                try{
                    System.out.print("Insert a command (1 to add a product, 2 to remove a product, 3 to see the products, 0 to exit the program): ");
                    choice = sc.nextInt();
                }catch(InputMismatchException e){
                    System.out.printf("%nYou must put an integer (0, 1, 2 or 3) as a command. Any other command will not work as well.%n");
                    choice = 0;
                }
            }while(choice > 3 || choice < 0);

            FileManagementService.fileReader(productsRepository.getProducts());

            Utilities.clearScreen();

            switch(choice){
                case 1:
                    addProduct(sc);
                    break;
                case 2:
                    removeProduct(sc);
                    break;
                case 3:
                    listProducts();
                    break;
            }

            FileManagementService.fileWriter(productsRepository.getProducts());
        }while(choice != 0);

        sc.close();
    }
}
