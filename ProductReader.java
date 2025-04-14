import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductReader {
    public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select a Product Data File");
        int fileSelection = chooser.showOpenDialog(null);

        if (fileSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            ArrayList<Product> productRecords = new ArrayList<>();

            try (Scanner fileScanner = new Scanner(selectedFile)) {
                System.out.printf("%-10s %-20s %-30s %-10s%n", "ID#", "Name", "Description", "Cost");
                System.out.println("============================================================");

                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    String[] data = line.split(",\s*");

                    if (data.length == 4) {
                        Product product = new Product(data[1], data[2], data[0], Double.parseDouble(data[3]));
                        productRecords.add(product);
                        System.out.printf("%-10s %-20s %-30s %-10.2f%n",
                                product.getID(), product.getName(), product.getDescription(), product.getCost());
                    } else {
                        System.out.println("Invalid data format in line: " + line);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("The selected file was not found: " + e.getMessage());
            }
        } else {
            System.out.println("No file was selected. Exiting program.");
        }
    }
}
