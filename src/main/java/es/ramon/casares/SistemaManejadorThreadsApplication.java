/**
 * SistemaManejadorThreadsApplication.java 24-sep-2016
 *
 * Copyright 2016 RAMON CASARES.
 * @author Ramon.Casares.Porto@gmail.com
 */
package es.ramon.casares;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import es.ramon.casares.ejemplo.ControladorPrincipal;

@SpringBootApplication(scanBasePackages = { "es.ramon.casares" })
public class SistemaManejadorThreadsApplication {

    /** The logger. */
    private static final Logger logger = LoggerFactory.getLogger(SistemaManejadorThreadsApplication.class);

    public static void main(final String[] args) {

        logger.info("Arrancando aplicacion");
        final ApplicationContext ctx = SpringApplication.run(SistemaManejadorThreadsApplication.class, args);

        final ControladorPrincipal controlador = new ControladorPrincipal();

        controlador.ejecutarPrueba();

    }
}
