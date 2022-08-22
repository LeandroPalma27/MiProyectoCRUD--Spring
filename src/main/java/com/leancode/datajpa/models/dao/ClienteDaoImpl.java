package com.leancode.datajpa.models.dao;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.leancode.datajpa.models.entity.Cliente;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// <========= POR AHORA ESTO ES JPA PURO (SIN HIBERNATE) =========>

@Repository
public class ClienteDaoImpl implements IClienteDaoEm {

    // Esta anotacion permite crear un contexto de persistencia desde el contenedor de spring.
    // Este contexto se encarga de gestionar las transacciones, ya sea unica o de manera extendida.
    // Gestiona las confirmaciones, las reversiones y gestion de errores.
    // Es seguro de usar sin limpiar manualmente.
    @PersistenceContext(type=PersistenceContextType.TRANSACTION)


    // EntityManager es parte de la api de persistencia(ligado a ORM) usada en Java.
    // Se usa para el manejo de persistencia con el uso de entidades y objetos mapeados a una base de
    // datos, todo con la finaliad de evitar el uso de codigo SQL.
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<Cliente> findAll() {
        return em.createQuery("from Cliente").getResultList();
    }

    @Override
    // Este save permite crear y editar registros:
    public void save(Cliente cliente) {
        // Si se crea, no pasamos un id al objeto (ya que es IDENTITY), por lo tanto validamos:
        if (cliente.getId() != null && cliente.getId() > 0) {
            // Y si se cumple, ejecutamos:
            em.merge(cliente);
        } else { // Caso contrario:
            // Codigo para guardar fecha local actual, ya sea solo fecha o fecha y hora, de tipo DATE:
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            var fechaActualString = formatter.format(LocalDateTime.now());
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fechaActualString, new ParsePosition(0));
            cliente.setFechaRegistro(date);
    
            // Permite ejecutar un commit de una insercion en nuestra BD, todo manejado por el contexto de
            // persistencia y el entity manager.
            em.persist(cliente);
        }
    }

    @Override
    public Cliente findById(Long id) {
        return em.find(Cliente.class, id);
    }

    @Override
    // Esto no es buena practica porque se suele necesitar tener el objeto que se actualizo.
    public Boolean update(Cliente cliente) {
        var clienteActualizado = em.merge(cliente);
        if (clienteActualizado != null) {
            return true;
        }
        return false;
    }

    @Override
    public void delete(Long id) {
        var cliente = findById(id);
        if (cliente != null) {
            em.remove(cliente);
        }
    }
    
}
