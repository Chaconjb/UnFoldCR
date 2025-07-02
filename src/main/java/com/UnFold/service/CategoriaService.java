package com.UnFold.service;

import com.UnFold.domain.Categoria;
import com.UnFold.repository.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scripting.support.StandardScriptUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Transactional(readOnly=true)
    public List<Categoria> getCategoria(boolean activos){
        var lista = categoriaRepository.findAll();
        
        if (activos) {
            lista.removeIf(c -> !c.isActivo());
        }
        return lista;
    }
    @Transactional(readOnly=true)
    public Categoria getCategoria(Categoria categoria){
        return categoriaRepository.findById(categoria.getIdCategoria())
                .orElse(null);
        
    }
    
    @Transactional
    public void save (Categoria categoria){
        categoriaRepository.save(categoria);
    }
    @Transactional
    public boolean delete(Categoria categoria){
        try {
            categoriaRepository.delete(categoria);
            categoriaRepository.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
  
    }
    
}
