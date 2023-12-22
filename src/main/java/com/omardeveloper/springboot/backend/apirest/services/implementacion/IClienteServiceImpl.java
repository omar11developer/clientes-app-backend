package com.omardeveloper.springboot.backend.apirest.services.implementacion;

import com.omardeveloper.springboot.backend.apirest.model.entity.Cliente;
import com.omardeveloper.springboot.backend.apirest.repository.IClienteRepository;
import com.omardeveloper.springboot.backend.apirest.services.contrato.ICLienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IClienteServiceImpl implements ICLienteService {

    private final IClienteRepository repository;

    public IClienteServiceImpl(IClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
