package com.evoltech.library.repository;

import com.evoltech.library.model.jpa.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    List<Documento> findByNombre(String nombre);

    Documento findByGuid(String guid);
}
