package com.UnFold.repository;

import com.UnFold.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

 
    List<Categoria> findByActivoTrue();
    
    
    Categoria findByDescripcion(String descripcion);
}