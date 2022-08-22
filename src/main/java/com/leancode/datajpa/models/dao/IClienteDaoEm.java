package com.leancode.datajpa.models.dao;

import java.util.List;

import com.leancode.datajpa.models.entity.Cliente;

public interface IClienteDaoEm {
    
    public List<Cliente> findAll();

    public void save(Cliente cliente);

    public Cliente findById(Long id);

    public Boolean update(Cliente cliente);

    public void delete(Long id);
    
}
