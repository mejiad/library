package com.evoltech.library.repository;

import com.evoltech.library.model.jpa.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    List<Usuario> findByNombre(String nombre);
    public Usuario findByEmailIgnoreCase(String email);

    List<Usuario> findByEmail(String email);
}