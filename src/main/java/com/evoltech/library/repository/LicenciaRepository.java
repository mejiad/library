package com.evoltech.library.repository;

import com.evoltech.library.model.jpa.Licencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LicenciaRepository extends JpaRepository<Licencia, Long> {
    List<Licencia> findByNombre(String nombre);
}
