package com.omardeveloper.springboot.backend.apirest.controllers;

import com.omardeveloper.springboot.backend.apirest.model.entity.Cliente;
import com.omardeveloper.springboot.backend.apirest.services.contrato.ICLienteService;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el cliente con id: ".concat(id.toString().concat(" no existe")));
        }
        return ResponseEntity.ok(cliente);
    }
    @PostMapping("/clientes")
    public ResponseEntity<?> save(@RequestBody Cliente cliente){
        Cliente clienteSave = null;
        Map<String, Object> response = new HashMap<>();
        try{
            clienteSave = service.save(cliente);
        } catch (DataAccessException e){
            response.put("Mensaje","Error al insertar el cliente");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente se ha creado con exito");
        response.put("cliente",clienteSave);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/clientes/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Cliente cliente){
        Cliente clienteLocal = service.findById(id);
        Map<String, Object> response = new HashMap<>();
        if (clienteLocal == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro el cliente con id: ".concat(id.toString().concat(" no existe")));
        }
        try{
            clienteLocal.setNombre(cliente.getNombre());
            clienteLocal.setApellido(cliente.getApellido());
            clienteLocal.setEmail(cliente.getEmail());
            service.save(clienteLocal);
        }catch (DataAccessException e){
            response.put("Mensaje","Error al actualizar el cliente");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente ha sido actualizado con exito");
        response.put("cliente", clienteLocal);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        Map<String, Object> response = new HashMap<>();
        try{
            Cliente clienteEliminar = service.findById(id);
            if(clienteEliminar==null){
                response.put("mensaje","El cliente que deseas eliminar no exite");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            service.delete(clienteEliminar.getId());

        }catch (DataAccessException e){
            response.put("Mensaje","Error al eliminar el cliente");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El cliente eliminado con éxito!");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
