package com.leancode.datajpa.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // Con la configuracion en el "application.properties" podemos configurar que se creen las tablas
    // de manera automatica, sin usar sql. Pero debemos marcar nuestro POJO con las anotaciones 
    // correspondientes.

    private static final long serialVersionUID = 1L;

}
