package com.evoltech.library.repository;

import com.evoltech.library.model.jpa.Escuela;
import com.evoltech.library.model.jpa.Archivo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagenRepository extends JpaRepository<Escuela, Long> {
    List<Archivo> findByNombre(String nombre);
}
