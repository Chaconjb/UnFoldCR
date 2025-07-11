package com.UnFold.service;

import com.UnFold.domain.Categoria;
import com.UnFold.repository.CategoriaRepository;
import com.UnFold.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional; 

@Service 
public class CategoriaServiceImpl implements CategoriaService {

   
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @Transactional(readOnly = true) 
    public List<Categoria> getCategorias(boolean activos) {
       
        var lista = categoriaRepository.findAll();

        
        if (activos) {
            
            lista.removeIf(c -> !c.isActivo());
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true) 
    public Categoria getCategoria(Categoria categoria) {
        
        return categoriaRepository.findById(categoria.getIdCategoria()).orElse(null);
    }

    @Override
    @Transactional 
    public void save(Categoria categoria) {
        
        categoriaRepository.save(categoria);
    }

    @Override
    @Transactional 
    public void delete(Categoria categoria) {
       
        categoriaRepository.delete(categoria);
    }
}