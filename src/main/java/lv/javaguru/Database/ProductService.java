package lv.javaguru.Database;

import javafx.collections.ObservableList;
import lv.javaguru.Product;
import lv.javaguru.service.ProductValidationException;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService<T> {

    public List<T> findAll();

    public void addProduct(T item) throws ProductValidationException, InvocationTargetException;

    public void deleteProduct(String name);

    public void deleteProduct(int ID);

    public ObservableList<T> findProductByName(String name);

    public ObservableList<T> findProductByID(int ID);

    public void setDiscountForCategory(ObservableList<Product> list, ProductCategory category, BigDecimal discount);

    public ObservableList<T> showCategory (ProductCategory category);
}
