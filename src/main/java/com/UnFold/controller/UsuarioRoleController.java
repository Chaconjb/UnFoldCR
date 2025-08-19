package com.UnFold.controller;

import com.UnFold.domain.Rol;
import com.UnFold.domain.Role;
import com.UnFold.domain.Usuario;
import com.UnFold.service.RolService;
import com.UnFold.service.RoleService;
import com.UnFold.service.UsuarioService;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario_role")
public class UsuarioRoleController {

    @Autowired
    private RolService RolService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RoleService RoleService;

    @GetMapping("/asignar")
    public String asignar(Usuario usuario, Model model) {
        if (usuario == null) {
            usuario = new Usuario();
        }
        usuario = usuarioService.getUsuarioPorUsername(usuario.getUsername());
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
        }

        var lista = RoleService.getRoles();
        ArrayList<String> rolesDisponibles = new ArrayList<>();
        for (Role r : lista) {
            rolesDisponibles.add(r.getRol());
        }
        var rolesAsignados = usuario.getRoles();
        for (Rol r : rolesAsignados) {
            rolesDisponibles.remove(r.getNombre());
        }

        model.addAttribute("rolesAsignados", rolesAsignados);
        model.addAttribute("rolesDisponibles", rolesDisponibles);
        model.addAttribute("idUsuario", usuario.getIdUsuario());
        model.addAttribute("username", usuario.getUsername());

        return "/usuario_role/asignar";
    }

    @GetMapping("/agregar")
    public String agregar(Model model, Rol rol, Usuario usuario) {
        RolService.save(rol);
        return "redirect:/usuario_role/asignar?username=" + usuario.getUsername();
    }

    @GetMapping("/eliminar")
    public String eliminar(Model model, Rol rol, Usuario usuario) {
        RolService.delete(rol);
        return "redirect:/usuario_role/asignar?username=" + usuario.getUsername();
    }

}
