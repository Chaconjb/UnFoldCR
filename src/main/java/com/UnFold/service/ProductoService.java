package com.UnFold.service;

import com.UnFold.domain.Producto;
import com.UnFold.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activos) {
        var lista = productoRepository.findAll();
        if (activos) {
            lista.removeIf(p -> !p.isActivo());
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {
        return productoRepository.findById(producto.getIdProducto())
                .orElse(null);
    }

    @Transactional
    public void save(Producto producto) {
        productoRepository.save(producto);
    }

    @Transactional(readOnly = true)
    public List<Producto> getProductosByCategoriaDescripcion(String descripcionCategoria, boolean activos) {
       
        return productoRepository.findByCategoria_DescripcionAndActivo(descripcionCategoria, activos);
    }

    @Transactional
    public void delete(Producto producto) {
        productoRepository.delete(producto);
    }
}