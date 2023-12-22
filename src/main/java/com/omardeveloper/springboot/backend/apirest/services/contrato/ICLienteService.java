package com.omardeveloper.springboot.backend.apirest.services.contrato;

import com.omardeveloper.springboot.backend.apirest.model.entity.Cliente;

import java.util.List;

public interface ICLienteService {
    public List<Cliente> findAll();
    public Cliente findById(Long id);

    public Cliente save(Cliente cliente);

    public void delete(Long id);



}
