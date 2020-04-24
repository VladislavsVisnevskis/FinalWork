package lv.javaguru.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lv.javaguru.Product;
import lv.javaguru.service.ProductValidationException;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import static lv.javaguru.service.ProductValidationService.validate;

public class ProductRepository implements ProductService<Product> {

    private ObservableList<Product> productList;

    public ProductRepository(ObservableList<Product> allProductList){
        this.productList = allProductList;
    }

    @Override
    public List findAll() {
        return productList;
    }

    @Override
    public void addProduct(Product product) throws ProductValidationException, InvocationTargetException {
            validate(product);
            productList.add(product);
    }

    @Override
    public void deleteProduct(String name) {

    }

    @Override
    public void deleteProduct(int ID) {

    }

    @Override
    public ObservableList<Product> findProductByName(String name) {
        ObservableList<Product> filteredProductList = null;
        filteredProductList = productList.stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return filteredProductList;
    }

    @Override
    public ObservableList<Product> findProductByID(int ID) {
        return null;
    }

    @Override
    public void setDiscountForCategory(ObservableList<Product> list, ProductCategory category, BigDecimal discount) {
        productList.stream()
                .filter(product -> product.getCategory().equals(category)).
                map(product -> {
                    product.setDiscount(discount);
                    product.setActualPrice((product.getPrice().multiply(BigDecimal.valueOf(100).subtract(discount))).divide(BigDecimal.valueOf(100)));
                            return product;
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    @Override
    public ObservableList<Product> showCategory (ProductCategory category){
        ObservableList<Product> filteredCategory = null;
        filteredCategory = productList.stream()
                .filter(product -> product.getCategory().equals(category))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        return filteredCategory;
    }

    @Override
    public void calculateActualPrice() {
        productList.stream().
                map(product -> {
                    product.setActualPrice((product.getPrice().multiply(BigDecimal.valueOf(100).subtract(product.getDiscount()))).divide(BigDecimal.valueOf(100)));
                    return product;
                })
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
