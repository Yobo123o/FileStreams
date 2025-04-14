import java.util.Objects;

/**
 * Represents a product with basic details and utility methods.
 */
public class Product {
    private String name;
    private String description;
    private String ID; // Should not change after initialization
    private double cost;

    /**
     * Full constructor to initialize all fields.
     */
    public Product(String name, String description, String ID, double cost) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        setCost(cost); // Ensures validation
    }

    /**
     * Overloaded constructor for partial initialization.
     */
    public Product(String name, String ID) {
        this(name, "", ID, 0.0);
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getID() {
        return ID;
    }

    public double getCost() {
        return cost;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCost(double cost) {
        if (cost < 0) {
            throw new IllegalArgumentException("Cost must be a non-negative value.");
        }
        this.cost = cost;
    }

    /**
     * Returns a CSV representation of the product.
     */
    public String toCSV() {
        return String.format("%s,%s,%s,%.2f", ID, name, description, cost);
    }

    /**
     * Returns a JSON representation of the product.
     */
    public String toJSON() {
        return String.format("{\"ID\":\"%s\", \"Name\":\"%s\", \"Description\":\"%s\", \"Cost\":%.2f}",
                ID, name, description, cost);
    }

    /**
     * Returns an XML representation of the product.
     */
    public String toXML() {
        return String.format("<Product><ID>%s</ID><Name>%s</Name><Description>%s</Description><Cost>%.2f</Cost></Product>",
                ID, name, description, cost);
    }

    @Override
    public String toString() {
        return String.format("%s: %s (Cost: $%.2f)", name, description, cost);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.cost, cost) == 0 &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(ID, product.ID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, ID, cost);
    }
}
