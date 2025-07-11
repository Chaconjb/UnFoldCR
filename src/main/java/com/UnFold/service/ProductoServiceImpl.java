package com.UnFold.service;

import com.UnFold.domain.Producto;
import com.UnFold.repository.ProductoRepository;
import com.UnFold.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional; 

@Service // 
public class ProductoServiceImpl implements ProductoService {

    @Autowired 
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true) 
    public List<Producto> getProductos(boolean activos) {
        var lista = productoRepository.findAll();
        if (activos) {

            lista.removeIf(p -> !p.isActivo());
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Producto getProducto(Producto producto) {
        
        return productoRepository.findById(producto.getIdProducto()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Producto producto) {
        productoRepository.save(producto);
    }

    @Transactional 
    public void delete(Producto producto) {
        productoRepository.delete(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductosByCategoriaDescripcion(String descripcionCategoria, boolean activos) {
        List<Producto> productos = productoRepository.findByCategoriaDescripcion(descripcionCategoria);
        if (activos) {
            productos.removeIf(p -> !p.isActivo());
        }
        return productos;
    }
}