package com.UnFold.service;

import com.UnFold.domain.Producto;
import java.util.List;

public interface ProductoService {
    
    public List<Producto> getProductos(boolean activos);
    
    public List<Producto> getProductosByCategoriaDescripcion(String descripcionCategoria, boolean activos);

    public Producto getProducto(Producto producto);

    public void save(Producto producto);

    public void delete(Producto producto);
    
}

