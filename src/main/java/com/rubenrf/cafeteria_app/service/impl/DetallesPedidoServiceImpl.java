package com.rubenrf.cafeteria_app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubenrf.cafeteria_app.model.DetallesPedido;
import com.rubenrf.cafeteria_app.repository.DetallesPedidoRepository;
import com.rubenrf.cafeteria_app.service.DetallesPedidoService;

@Service
public class DetallesPedidoServiceImpl implements DetallesPedidoService {

    @Autowired
    private DetallesPedidoRepository detallesPedidoRepository;

    @Override
    public void crearDetallesPedido(List<DetallesPedido> detallesPedidoList) {

        detallesPedidoRepository.saveAll(detallesPedidoList);
    }

}
