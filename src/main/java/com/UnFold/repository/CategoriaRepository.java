package com.UnFold.repository;
import com.UnFold.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriaRepository
        extends JpaRepository<Categoria, Long>{
    
}
