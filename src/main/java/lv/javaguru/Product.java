package lv.javaguru;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import lv.javaguru.Database.ProductCategory;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Product {

    private static final AtomicInteger count = new AtomicInteger(0);
    private final SimpleIntegerProperty ID;
    private SimpleStringProperty name;
    private BigDecimal price;
    private ProductCategory category;
    private BigDecimal discount = BigDecimal.valueOf(0);
    private BigDecimal actualPrice;
    private SimpleStringProperty description = new SimpleStringProperty("");

    public Product(String name, BigDecimal price, ProductCategory category) {
        this.ID = new SimpleIntegerProperty(count.getAndIncrement());
        this.name = new SimpleStringProperty(name);
        this.price = price;
        this.category = category;
        this.actualPrice = price.multiply((BigDecimal.valueOf(100).subtract(discount)).divide(BigDecimal.valueOf(100)));
    }

    public int getID() {
        return ID.get();
    }

    public void setName(String name){
        this.name = new SimpleStringProperty(name);
    }

    public String getName() {
        return name.get();
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getActualPrice(){
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getName().equals(product.getName()) &&
                getPrice().equals(product.getPrice()) &&
                getCategory() == product.getCategory() &&
                Objects.equals(getDiscount(), product.getDiscount()) &&
                Objects.equals(getDescription(), product.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPrice(), getCategory(), getDiscount(), getDescription());
    }

    @Override
    public String toString() {
        return "Product{" +
                "ID=" + ID +
                ", name=" + name +
                ", price=" + price +
                ", category=" + category +
                ", discount=" + discount +
                ", actualPrice=" + actualPrice +
                ", description=" + description +
                '}';
    }
}