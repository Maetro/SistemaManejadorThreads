/**
 * ControladorPrincipal.java 24-sep-2016
 *
 * Copyright 2016 RAMON CASARES.
 * @author Ramon.Casares.Porto@gmail.com
 */
package es.ramon.casares.ejemplo;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.ramon.casares.ejemplo.helper.ConfiguracionHelper;
import es.ramon.casares.ejemplo.manager.SistemaManejadorThreadsManager;

/**
 * The Class ControladorPrincipal.
 */
@RestController
public class ControladorPrincipal {

    private static final int THREADS_CONEXION = Runtime.getRuntime().availableProcessors();

    /** The configuracion. */
    @Autowired
    private ConfiguracionHelper configuracion;

    /** The sistema manejador threads manager. */
    private static SistemaManejadorThreadsManager sistemaManejadorThreadsManager;

    @RequestMapping("/")
    public void ejecutarPrueba() {

        final int numeroTareas = this.configuracion.getNumeroTareas();
        final int numeroEjecuciones = this.configuracion.getNumeroIteraciones();

        ControladorPrincipal.sistemaManejadorThreadsManager = new SistemaManejadorThreadsManager(THREADS_CONEXION,
                numeroTareas,
                numeroEjecuciones);
        ControladorPrincipal.sistemaManejadorThreadsManager.init();
    }

    @RequestMapping("/obtener")
    public String obtenerResultado(@RequestParam(value = "i", defaultValue = "0") final Integer i) {
        try {
            return String
                    .valueOf(ControladorPrincipal.sistemaManejadorThreadsManager.obtenerResultado(i).getResultado());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
