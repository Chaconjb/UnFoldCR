package com.UnFold.service.impl; // O com.UnFold.service

import com.UnFold.domain.Usuario;
import com.UnFold.repository.UsuarioRepository; // Asegúrate de tener esta interfaz
import com.UnFold.service.UsuarioService;     // Asegúrate de tener esta interfaz
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServices1 implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> getUsuarios(boolean activos) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (activos) {
            usuarios.removeIf(u -> !u.isActivo()); // Asumiendo u.isActivo() en tu clase Usuario
        }
        return usuarios;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario getUsuario(Usuario usuario) {
        return usuarioRepository.findById(usuario.getIdUsuario()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void delete(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }
}