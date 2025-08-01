package com.UnFold.repository;

import com.UnFold.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    
     public Usuario findByUsername(String username);
    
}
