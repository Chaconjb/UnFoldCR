package com.UnFold.repository;

import com.UnFold.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository
        extends JpaRepository<Producto, Long> {

    
    public List<Producto> findByCategoria_DescripcionAndActivo(String descripcionCategoria, boolean activo);
}