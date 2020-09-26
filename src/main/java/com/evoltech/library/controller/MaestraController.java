package com.evoltech.library.controller;

import com.evoltech.library.model.jpa.Usuario;
import com.evoltech.library.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class MaestraController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @RequestMapping(value = "/usuarios", method= RequestMethod.GET)
    public String usuarios(Model model){
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("users", usuarios);
        return "HomePage";
    }
}
