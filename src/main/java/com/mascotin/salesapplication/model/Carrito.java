package com.mascotin.salesapplication.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openxava.annotations.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Carrito {
    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrito> items;

    @Transient
    private String metodoPago;

    @Transient
    private String nombreTitular;

    @Transient
    private String datosPago;

    @Money @ReadOnly
    public BigDecimal getTotal() {
        return items == null ? BigDecimal.ZERO : items.stream()
                .map(ItemCarrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
