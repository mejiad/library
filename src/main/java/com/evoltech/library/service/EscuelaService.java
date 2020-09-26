package com.evoltech.library.service;

import com.evoltech.library.controller.EscuelaController;
import com.evoltech.library.model.jpa.Coleccion;
import com.evoltech.library.model.jpa.Documento;
import com.evoltech.library.repository.ColeccionRepository;
import com.evoltech.library.repository.EscuelaRepository;
import com.evoltech.library.repository.LicenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.stream.Collectors.groupingBy;

@Service
public class EscuelaService {

    Logger log = LoggerFactory.getLogger(EscuelaService.class);

    @Autowired
    EscuelaRepository escuelaRepository;

    @Autowired
    LicenciaRepository licenciaRepository;

    @Autowired
    ColeccionRepository coleccionRepository;

   public Map<String, List<Coleccion>> getColeccionesByEdicion(String nombre){
        List<Coleccion> listColecciones = coleccionRepository.findByNombre(nombre);
       Map<String, List<Coleccion>> coleccionesByEdicion =
               listColecciones.stream().collect(groupingBy(Coleccion::getEdicion));

       for (String key: coleccionesByEdicion.keySet() ) {
           List<Coleccion> colList = coleccionesByEdicion.get(key);
       }
       return coleccionesByEdicion;
    }

    public Map<String, List<Documento>> getDocumentosByColeccion(String guid){

       Coleccion col = coleccionRepository.findByGuid(guid).stream().findFirst()
               .orElseThrow();
        List<Documento> listDocumentos = col.getDocumentos();
        Map<String, List<Documento>> documentosByTipo =
        listDocumentos.stream().collect(groupingBy(Documento::getCategoria));

        return documentosByTipo;
    }
}
