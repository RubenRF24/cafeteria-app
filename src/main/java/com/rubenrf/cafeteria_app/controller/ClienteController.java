package com.rubenrf.cafeteria_app.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.rubenrf.cafeteria_app.dto.cliente.DatosActualizarCliente;
import com.rubenrf.cafeteria_app.dto.cliente.DatosCrearCliente;
import com.rubenrf.cafeteria_app.dto.cliente.DatosListadoCliente;
import com.rubenrf.cafeteria_app.model.Cliente;
import com.rubenrf.cafeteria_app.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<?> crearCliente(@RequestBody @Valid DatosCrearCliente datosCrearCliente,
            UriComponentsBuilder uriComponentsBuilder) {
        Cliente cliente = clienteService.crearCliente(datosCrearCliente);

        URI uri = uriComponentsBuilder.path("/api/clientes/{id}").buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).body("Cliente #" + cliente.getId() + " creado correctamente.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarClientePorId(id);

        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<?> listarClientes(Pageable pageable) {
        Page<DatosListadoCliente> clientes = clienteService.listarClientes(pageable);

        return clientes.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(clientes.getContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCliente(@PathVariable Long id,
            @RequestBody DatosActualizarCliente datosActualizarCliente) {

        Cliente cliente = clienteService.buscarClientePorId(id);

        cliente.actualizarCliente(datosActualizarCliente);

        Cliente clienteActualizado = clienteService.actualizarCliente(cliente);
        return ResponseEntity.ok().body("Cliente #" + clienteActualizado.getId() + " actualizado correctamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

}
