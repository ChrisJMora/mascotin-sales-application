package com.mascotin.salesapplication.model;

import com.mascotin.salesapplication.calculator.DefaultZeroCalculator;
import com.mascotin.salesapplication.model.catalogue.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.openxava.annotations.*;
import javax.persistence.*;
import javax.validation.ValidationException;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
@Entity
@Getter @Setter
@NoArgsConstructor
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.UnnecessaryAnnotationValueElement"})
public class Product {

    @Id @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(length = 20, unique = true)
    private String sku;

    @Required
    private String name;

    @Required
    @Money
    private BigDecimal sellPrice;

    @Required
    @DecimalMin(value = "0.00", inclusive = true)
    @DecimalMax("1.00")
    @DefaultValueCalculator(value = DefaultZeroCalculator.class)
    private BigDecimal sellDiscount;

    @Required
    @Money
    private BigDecimal costoCompra;

    @Required
    @Enumerated(EnumType.STRING)
    private ProductManufacturer manufacturer;

    @Required
    @Enumerated(EnumType.STRING)
    private ProductBrand productBrand;

    @Required
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Required
    @Enumerated(EnumType.STRING)
    private PetSpecie petSpecie;

    @Required
    @Enumerated(EnumType.STRING)
    private PetAge petAge;

    @Required
    @Enumerated(EnumType.STRING)
    private PetBreed petBreed;

    @Required
    private int stock;

    @PrePersist
    @PreUpdate
    private void validate() {
        if (sellPrice == null || sellPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("El precio debe ser mayor a 0");
        }

        if (costoCompra == null || costoCompra.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("El costo de compra debe ser mayor a 0");
        }

        if (sellDiscount == null || sellDiscount.compareTo(BigDecimal.ZERO) < 0 || sellDiscount.compareTo(BigDecimal.ONE) > 0) {
            throw new ValidationException("El descuento debe estar entre 0.00 y 1.00");
        }
    }
}
