package com.omardeveloper.springboot.backend.apirest.repository;

import com.omardeveloper.springboot.backend.apirest.model.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface IClienteRepository extends CrudRepository<Cliente,Long> {
}
