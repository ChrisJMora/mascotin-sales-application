package com.mascotin.salesapplication.model;

import com.mascotin.salesapplication.catalogue.*;
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
@SuppressWarnings({"PMD.CyclomaticComplexity"})
public class Producto {
    @Id
    @Column(length = 10)
    private String sku;

    @Required
    private String nombre;

    @Required
    @Money
    private BigDecimal precioVenta;

    @Required
    @DecimalMin("0.00")
    @DecimalMax("1.00")
    private BigDecimal descuento;

    @Required
    @Money
    private BigDecimal costoCompra;

    @Required
    @Enumerated(EnumType.STRING)
    private Fabricante fabricante;

    @Required
    @Enumerated(EnumType.STRING)
    private Marca marca;

    @Required
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    @Required
    @Enumerated(EnumType.STRING)
    private Especie especie;

    @Required
    @Enumerated(EnumType.STRING)
    private Edad edad;

    @Required
    @Enumerated(EnumType.STRING)
    private Raza raza;

    @Required
    private int stock;

    @PrePersist
    @PreUpdate
    private void validate() {
        if (precioVenta == null || precioVenta.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("El precio debe ser mayor a 0");
        }

        if (costoCompra == null || costoCompra.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("El costo de compra debe ser mayor a 0");
        }

        if (descuento == null || descuento.compareTo(BigDecimal.ZERO) < 0 || descuento.compareTo(BigDecimal.ONE) > 0) {
            throw new ValidationException("El descuento debe estar entre 0.00 y 1.00");
        }
    }
}
