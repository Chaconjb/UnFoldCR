package com.UnFold.service;

import com.UnFold.domain.Constante;
import com.UnFold.repository.ConstanteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConstanteService {

    //Se crea una única instancia de manera automática
    @Autowired
    private ConstanteRepository constanteRepository;

    @Transactional(readOnly = true)
    public List<Constante> getConstantes() {
        var lista = constanteRepository.findAll();
        return lista;
    }

    @Transactional(readOnly = true)
    public Constante getConstante(Constante constante) {

        return constanteRepository.findById(constante.getIdConstante()).orElse(null);
    }

    @Transactional
    public String getValorPorAtributo(String atributo) {
        Constante constante = constanteRepository.findByAtributo(atributo);
        if (constante != null) {
            return constante.getValor();
        } else {
            return "";
        }
    }

    @Transactional
    public void save(Constante constante) {
        constanteRepository.save(constante);

    }

    @Transactional
    public boolean delete(Constante constante) {
        try {
            constanteRepository.delete(constante);
            constanteRepository.flush();
            return true;
        } catch (Exception e) {
            return false;

        }

    }

}
