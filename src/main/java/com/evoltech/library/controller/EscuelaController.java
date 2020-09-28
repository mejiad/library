package com.evoltech.library.controller;

import com.evoltech.library.model.jpa.Coleccion;
import com.evoltech.library.model.jpa.Documento;
import com.evoltech.library.model.jpa.Escuela;
import com.evoltech.library.repository.ColeccionRepository;
import com.evoltech.library.repository.DocumentoRepository;
import com.evoltech.library.repository.EscuelaRepository;
import com.evoltech.library.service.EscuelaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class EscuelaController {

    Logger log = LoggerFactory.getLogger(EscuelaController.class);

    @Autowired
    EscuelaRepository escuelaRepository;

    @Autowired
    ColeccionRepository coleccionRepository;

    @Autowired
    DocumentoRepository documentoRepository;

    @Autowired
    EscuelaService escuelaService;

    @RequestMapping(value = "/", method= RequestMethod.GET)
    public String homepage(Model model){
        List<Escuela> escuelas = escuelaRepository.findAll();
        model.addAttribute("escuelas", escuelas);
        return "HomePage";
    }

    @RequestMapping(value = "/home", method= RequestMethod.GET)
    public String home(Model model){
        List<Escuela> escuelas = escuelaRepository.findAll();
        model.addAttribute("escuelas", escuelas);
        return "HomePage";
    }

    @RequestMapping(value = "/colecciones/{coleccion}", method= RequestMethod.GET)
    public String colecciones(@PathVariable String coleccion, HttpServletRequest request, Model model){
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        log.warn(" Coleccion: " + coleccion);
        log.warn(" Principal: " + principal.getName());

        String nombre = "El ABC";
        if (coleccion != null && coleccion.length() > 0) {
            nombre = coleccion;
        }
        List<Coleccion> listTotal = coleccionRepository.findByNombre(nombre);
        Coleccion col;
       // escuelaService.getColeccionesByNameOrderByEdicion(nombre);

        /*
        if (list != null && list.size() > 0) {
            col = list.stream().findFirst().get();
            List<Documento> listDocs = col.getDocumentos();
        } else {
            col = null;
        }
        */

        Long id = escuelaService.findIdByEmail(userName);
        List<Coleccion> coleccionesForUser = escuelaService.getColeccionesByLicenciasForUser(id);

        Map<String, List<Coleccion>> colecciones = escuelaService.getColeccionesByNameOrderByEdicion(nombre, userName);

        model.addAttribute("colecciones", colecciones);
        return "Colecciones";
    }

    @RequestMapping(value = "/documentos/{guid}", method= RequestMethod.GET)
    public String documentos(@PathVariable String guid, Model model){
        // esperamos un map con la categoria el documento
        Map<String, List<Documento>> documentosByCategoria = escuelaService.getDocumentosByColeccion(guid);
        model.addAttribute("documentos", documentosByCategoria );

        return "Documentos";
    }

    @RequestMapping(value = "/documento/{guid}", method=RequestMethod.GET)
    public String documento(@PathVariable String guid, Model model){

        Documento documento = documentoRepository.findByGuid(guid);
        model.addAttribute("documento", documento);

        return "Documento";
    }


}
