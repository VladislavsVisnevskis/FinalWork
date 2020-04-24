package lv.javaguru.service;

public class ProductValidationException extends Exception{

        public ProductValidationException(String message) {
            super(message);
        }

        public ProductValidationException(String message, Throwable cause) {
            super(message, cause);
        }

}
