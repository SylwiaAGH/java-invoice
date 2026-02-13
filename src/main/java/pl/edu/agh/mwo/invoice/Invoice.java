package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {

    private Map<Product, Integer> products =
            new HashMap<>();

    public void addProduct(Product product) {
       if(product == null){
           throw new IllegalArgumentException("Wartość produkt nie może być null-em");
       }

        this.addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if(quantity == 0 || quantity <= 0){
            throw new IllegalArgumentException("Ilość nie może być równa zero lub mniejsza niż zero");
        }

        this.products.put(product, quantity);
    }

    public BigDecimal getNetValue() {
        BigDecimal value =BigDecimal.ZERO;
        for (Product product : this.products.keySet()) {
            Integer quantity = this.products.get(product);
            BigDecimal price = product.getPrice();
            price = price.multiply(BigDecimal.valueOf(quantity));
            value= value.add(price);
        }

        return value;
    }

    public BigDecimal getTax() {

            return getGrossValue().subtract(getNetValue());
    }

    public BigDecimal getGrossValue() {
            BigDecimal value =BigDecimal.ZERO;
            for (Product product : this.products.keySet()) {
                Integer quantity = this.products.get(product);
                BigDecimal price = product.getPriceWithTax();
                price = price.multiply(BigDecimal.valueOf(quantity));
                value= value.add(price);
            }

            return value;

    }
}
