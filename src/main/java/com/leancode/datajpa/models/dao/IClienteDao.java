package com.leancode.datajpa.models.dao;

import java.util.List;
import java.util.Optional;

import com.leancode.datajpa.models.entity.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{
 
    // Podemos implementar querys basados en ORM y JPA:
    /* @Query("") */
    // Y tambien querys nativos SQL:
    /* @Query(value = "") */
    /* public void findByXXXXXXX(String XXXXXX);  */

    @Query(value = "SELECT * FROM clientes WHERE dni like :dni", nativeQuery = true)
    public List<Cliente> findByDni(@Param("dni") String dni);

}
