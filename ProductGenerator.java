import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductGenerator {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        ArrayList<Product> productRecords = new ArrayList<>();
        boolean addMore = true;

        System.out.println("Welcome to the Product Generator!");
        System.out.println("Enter product details (ID, Name, Description, Cost).\n");

        while (addMore) {
            String id = SafeInput.getNonZeroLenString(in, "Enter Product ID");
            String name = SafeInput.getNonZeroLenString(in, "Enter Product Name");
            String description = SafeInput.getNonZeroLenString(in, "Enter Product Description");
            double cost = SafeInput.getDouble(in, "Enter Product Cost");

            productRecords.add(new Product(name, description, id, cost));

            addMore = SafeInput.getYNConfirm(in, "Do you want to add another product?");
        }

        String fileName = SafeInput.getNonZeroLenString(in, "Enter the name of the file to save the data (e.g., ProductTestData.txt)");

        try (FileWriter writer = new FileWriter(fileName)) {
            for (Product product : productRecords) {
                writer.write(product.toCSV() + "\n");
            }
            System.out.println("Data successfully saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        System.out.println("Thank you for using the Product Generator!");
    }
}