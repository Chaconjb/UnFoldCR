/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.UnFold.service;

import java.util.List;
import com.UnFold.domain.Usuario;
/**
 *
 * @author campo
 */
public interface UsuarioService {
    
    public List<Usuario> getUsuarios(boolean activos);

    public Usuario getUsuario(Usuario usuario);

    public void save(Usuario usuario);

    public void delete(Usuario usuario);
    
}
