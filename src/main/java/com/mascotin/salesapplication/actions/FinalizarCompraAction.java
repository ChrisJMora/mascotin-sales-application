package com.mascotin.salesapplication.actions;

import com.mascotin.salesapplication.catalogue.*;
import com.mascotin.salesapplication.model.*;
import org.openxava.actions.*;
import org.openxava.jpa.XPersistence;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class FinalizarCompraAction extends ViewBaseAction {

    public void execute() throws Exception {
        EntityManager em = XPersistence.getManager();

        Carrito carrito = em.find(Carrito.class, getView().getValue("id"));
        if (carrito == null || carrito.getItems() == null || carrito.getItems().isEmpty()) {
            addError("El carrito está vacío o no se encontró");
            return;
        }

        String metodoPago = (String) getView().getValue("metodoPago");
        String titular = (String) getView().getValue("nombreTitular");
        String datosPago = (String) getView().getValue("datosPago");

        if (metodoPago == null || titular == null || datosPago == null ||
                metodoPago.isEmpty() || titular.isEmpty() || datosPago.isEmpty()) {
            addError("Todos los datos de pago son obligatorios");
            return;
        }

        Compra compra = new Compra();
        compra.setFechaCompra(LocalDate.now());
        compra.setMetodoPago(MetodoPago.valueOf(metodoPago));
        compra.setNombreTitular(titular);
        compra.setDatosPago(datosPago);
        compra.setDetalles(new ArrayList<>());

        for (ItemCarrito item : carrito.getItems()) {
            Producto producto = item.getProducto();
            if (producto.getStock() < item.getCantidad()) {
                addError("Stock insuficiente para el producto: " + producto.getNombre());
                return;
            }

            producto.setStock(producto.getStock() - item.getCantidad());

            DetalleCompra detalle = new DetalleCompra();
            detalle.setCompra(compra);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            compra.getDetalles().add(detalle);
        }

        em.persist(compra);
        carrito.getItems().clear();
        addMessage("Compra finalizada correctamente");

        // Puedes invocar aquí la generación del PDF
    }
}
