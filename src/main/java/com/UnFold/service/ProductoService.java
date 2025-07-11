package com.UnFold.service;

import com.UnFold.domain.Producto;
import java.util.List;

public interface ProductoService {

    public List<Producto> getProductos(boolean activos);

    public Producto getProducto(Producto producto);

    public void save(Producto producto);

    public List<Producto> getProductosByCategoriaDescripcion(String descripcionCategoria, boolean activos);

    public void delete(Producto producto);

}
