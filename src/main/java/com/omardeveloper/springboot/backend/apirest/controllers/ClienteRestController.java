package com.omardeveloper.springboot.backend.apirest.controllers;

import com.omardeveloper.springboot.backend.apirest.model.entity.Cliente;
import com.omardeveloper.springboot.backend.apirest.services.contrato.ICLienteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

    private final ICLienteService service;

    public ClienteRestController(ICLienteService service) {
        this.service = service;
    }

    @GetMapping("/clientes")
    public ResponseEntity<?> index(){
        List<Cliente> clientes = service.findAll();
        if (clientes.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientes);
    }
    @GetMapping("/clientes/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Cliente cliente = service.findById(id);
        if (cliente  == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }
    @PostMapping("/clientes")
    public ResponseEntity<?> save(@RequestBody Cliente cliente){
        Cliente clienteSave = service.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSave);
    }
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Cliente cliente){
        Cliente clienteLocal = service.findById(id);
        if (clienteLocal == null){
            return ResponseEntity.notFound().build();
        }
        clienteLocal.setNombre(cliente.getNombre());
        clienteLocal.setApellido(cliente.getApellido());
        clienteLocal.setEmail(cliente.getEmail());
        service.save(clienteLocal);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteLocal);
    }
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Cliente cliente = service.findById(id);
        if (cliente == null){
            return ResponseEntity.notFound().build();
        }
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
