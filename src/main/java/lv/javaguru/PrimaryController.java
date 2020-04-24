package lv.javaguru;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.BigDecimalStringConverter;
import lv.javaguru.Database.ProductCategory;
import lv.javaguru.Database.ProductRepository;
import lv.javaguru.Database.ProductService;
import lv.javaguru.service.ProductValidationException;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import static lv.javaguru.Database.ProductCategory.VEGETABLES;

public class PrimaryController implements Initializable {

    @FXML
    private TableView<Product> table;

    @FXML
    private TableColumn<Product, Integer> ID;

    @FXML
    private TableColumn<Product, String> name;

    @FXML
    private TableColumn<Product, BigDecimal> price;

    @FXML
    private TableColumn<Product, ProductCategory> category;

    @FXML
    private TableColumn<Product, BigDecimal> discount;

    @FXML
    private TableColumn<Product, BigDecimal> actualPrice;

    @FXML
    private TableColumn<Product, String> description;

    @FXML
    private TextField addingProductNameField;

    @FXML
    private TextField addingProductPriceField;

    @FXML
    private TextField addingProductDiscountField;

    @FXML
    private TextField addingProductDescriptionField;

    @FXML
    private ComboBox<ProductCategory> addingProductCategoryField;

    @FXML
    private ComboBox<ProductCategory> discountProductCategoryField;

    @FXML
    private ComboBox<ProductCategory> showCategoryComboBox;

    @FXML
    private TextField discountCategoryField;

    @FXML
    private Button showCategoryButton;

    @FXML
    private TextField findingProductNameField;

    @FXML
    private Button addProductButton;

    @FXML
    private Button removeProductButton;

    @FXML
    private Button findProductButton;

    @FXML
    private AnchorPane myAnchorPane;


    ObservableList<Product> allProductList = FXCollections.observableArrayList();
    ProductService<Product> productService = new ProductRepository(allProductList);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        price.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("price"));
        category.setCellValueFactory(new PropertyValueFactory<Product, ProductCategory>("category"));
        discount.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("discount"));
        actualPrice.setCellValueFactory(new PropertyValueFactory<Product, BigDecimal>("actualPrice"));
        description.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        showCategoryComboBox.getItems().addAll(ProductCategory.values());
        addingProductCategoryField.getItems().addAll(ProductCategory.values());
        discountProductCategoryField.getItems().addAll(ProductCategory.values());

        table.setItems(allProductList);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        table.setEditable(true);
        name.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        price.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        discount.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));


        addingProductPriceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d+\\.?\\d*")) {
                    addingProductPriceField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        addingProductDiscountField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d+\\.?\\d*")) {
                    addingProductDiscountField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        discountCategoryField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d+\\.?\\d*")) {
                    discountCategoryField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    public void addProductButtonPressed(){

        try {
            Product newProduct = new Product (addingProductNameField.getText(), BigDecimal.valueOf(Double.parseDouble(addingProductPriceField.getText())), addingProductCategoryField.getValue());

            if(addingProductDiscountField.getText() == null || addingProductDiscountField.getText().equals("")){
                newProduct.setDiscount(BigDecimal.valueOf(0));
            }
            else {
                newProduct.setDiscount(BigDecimal.valueOf((Double.parseDouble(addingProductDiscountField.getText()))));
            }
            newProduct.setDescription(addingProductDescriptionField.getText());

            productService.addProduct(newProduct);
            addingProductNameField.clear();
            addingProductPriceField.clear();
            addingProductDiscountField.clear();
            addingProductDescriptionField.clear();
        } catch (NumberFormatException | InvocationTargetException | ProductValidationException e) {
            WarningDialog.showWarning(e.getMessage());
        }
    }

    public void changeProductNameCellEvent(TableColumn.CellEditEvent editedNameCell){
        Product productSelected = table.getSelectionModel().getSelectedItem();
        productSelected.setName(editedNameCell.getNewValue().toString());
    }

    public void changeProductPriceCellEvent(TableColumn.CellEditEvent editedPriceCell){
        Product productSelected = table.getSelectionModel().getSelectedItem();
        productSelected.setPrice(BigDecimal.valueOf(Double.parseDouble(editedPriceCell.getNewValue().toString())));
    }

    public void changeProductDescriptionCellEvent(TableColumn.CellEditEvent editedDescriptionCell){
        Product productSelected = table.getSelectionModel().getSelectedItem();
        productSelected.setDescription(editedDescriptionCell.getNewValue().toString());
    }

    public void changeProductDiscountCellEvent(TableColumn.CellEditEvent editedDiscountCell){
        Product productSelected = table.getSelectionModel().getSelectedItem();
        try {
            productSelected.setDiscount(BigDecimal.valueOf(Double.parseDouble(editedDiscountCell.getNewValue().toString())));
            table.getColumns().get(0).setVisible(false);
            table.getColumns().get(0).setVisible(true);
        }
        catch (Exception e){
            WarningDialog.showWarning(e.getMessage());
        }
    }


    public void deleteButtonPressed(){
        ObservableList<Product> selectedRows, productsToShow;
        productsToShow = table.getItems();

        selectedRows = table.getSelectionModel().getSelectedItems();

        for (Product product: selectedRows){
            productsToShow.remove(product);
        }
    }

    public void findButtonPressed(){
         table.setItems(productService.findProductByName(findingProductNameField.getText()));
    }

    public void showAllButtonPressed(){
        table.setItems(allProductList);
    }

    public void setDiscountButtonPressed(){
        try {
            productService.setDiscountForCategory(allProductList, discountProductCategoryField.getValue(), BigDecimal.valueOf(Double.parseDouble(discountCategoryField.getText())));
            table.getColumns().get(0).setVisible(false);
            table.getColumns().get(0).setVisible(true);
        }
        catch (Exception e){
            WarningDialog.showWarning("Empty field");
        }
    }

    public void showCategoryButtonPressed(){
        table.setItems(productService.showCategory(showCategoryComboBox.getValue()));
    }

    public void refreshButtonPressed(){
        productService.calculateActualPrice();
        table.getColumns().get(0).setVisible(false);
        table.getColumns().get(0).setVisible(true);
    }
}
