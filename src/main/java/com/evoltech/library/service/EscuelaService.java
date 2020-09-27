package com.evoltech.library.service;

import com.evoltech.library.model.jpa.*;
import com.evoltech.library.repository.ColeccionRepository;
import com.evoltech.library.repository.UsuarioRepository;
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
    ColeccionRepository coleccionRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

   public Map<String, List<Coleccion>> getColeccionesByNameOrderByEdicion(String nombre){
        List<Coleccion> listColecciones = coleccionRepository.findByNombre(nombre);
       return listColecciones.stream().collect(groupingBy(Coleccion::getEdicion));
    }

    public Map<String, List<Documento>> getDocumentosByColeccion(String guid){
        Coleccion col = coleccionRepository.findByGuid(guid).stream().findFirst()
               .orElseThrow();
        List<Documento> listDocumentos = col.getDocumentos();

        return listDocumentos.stream().collect(groupingBy(Documento::getCategoria));
    }

    public ArrayList<Coleccion> getLicenciasForUser(Long id) {
        Usuario usuario = usuarioRepository.getOne(id);
        Escuela escuela = usuario.getEscuela();
        List<Licencia> licencias = escuela.getLicencias();
        ArrayList<Coleccion> colecciones = new ArrayList<>();
        for (Licencia licencia : licencias) {
            colecciones.add(licencia.getColeccion());
        }
       return colecciones;
    }

    public List<Coleccion> filterColecciones(ArrayList<Coleccion> colecciones, ArrayList<Coleccion> coleccionesByEscuela) {
        for (Coleccion col : coleccionesByEscuela ) {
            if (colecciones.contains(col)){
                col.setEnable(true);
                log.warn("Si se encuentra: " + col.getNombre());
            }
        }
        return colecciones;
    }

}
