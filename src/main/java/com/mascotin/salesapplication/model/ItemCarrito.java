package com.mascotin.salesapplication.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.openxava.annotations.*;
import javax.persistence.*;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@NoArgsConstructor
public class ItemCarrito {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Carrito carrito;

    @ManyToOne @Required
    private Producto producto;

    @Required @Min(1)
    private int cantidad;

    @ManyToOne
    private PerfilMascota perfilMascota;

    @Money @ReadOnly
    public BigDecimal getSubtotal() {
        if (producto == null) return BigDecimal.ZERO;
        BigDecimal precio = producto.getPrecioVenta()
                .multiply(BigDecimal.ONE.subtract(producto.getDescuento()));
        return precio.multiply(new BigDecimal(cantidad));
    }
}