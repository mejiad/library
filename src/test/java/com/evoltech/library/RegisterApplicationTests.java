package com.evoltech.library;

import com.evoltech.library.model.jpa.*;
import com.evoltech.library.repository.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
// import org.junit.platform.commons.logging.Logger;
// import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterApplicationTests {

	Logger log = LoggerFactory.getLogger(RegisterApplicationTests.class);

	@Autowired
	private EscuelaRepository escuelaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private LicenciaRepository licenciaRepository;

	@Test
	@Order(1)
	void contextLoads() {
	}

	@Test
    @Order(2)
    @Transactional
	@Commit
	void addEscuela() {
		log.info("<<<<<<<<<<<<<<<<<<<<<  2   >>>>>>>>>>>>>>>>>>>>>>>>");
		Escuela escuela = new Escuela("Escuela Test");

		Usuario usuario = new Usuario("email test", "Maestra Test", "2244");
		escuela.addUsuario(usuario);

		escuelaRepository.save(escuela);

		usuarioRepository.save(usuario);

		escuelaRepository.count() ;
		assert(escuela.getUsuarios().size() > 0);
	}

	@Test
	@Order(5)
    void findEscuela() {
		log.info("<<<<<<<<<<<<<<<<<<<<<  5   >>>>>>>>>>>>>>>>>>>>>>>>");
		Optional<Escuela> optionalList = escuelaRepository.findById(1L);
		Escuela escuela = null;
		if (optionalList.isPresent()) {
			escuela = optionalList.get();
			log.info("Valor de escuela nombre: " + escuela.getNombre());
		}

		assertEquals("Uno", escuela.getNombre());
	}


	@Test
	@Order(6)
	@Transactional
	void findEscuelaGrupo() {
		log.info("<<<<<<<<<<<<<<<<<<<<<  6   >>>>>>>>>>>>>>>>>>>>>>>>");

		List<Escuela> escuelaList = escuelaRepository.findByNombre("Uno");
		Escuela escuela = null;
		if (escuelaList.size() > 0){
		    escuela = escuelaList.get(0);
			log.info("Valor de escuela nombre: " + escuela.getNombre());
        }

		List<Grupo> listGrupos = escuela.getGrupos();
		log.info("Lista grupos size: " + listGrupos.size());

		assertEquals(true, listGrupos.size() > 0 );
	}

	@Test
	@Order(8)
	@Transactional
	@Commit
	void addLicencia() {
		log.info("<<<<<<<<<<<<<<<<<<<<<  8   >>>>>>>>>>>>>>>>>>>>>>>>");
		Optional<Escuela> optionalEscuela = escuelaRepository.findById(1L);
		if(optionalEscuela.isPresent()) {
			Escuela escuela = optionalEscuela.get();
			Licencia licencia = new Licencia();
			licencia.setNombre("Licencia Test");
			escuela.addLicencia(licencia);
			licenciaRepository.save(licencia);
			assertEquals(true, true);
		} else {
			log.warn("No hay escuela");
			assertEquals(true, false);
		}
	}
}
