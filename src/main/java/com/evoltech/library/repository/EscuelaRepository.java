package com.evoltech.library.repository;

import com.evoltech.library.model.jpa.Escuela;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EscuelaRepository extends JpaRepository<Escuela, Long> {

    List<Escuela> findByNombre(String nombre);

}
