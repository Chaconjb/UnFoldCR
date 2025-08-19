package com.UnFold.service;

import com.UnFold.domain.Categoria;
import com.UnFold.repository.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Categoria> getCategorias(boolean activos) {
        var lista = categoriaRepository.findAll();

        if (activos) {
            // Usa el stream para filtrar de forma más eficiente
            return lista.stream().filter(Categoria::isActivo).toList();
        }
        return lista;
    }

    @Transactional(readOnly = true)
    public Categoria getCategoria(Categoria categoria) {
        return categoriaRepository.findById(categoria.getIdCategoria())
                .orElse(null);
    }

    @Transactional
    public void save(Categoria categoria) {
        categoriaRepository.save(categoria);
    }

    @Transactional
    public boolean delete(Categoria categoria) {
        try {
            categoriaRepository.delete(categoria);
            // El flush asegura que la eliminación se ejecute de inmediato, lo cual es útil para la prueba.
            categoriaRepository.flush(); 
            return true;
        } catch (Exception e) {
            // Manejo del error en caso de que la eliminación falle
            return false;
        }
    }
}