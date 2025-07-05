package com.mascotin.salesapplication.model;

import com.mascotin.salesapplication.catalogue.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openxava.annotations.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Compra {
    @Id @GeneratedValue
    private Long id;

    private LocalDate fechaCompra;

    @Enumerated(EnumType.STRING)
    private MetodoPago metodoPago;

    private String nombreTitular;

    private String datosPago;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<DetalleCompra> detalles;

    @Money @ReadOnly
    public BigDecimal getTotal() {
        return detalles == null ? BigDecimal.ZERO : detalles.stream()
                .map(DetalleCompra::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}