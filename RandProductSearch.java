import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.RandomAccessFile;

/**
 * RandProductSearch allows users to search for product records
 * stored in a RandomAccessFile using partial product name matching.
 */
public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private final File file = new File("RandProductData.dat");

    /**
     * Constructs the product search interface.
     */
    public RandProductSearch() {
        setTitle("Product Search");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        JButton quitButton = new JButton("Quit");

        topPanel.add(new JLabel("Search by partial name:"), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(quitButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        searchButton.addActionListener(this::searchRecords);
        quitButton.addActionListener(e -> System.exit(0));

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Searches the binary file for product names that match the input term.
     */
    private void searchRecords(ActionEvent e) {
        String keyword = searchField.getText().trim().toLowerCase();
        resultArea.setText("");

        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a search term.");
            return;
        }

        final int RECORD_LEN = 35 + 75 + 6 + 10;

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            long totalRecords = raf.length() / RECORD_LEN;

            for (int i = 0; i < totalRecords; i++) {
                raf.seek(i * RECORD_LEN);
                byte[] recordBytes = new byte[RECORD_LEN];
                raf.readFully(recordBytes);

                String record = new String(recordBytes);
                String name = record.substring(0, 35).trim();

                if (name.toLowerCase().contains(keyword)) {
                    String desc = record.substring(35, 110).trim();
                    String id = record.substring(110, 116).trim();
                    double cost = Double.parseDouble(record.substring(116).trim());

                    resultArea.append(String.format("%s (%s) - $%.2f\n", name, desc, cost));
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error reading records: " + ex.getMessage());
        }
    }

    /**
     * Launches the GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandProductSearch::new);
    }
}
