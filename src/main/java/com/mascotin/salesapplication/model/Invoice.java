package com.mascotin.salesapplication.model;

import com.mascotin.salesapplication.calculator.CurrentLocalDateCalculator;
import com.mascotin.salesapplication.model.catalogue.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.openxava.annotations.*;
import org.openxava.jpa.XPersistence;

import javax.persistence.*;
import javax.validation.ValidationException;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.LawOfDemeter"})
public class Invoice {

    @Id @Hidden
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;

    @Required
    @FutureOrPresent
    @DefaultValueCalculator(CurrentLocalDateCalculator.class)
    private LocalDate purchaseDate;

    @Enumerated(EnumType.STRING)
    private MetodoPago payMethod;

    @Required
    private String nameHolder;

    private String payData;

    @ElementCollection
    @Size(min = 1, message = "El carrito de compras debe tener al menos un producto.")
    @ListProperties("product.sku, product.name, amount, subtotal")
    private List<ItemShoppingCart> shoppingCart;

    @PrePersist
    @PreUpdate
    private void updateProductStock() {
        if (shoppingCart == null || shoppingCart.isEmpty()) {
            return; // No hay elementos para procesar
        }

        final EntityManager entityManager = XPersistence.getManager();
        for (final ItemShoppingCart orderItem : shoppingCart) {
            final Product product = orderItem.getProduct();
            final int quantity = orderItem.getAmount();

            if (product == null || quantity <= 0) {
                throw new ValidationException("Producto o cantidad inválida en la línea de pedido");
            }

            // Cargar la entidad Product desde la base de datos para evitar problemas de persistencia
            final Product managedProduct = entityManager.find(Product.class, product.getProductId());
            if (managedProduct == null) {
                throw new ValidationException("Producto no encontrado: " + product.getSku());
            }

            final int newStock = managedProduct.getStock() - quantity;
            if (newStock < 0) {
                throw new ValidationException("Stock insuficiente para el producto: " + managedProduct.getName());
            }

            managedProduct.setStock(newStock);
            entityManager.merge(managedProduct); // Actualizar el producto en la base de datos
        }
    }
}