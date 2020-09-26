package com.evoltech.library.repository;

import com.evoltech.library.model.jpa.Coleccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ColeccionRepository extends JpaRepository<Coleccion, Long> {

    List<Coleccion> findByNombre(String nombre);
    List<Coleccion> findByNombreAndEdicion(String nombre, String edicion);
    List<Coleccion> findByNombreAndNivelAndEdicion(String nombre, String nivel, String edicion);
    List<Coleccion> findByGuid(String guid);
}