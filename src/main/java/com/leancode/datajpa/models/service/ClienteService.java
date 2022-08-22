package com.leancode.datajpa.models.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.leancode.datajpa.models.dao.IClienteDao;
import com.leancode.datajpa.models.dao.IClienteDaoEm;
import com.leancode.datajpa.models.entity.Cliente;
import com.leancode.datajpa.models.provider.CloudinaryDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// Esto pertenece a un patron de diseño llamado fachada, donde vamos a reunir varios dao (no 
// necesariamente), para asi poder manejar un service por cada entidad y no usar los dao repetidos en
// el controlador.
// Aqui tambien puede ir nuestra logica de negocio.
public class ClienteService implements IClienteService{

    @Autowired
    private IClienteDao clienteDao;

    @Autowired
    private CloudinaryDao cloudinaryDao;

    @Autowired
    private IClienteDaoEm clienteDaoEm;

    @Override
    // Esta anotacion sirve para indicar si es una consulta ORM solo de lectura.
    // Pero no se limita a eso. Se pueden manejar varios comportamientos en la transaccion.
    @Transactional(readOnly = true)
    public Iterable<Cliente> findAll() {
        return clienteDao.findAll();
    }

    @Override
    @Transactional
    // Este metodo retorna un mensaje indicando el estado de la subida del archvivo:
    public Map<String, Object> save(Cliente cliente){
        if (cliente.getFile() != null) {
            var result = cloudinaryDao.upload(cliente.getFile());
            if (result.get("idFoto") != null) {
                cliente.setFotoId(result.get("idFoto").toString());
                cliente.setUrlFoto(result.get("urlFoto").toString());
                clienteDaoEm.save(cliente);
                return result;
            } 
            cliente.setUrlFoto("");
            cliente.setUrlFoto("");
            clienteDaoEm.save(cliente);
            return result;
        }
        // Esto se cumple cuando solo es un edit:
        clienteDaoEm.save(cliente);
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Boolean update(Cliente cliente) {
        return clienteDaoEm.update(cliente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteDaoEm.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable page) {
        return clienteDao.findAll(page);
    }

    @Override
    public List<Cliente> obtenerRegistros() {
        return clienteDaoEm.findAll();
    }

    @Override
    public List<Cliente> findByDni(String dni) {
        return clienteDao.findByDni(dni);
    }

    @Override
    public Cliente obtenerPorId(Long id) {
        return clienteDao.findById(id).get();
    }
    
}
