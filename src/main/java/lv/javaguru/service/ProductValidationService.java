package lv.javaguru.service;

import lv.javaguru.Product;

import java.math.BigDecimal;

public class ProductValidationService {

    public static void validate(Product product) throws ProductValidationException {

        product.setActualPrice(product.getPrice().multiply(new BigDecimal("1").subtract(product.getDiscount().divide(new BigDecimal(100)))));

        if (!product.getName().matches("[A-Za-z0-9]+") || product.getName().length() < 3 || product.getName().length() > 15) {
            throw new ProductValidationException("Product name is invalid");
        }

        if (product.getDiscount() == null ){
            product.setDiscount(BigDecimal.valueOf(0));
        }

        if ((product.getDiscount().compareTo(BigDecimal.valueOf(0)) < 0) || (product.getDiscount().compareTo(BigDecimal.valueOf(100))) > 0) {
            throw new ProductValidationException("Product discount is invalid");
        }

        if(product.getCategory() == null || product.getCategory().equals("")){
            throw new ProductValidationException("Choose the product category");
        }
    }
}
