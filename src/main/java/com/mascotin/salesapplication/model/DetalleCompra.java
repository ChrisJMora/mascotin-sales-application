package com.mascotin.salesapplication.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openxava.annotations.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Setter
@NoArgsConstructor
public class DetalleCompra {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private Compra compra;

    @ManyToOne
    private Producto producto;

    private int cantidad;

    @Money @ReadOnly
    public BigDecimal getSubtotal() {
        if (producto == null) return BigDecimal.ZERO;
        BigDecimal precio = producto.getPrecioVenta()
                .multiply(BigDecimal.ONE.subtract(producto.getDescuento()));
        return precio.multiply(new BigDecimal(cantidad));
    }
}
