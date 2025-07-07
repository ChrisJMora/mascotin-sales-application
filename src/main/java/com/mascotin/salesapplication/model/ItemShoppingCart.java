package com.mascotin.salesapplication.model;

import com.mascotin.salesapplication.calculator.DefaultZeroCalculator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openxava.annotations.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@SuppressWarnings({"PMD.UnnecessaryAnnotationValueElement", "PMD.LawOfDemeter"})
public class ItemShoppingCart {

    @Required
    @OneToOne
    private Product product;

    @Required
    @DefaultValueCalculator(value = DefaultZeroCalculator.class)
    private int amount;

    @ReadOnly
    @Stereotype("MONEY")
    @Depends("product, amount")
    public BigDecimal getSubtotal() {
        BigDecimal subtotal = BigDecimal.ZERO;
        if (product != null) {
            final BigDecimal price = product.getSellPrice()
                    .multiply(BigDecimal.ONE.subtract(product.getSellDiscount()));
            subtotal = price.multiply(new BigDecimal(amount)).setScale(2, RoundingMode.HALF_UP);
        }
        return subtotal;
    }
}