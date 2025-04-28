package com.rubenrf.cafeteria_app.dto.producto;

import com.rubenrf.cafeteria_app.model.Categoria;
import com.rubenrf.cafeteria_app.model.Producto;

public record DatosListadoProducto(Long id, String nombre, Double precio, Categoria categoria, Integer stock) {
    // Constructor, getters, and other methods can be added here if needed

    public DatosListadoProducto(Producto producto) {
        this(producto.getId(), producto.getNombre(), producto.getPrecio(), Categoria.valueOf(producto.getCategoria()),
                producto.getStock());
    }
}
