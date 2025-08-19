package com.UnFold.service;

import com.UnFold.domain.Rol;
import com.UnFold.repository.RolRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolService {

    //Se crea una única instancia de manera automática
    @Autowired
    private RolRepository rolRepository;

    @Transactional(readOnly = true)
    public List<Rol> getRols() {
        var lista = rolRepository.findAll();
        return lista;
    }

    @Transactional(readOnly = true)
    public Rol getRol(Rol rol) {
        return rolRepository .findById(rol.getIdRol()).orElse(null);
    }

    @Transactional
    public void save(Rol rol) {
        rolRepository.save(rol);

    }
    @Transactional
    public boolean delete(Rol rol) {
        try {
        rolRepository.delete(rol);
        rolRepository.flush();
        return true;
        } catch (Exception e) {
            return false;
            
        }

    }

}
