package com.rubenrf.cafeteria_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rubenrf.cafeteria_app.dto.cliente.DatosCrearCliente;
import com.rubenrf.cafeteria_app.dto.cliente.DatosListadoCliente;
import com.rubenrf.cafeteria_app.model.Cliente;
import com.rubenrf.cafeteria_app.repository.ClienteRepository;
import com.rubenrf.cafeteria_app.service.ClienteService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente crearCliente(DatosCrearCliente datosCrearCliente) {
        Cliente cliente = Cliente.builder()
                .nombre(datosCrearCliente.nombre())
                .telefono(datosCrearCliente.telefono())
                .correo(datosCrearCliente.correo())
                .build();

        return clienteRepository.save(cliente);
    }

    @Override
    public Page<DatosListadoCliente> listarClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(DatosListadoCliente::new);
    }

    @Override
    public Cliente buscarClientePorId(Long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new EntityNotFoundException("Cliente #" + idCliente + " no encontrado."));
    }

    @Override
    public Cliente actualizarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminarCliente(Long idCliente) {
        clienteRepository.deleteById(idCliente);
    }

}
