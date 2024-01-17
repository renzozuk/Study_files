package model.services;

import model.entities.ImportedProduct;
import model.entities.Product;
import model.entities.UsedProduct;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class FileManagementService {
    public static void fileReader(List<Product> products) throws FileNotFoundException {
        File file = new File("resources//dataResources//summary.csv");

        if(file.exists()){
            Scanner reader = new Scanner(new File("resources//dataResources//summary.csv"));

            String line;

            products.clear();

            while(reader.hasNextLine()){
                line = reader.nextLine();

                String[] information = line.split(",");

                switch(information[0]){
                    case "ImportedProduct":
                        products.add(new ImportedProduct(information[1], Double.parseDouble(information[2]), Double.parseDouble(information[3])));
                        break;
                    case "UsedProduct":
                        products.add(new UsedProduct(information[1], Double.parseDouble(information[2]), information[3]));
                        break;
                    default:
                        products.add(new Product(information[1], Double.parseDouble(information[2])));
                        break;
                }
            }

            reader.close();
        }
    }

    public static void fileWriter(List<Product> products) throws IOException {
        FileWriter writer = new FileWriter("resources//dataResources//summary.csv");

        for (Product product : products) {
            writer.write(product.getClass().getSimpleName() + ",");

            writer.write(product.getName() + ",");

            writer.write(String.valueOf(product.getPrice()));

            if(product instanceof ImportedProduct){
                writer.write(",");
                writer.write(String.format("%.2f", ((ImportedProduct) product).getCustomsFee()));
            }else if(product instanceof UsedProduct){
                writer.write(",");
                writer.write(((UsedProduct) product).getManufactureDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }

            writer.write("\n");
        }

        writer.close();
    }
}
