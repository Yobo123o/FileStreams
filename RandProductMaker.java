import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.RandomAccessFile;

/**
 * RandProductMaker allows users to input Product data and
 * save it to a binary file using RandomAccessFile with fixed-length records.
 */
public class RandProductMaker extends JFrame {
    private JTextField nameField, descField, idField, costField, countField;
    private JButton addButton, quitButton;
    private int recordCount = 0;
    private final File file = new File("RandProductData.dat");

    /**
     * Constructs the GUI form for entering Product data.
     */
    public RandProductMaker() {
        setTitle("Product Entry - RandomAccess Format");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 5, 5));

        nameField = new JTextField();
        descField = new JTextField();
        idField = new JTextField();
        costField = new JTextField();
        countField = new JTextField("0");
        countField.setEditable(false);

        add(new JLabel("Product Name:")); add(nameField);
        add(new JLabel("Description:")); add(descField);
        add(new JLabel("Product ID:")); add(idField);
        add(new JLabel("Cost:")); add(costField);
        add(new JLabel("Records Saved:")); add(countField);

        addButton = new JButton("Add Product");
        quitButton = new JButton("Quit");
        add(addButton); add(quitButton);

        addButton.addActionListener(this::saveProduct);
        quitButton.addActionListener(e -> System.exit(0));

        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Validates fields, formats data, and writes the record to the binary file.
     */
    private void saveProduct(ActionEvent e) {
        String name = nameField.getText().trim();
        String desc = descField.getText().trim();
        String id = idField.getText().trim();
        String costStr = costField.getText().trim();

        if (name.isEmpty() || desc.isEmpty() || id.isEmpty() || costStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return;
        }

        double cost;
        try {
            cost = Double.parseDouble(costStr);
            if (cost < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid cost value.");
            return;
        }

        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            raf.seek(raf.length());
            raf.writeBytes(pad(name, 35));
            raf.writeBytes(pad(desc, 75));
            raf.writeBytes(pad(id, 6));
            raf.writeBytes(String.format("%10.2f", cost));

            recordCount++;
            countField.setText(String.valueOf(recordCount));
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to write record: " + ex.getMessage());
        }
    }

    /**
     * Pads or truncates a string to a fixed length.
     */
    private String pad(String input, int length) {
        if (input.length() > length) return input.substring(0, length);
        return String.format("%-" + length + "s", input);
    }

    /**
     * Clears form input fields.
     */
    private void clearFields() {
        nameField.setText("");
        descField.setText("");
        idField.setText("");
        costField.setText("");
        nameField.requestFocus();
    }

    /**
     * Launches the GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandProductMaker::new);
    }
}
