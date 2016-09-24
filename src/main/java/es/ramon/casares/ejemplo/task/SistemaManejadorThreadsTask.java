/**
 * SistemaManejadorThreadsTask.java 24-sep-2016
 *
 * Copyright 2016 RAMON CASARES.
 * @author Ramon.Casares.Porto@gmail.com
 */
package es.ramon.casares.ejemplo.task;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ramon.casares.ejemplo.manager.SistemaManejadorThreadsManager;

/**
 * The Class SistemaManejadorThreadsTask.
 */
public class SistemaManejadorThreadsTask implements Callable<SistemaManejadorThreadsTask> {
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(SistemaManejadorThreadsTask.class);

    /** The buyer id. */
    private final Integer numeroIteraciones;

    private final Integer identificadorThread;

    private Long resultado = 1L;

    /** The Sistema manejador threads manager. */
    private final SistemaManejadorThreadsManager SistemaManejadorThreadsManager;

    /**
     * Instancia un nuevo responsable negocio comprador task.
     *
     * @param SistemaManejadorThreadsManager
     *            the sistema manejador threads manager
     * @param numeroIteraciones
     *            the numero iteraciones
     * @param identificadorThread
     *            the identificador thread
     */
    public SistemaManejadorThreadsTask(final SistemaManejadorThreadsManager SistemaManejadorThreadsManager,
            final Integer numeroIteraciones, final Integer identificadorThread) {

        this.numeroIteraciones = numeroIteraciones;
        this.identificadorThread = identificadorThread;
        this.SistemaManejadorThreadsManager = SistemaManejadorThreadsManager;
    }

    /**
     * Realizar operacion.
     *
     * @return the sistema manejador threads task
     */
    private SistemaManejadorThreadsTask realizarOperacion() {

        if (SistemaManejadorThreadsTaskEstado.EN_ESPERA.equals(this.SistemaManejadorThreadsManager.getMapaEstados()
                .get(this.identificadorThread))) {
            SistemaManejadorThreadsTask.logger.info("Ejecutando tarea: " + this.identificadorThread);
            this.SistemaManejadorThreadsManager.getMapaEstados().put(this.identificadorThread,
                    SistemaManejadorThreadsTaskEstado.EN_EJECUCION);
            // operaciones a realizar
            for (int c = 1; c <= this.numeroIteraciones; c++) {
                this.resultado = this.resultado + c;
            }
            this.SistemaManejadorThreadsManager.getMapaEstados().put(this.identificadorThread,
                    SistemaManejadorThreadsTaskEstado.TERMINADO);
            SistemaManejadorThreadsTask.logger.info("Tarea terminada: " + this.identificadorThread);
        }

        return this;
    }

    @Override
    public SistemaManejadorThreadsTask call() throws Exception {
        return realizarOperacion();
    }

    /**
     * Gets the resultado.
     *
     * @return the resultado
     */
    public Long getResultado() {
        return this.resultado;
    }
}
