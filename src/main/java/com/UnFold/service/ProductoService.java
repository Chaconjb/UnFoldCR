
package com.UnFold.service;

import com.UnFold.domain.Producto;
import com.UnFold.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getProductos(boolean incluirInactivos) {
        List<Producto> productos = productoRepository.findAll();
        if (!incluirInactivos) {
            productos.removeIf(p -> !p.isActivo());
        }
        return productos;
    }

    public void save(Producto producto) {
        productoRepository.save(producto);
    }

    public void delete(Producto producto) {
        productoRepository.delete(producto);
    }

    public Producto getProducto(Producto producto) {
        return productoRepository.findById(producto.getIdProducto()).orElse(null);
    }

    public List<Producto> getProductosByCategoriaDescripcion(String descripcion, boolean soloActivos) {
        return productoRepository.findAll().stream()
                .filter(p -> p.getCategoria() != null &&
                             descripcion.equalsIgnoreCase(p.getCategoria().getDescripcion()) &&
                             (soloActivos ? p.isActivo() : true))
                .collect(Collectors.toList());
    }
}
