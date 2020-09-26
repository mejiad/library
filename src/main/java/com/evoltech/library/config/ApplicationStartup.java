package com.evoltech.library.config;

import com.evoltech.library.model.jpa.*;
import com.evoltech.library.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    Logger log = LoggerFactory.getLogger(ApplicationStartup.class);

    @Autowired
    EscuelaRepository escuelaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ColeccionRepository coleccionRepository;

    @Autowired
    DocumentoRepository documentoRepository;

    private int ESCUELAS = 10;
    private int MAX_USUARIOS = 5;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event){
        log.warn("<<<<<<<<<<<<<<<<<<<<<<<<    on Application ready   >>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.warn("<<<<<<<<<<<<<<<<<<<<<<<<    Init database     >>>>>>>>>>>>>>>>>>>>>>>>>>");

        createColecciones();
        createEscuelas(ESCUELAS);

        log.warn("<<<<<<<<<<<<<<<<<<<    Inicializacion terminada database  >>>>>>>>>>>>>>>>");
    }


    private void createColecciones(){
        // El ABC
        Documento documento;
        Coleccion coleccion;
        //+<<<<<<<<<<<<<<<<<<<<<<<<<<<   anc nivel 1 ed 2017 >>>>>>>>>>>>>>>>>>>>>>>>>>>+
        coleccion = new Coleccion("El ABC", "Nivel 1", "Ed. 2015");

        documento = new Documento("Nombre del documento 01",
                "Libro",
                "Descripción del documento 01",
                "/pdf/pdf01.pdf",
                "pdf",
                "01-01-2020",
                null);

        documentoRepository.save(documento);
        coleccion.addDocumento(documento);

        // documento
        documento = new Documento("Nombre del documento 02",
                "Guía Pedagógica",
                "Descripción del documento 02",
                "/pdf/pdf01.pdf",
                "pdf",
                "01-01-2020",
                null);

        documentoRepository.save(documento);
        coleccion.addDocumento(documento);
        coleccionRepository.save(coleccion);

        //  primera coleccion

        //+<<<<<<<<<<<<<<<<<<<<<<<<<<<   anc nivel 1 ed 2017 >>>>>>>>>>>>>>>>>>>>>>>>>>>+
        coleccion = new Coleccion("El ABC", "Nivel 1", "Ed. 2017");
        documento = new Documento("Nombre del documento 03",
                "Libro",
                "Descripción del documento 02",
                "pdf/pdf02.pdf",
                "pdf",
                "01-01-2020",
                null);
        documentoRepository.save(documento);
        coleccion.addDocumento(documento);
        coleccionRepository.save(coleccion);

        //+<<<<<<<<<<<<<<<<<<<<<<<<<<<   anc nivel 1 ed 2017 >>>>>>>>>>>>>>>>>>>>>>>>>>>+
        coleccion = new Coleccion("El ABC", "Nivel 1", "Ed. 2017");
        coleccion = new Coleccion("El ABC", "Nivel 2", "Ed. 2015");
        coleccionRepository.save(coleccion);
        coleccion = new Coleccion("El ABC", "Nivel 2", "Ed. 2017");
        coleccionRepository.save(coleccion);

        coleccion = new Coleccion("El ABC", "Nivel 3", "Ed. 2015");
        coleccionRepository.save(coleccion);
        coleccion = new Coleccion("El ABC", "Nivel 3", "Ed. 2017");
        coleccionRepository.save(coleccion);

        // EL 123
        coleccion = new Coleccion("El 123", "Nivel 1", "Ed. 2015");
        coleccionRepository.save(coleccion);
        coleccion = new Coleccion("El 123", "Nivel 1", "Ed. 2017");
        coleccionRepository.save(coleccion);

        coleccion = new Coleccion("El 123", "Nivel 2", "Ed. 2015");
        coleccionRepository.save(coleccion);
        coleccion = new Coleccion("El 123", "Nivel 2", "Ed. 2017");
        coleccionRepository.save(coleccion);

        coleccion = new Coleccion("El 123", "Nivel 3", "Ed. 2015");
        coleccionRepository.save(coleccion);
        coleccion = new Coleccion("El 123", "Nivel 3", "Ed. 2017");
        coleccionRepository.save(coleccion);
    }

    private void createEscuelas(int num){
        for(int i = 0; i < num; i++){
            String name = "Escuela Demo " + i;
            Escuela escuela = new Escuela(name);
            List<Coleccion> listColeccion = coleccionRepository.findByNombreAndNivelAndEdicion("El ABC",
                    "Nivel 1", "Ed. 2015" );
            Coleccion coleccion;
            if (listColeccion.size() > 0) {
                coleccion = listColeccion.get(0);
                Licencia licencia = new Licencia("001", coleccion, true);
                escuela.addLicencia(licencia);
            }
            for (int k = 0; k < MAX_USUARIOS; k++){
                Usuario usuario = createUsuario();
                escuela.addUsuario(usuario);
            }
            escuelaRepository.save(escuela);
        }
    }

    private String[] lastName = {"García", "González", "Rodríguez", "Fernández", "López",
            "Martinez", "Sánchez", "Pérez", "Gómez", "Jiménez",
            "Ruiz", "Hernández", "Díaz", "Moreno", "Romero"};

    private String[] names = {"Juan", "Luis", "José", "Maria", "Valeria",
            "Elba", "Gretel", "Ilsa", "Olivia", "Lara",
            "Pablo", "Diego", "Gabriel", "Angel", "Julián"};

    private int MAX_MAESTRAS = 15;

    private Usuario createUsuario() {
        Random rand = new Random();
        int idxName = rand.nextInt(names.length);
        int idxLastname = rand.nextInt(lastName.length);
        int unico = rand.nextInt(1000);
        String email = names[idxName] + "." + lastName[idxLastname] + "." + unico + "@gmail.com";
        String name = names[idxName] + " " + lastName[idxLastname];
        Usuario usuario = new Usuario(email, name);
        return usuario;
    }

    private void createMaestras() {
        Usuario[] usuarios = new Usuario[MAX_MAESTRAS];
        Random rand = new Random();
        for(int i = 0; i < MAX_MAESTRAS; i++) {
            int idxName = rand.nextInt(15);
            int idxLastname = rand.nextInt(15);
            int randName = rand.nextInt(30);
            String email = names[idxName] + "." + lastName[idxLastname] + randName + "@gmail.com";
            String name = names[idxName] + " " + lastName[idxLastname] + " " + randName;
            usuarios[i] = new Usuario(email, name);
        }
    }

    private int MAX_DOCUMENTOS = 6;
    String[] docsNombres = {"Actividades", "Guía Pedagógica", "Tareas", "Ejercicios"};

    String[] docsDescr = {"Actividades dentro del salon de clases", "Guía Pedagógica para la maestra", "Tareas y ejercicios para hacer en casa", "Ejercicios dentro del salón de clase"};

    String[] mimeType = {"pdf", "audio", "video", "imagen"};

    String[] pdfLinks = {
            "http://localhost:8080/pdf/PREE-1-MIALBUM-BAJA.pdf",
            "http://localhost:8080/pdf/PREE-2-MIALBUM-BAJA.pdf",
            "http://localhost:8080/pdf/PREE-3-MIALBUM-BAJA.pdf"
    };

    String[] audioLinks = {
            "http://localhost:8080/audios/audio00.mp3",
            "http://localhost:8080/audios/audio01.mp3",
            "http://localhost:8080/audios/audio02.mp3"
    };

   String[] videoLinks = { "https://www.youtube.com/embed/9WNCpGxZ0Gg",
            "https://www.youtube.com/embed/AgoJOI0A3e8",
           "https://www.youtube.com/embed/iaaZKuHXOVU",
           "https://www.youtube.com/embed/VODa1HYGHn4",
           "https://www.youtube.com/embed/2vF3H6mx3eM"
   };

    String[] imagenLinks = {
            "https://www.youtube.com/embed/9WNCpGxZ0Gg",
            "https://www.youtube.com/embed/9WNCpGxZ0Gg",
            "https://www.youtube.com/embed/9WNCpGxZ0Gg",
            "https://www.youtube.com/embed/9WNCpGxZ0Gg"
    };
    Map<String, String[]> media = Map.of(
            "pdf", pdfLinks,
            "audio", audioLinks,
            "video", videoLinks,
            "imagen", imagenLinks
    );

    /*
    private void addDocumentos(Planeacion planeacion){
        Random rand = new Random();
        int numDocs = rand.nextInt(MAX_DOCUMENTOS) + 1;
        for(int i = 0; i < numDocs; i++) {
            int idxNombre = rand.nextInt(docsNombres.length);
            // public Documento(String nombre, String descripcion, String uri, String mimeType, String fechaStr){
            String mime = getMimetype();
            String link = getLink(mime);
            String nombre = docsNombres[idxNombre];
            String descr = docsDescr[idxNombre];
            String[] archivos = new String[]{ "http://localhost:8080/images/back01_640x480.jpg",
                    "http://localhost:8080/images/back02_640x480.jpg",
                    "http://localhost:8080/images/back03_640x480.jpg"
                };
           Documento doc = new Documento(nombre, descr,  link,  mime, "20-09-2020", archivos);

           // documentoRepository.save(doc);
           planeacion.addDocumento(doc);
           // planeacionRepository.save(planeacion);
        }
    }
     */

    private String getMimetype() {
        Random rand = new Random();

        Set<String> keys = media.keySet();
        int len = media.keySet().size();
        int selected = rand.nextInt(len);
        String arr[] = new String[len];

        int i = 0;
        for (String x : keys){
            arr[i++] = x;
        }
        return arr[selected];
    }

    private String getLink(String mime){
        Random rand = new Random();
        String arr[] = media.get(mime);
        int len = arr.length;
        int selected = rand.nextInt(len);
        return arr[selected];
    }

}
