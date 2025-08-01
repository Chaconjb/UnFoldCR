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
            return lista.stream().filter(Producto::isActivo).toList();
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

    @Transactional
    public boolean delete(Producto producto) {
        try {
            productoRepository.delete(producto);
            productoRepository.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Transactional(readOnly = true)
    public List<Producto> getProductosByCategoriaDescripcion(String descripcion, boolean activos) {
        var lista = productoRepository.findByCategoriaDescripcion(descripcion);
        if (activos) {
            return lista.stream().filter(Producto::isActivo).toList();
        }
        return lista;
    }
}
