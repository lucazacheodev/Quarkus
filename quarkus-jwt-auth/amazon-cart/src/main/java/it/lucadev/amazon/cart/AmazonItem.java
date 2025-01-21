package it.lucadev.amazon.cart;
import java.util.Objects;

public class AmazonItem {

    private Long id;
    private String name;
    private int quantity;


    public AmazonItem() {
    }

    public AmazonItem(Long id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public AmazonItem id(Long id) {
        setId(id);
        return this;
    }

    public AmazonItem name(String name) {
        setName(name);
        return this;
    }

    public AmazonItem quantity(int quantity) {
        setQuantity(quantity);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AmazonItem)) {
            return false;
        }
        AmazonItem amazonItem = (AmazonItem) o;
        return Objects.equals(id, amazonItem.id) && Objects.equals(name, amazonItem.name) && quantity == amazonItem.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }
    
}
