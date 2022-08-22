package com.leancode.datajpa.models.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.leancode.datajpa.models.entity.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClienteService {

    public List<Cliente> obtenerRegistros();
    
    public Iterable<Cliente> findAll();

    public List<Cliente> findByDni(String dni);

    public Page<Cliente> findAll(Pageable page);

    // Debe ser de tipo string para revisar si la subida fue exitosa o no:
    public Map<String, Object> save(Cliente cliente);

    public Cliente findById(Long id);

    public Boolean update(Cliente cliente);

    public void delete(Long id);

    public Cliente obtenerPorId(Long id);

}
