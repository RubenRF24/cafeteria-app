package com.rubenrf.cafeteria_app.service;

import com.rubenrf.cafeteria_app.dto.detallesPedido.DatosCrearDetallesPedido;
import com.rubenrf.cafeteria_app.model.Pedido;

public interface DetallesPedidoService {

    void crearDetallesPedido(Pedido pedido, DatosCrearDetallesPedido datosCrearDetallesPedido);

}
