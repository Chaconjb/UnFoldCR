package com.UnFold.service.impl; // O com.UnFold.service

import com.UnFold.domain.Producto;
import com.UnFold.repository.ProductoRepository; // Asegúrate de tener esta interfaz
import com.UnFold.service.ProductoService;     // Asegúrate de tener esta interfaz
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; // Necesario para el método getProductosByCategoriaDescripcion

@Service
public class ProductoServices1 implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activos) {
        List<Producto> productos = productoRepository.findAll();
        if (activos) {
            productos.removeIf(p -> !p.isActivo()); // Asumiendo p.isActivo() en tu clase Producto
        }
        return productos;
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

    @Override
    @Transactional
    public void delete(Producto producto) {
        productoRepository.delete(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> getProductosByCategoriaDescripcion(String descripcionCategoria, boolean activos) {
        // Esta es una implementación básica. Si tu ProductoRepository puede hacer la consulta
        // directamente en la base de datos (ej. con un método como findByCategoriaDescripcionAndActivo),
        // sería más eficiente usarlo aquí.
        // Ejemplo: return productoRepository.findByCategoriaDescripcionAndActivo(descripcionCategoria, activos);

        List<Producto> productos = productoRepository.findAll(); // Obtiene todos los productos
        return productos.stream()
                .filter(p -> p.getCategoria() != null && // Asegúrate de que el producto tiene una categoría
                             p.getCategoria().getDescripcion() != null &&
                             p.getCategoria().getDescripcion().equalsIgnoreCase(descripcionCategoria) &&
                             p.isActivo() == activos) // Asumiendo p.isActivo()
                .collect(Collectors.toList());
    }
}