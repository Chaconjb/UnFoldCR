package com.UnFold.service;

import com.UnFold.domain.Categoria;
import java.util.List;

public interface CategoriaService {

    public List<Categoria> getCategorias(boolean activos);

    public Categoria getCategoria(Categoria categoria);

    public void save(Categoria categoria);

 
    public void delete(Categoria categoria);
    
}