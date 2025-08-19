package com.UnFold.repository;

import com.UnFold.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository
        extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoriaDescripcion(String descripcion);

}
