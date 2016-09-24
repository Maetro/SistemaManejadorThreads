/**
 * SistemaManejadorThreadsManager.java 24-sep-2016
 *
 * Copyright 2016 RAMON CASARES.
 * @author Ramon.Casares.Porto@gmail.com
 */
package es.ramon.casares.ejemplo.manager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import es.ramon.casares.ejemplo.task.SistemaManejadorThreadsTask;
import es.ramon.casares.ejemplo.task.SistemaManejadorThreadsTaskEstado;

/**
 * The Class SistemaManejadorThreadsManager.
 */
public class SistemaManejadorThreadsManager {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(SistemaManejadorThreadsManager.class);

    /** The executor principal. - Se encargará de ir buscando la informacion secuencialmente de los compradores. */
    private ExecutorService executorPrincipal;

    private final Integer numeroTareas;
    private final int threadpool;

    /*
     * The executor reservado.- Reservado por si el usuario busca un comprador que esta en espera, se utilizará este
     * worker
     */
    private ExecutorService executorReservado;

    /** The task list. Mapa de comprador a la promesa de sus datos */
    Map<Integer, FutureTask<SistemaManejadorThreadsTask>> taskList = new ConcurrentHashMap<Integer, FutureTask<SistemaManejadorThreadsTask>>();

    /** Este mapa evitara que los dos ejecutores intenten. */
    protected Map<Integer, SistemaManejadorThreadsTaskEstado> mapaEstados = new ConcurrentHashMap<Integer, SistemaManejadorThreadsTaskEstado>();

    /** The id seccion. */
    private final Integer numeroEjecuciones;

    /**
     * Instantiates a new sistema manejador threads manager.
     *
     * @param threadpool
     *            the threadpool
     * @param numeroTareas
     *            the numero tareas
     * @param numeroEjecuciones
     *            the numero ejecuciones
     */
    public SistemaManejadorThreadsManager(final int threadpool, final Integer numeroTareas,
            final Integer numeroEjecuciones) {
        this.numeroTareas = numeroTareas;
        this.numeroEjecuciones = numeroEjecuciones;
        this.threadpool = threadpool;
    }

    /**
     * Inicializa.
     */
    public void init() {

        // Create a new ExecutorService with 2 thread to execute and store the Futures
        SistemaManejadorThreadsManager.logger
                .debug("Empezamos la creacion de los threads para las tareas. ThreadPool: " + this.threadpool);
        final StopWatch clock = new StopWatch("Reloj de creacion de los threads ");
        clock.start();
        // Operaciones a realizar.
        this.executorPrincipal = Executors.newFixedThreadPool(this.threadpool - 1);
        this.executorReservado = Executors.newFixedThreadPool(1);

        for (int i = 0; i < this.numeroTareas; i++) {
            SistemaManejadorThreadsManager.logger
                    .info("Task creado para la tarea: " + i);
            final FutureTask<SistemaManejadorThreadsTask> futureTaskComprador = new FutureTask<SistemaManejadorThreadsTask>(
                    new SistemaManejadorThreadsTask(this, this.numeroEjecuciones, i));
            this.taskList.put(i, futureTaskComprador);
            this.getMapaEstados().put(i, SistemaManejadorThreadsTaskEstado.EN_ESPERA);

        }
        clock.stop();
        clock.prettyPrint();
        for (final FutureTask<SistemaManejadorThreadsTask> tareaFutura : this.taskList.values()) {
            this.executorPrincipal.execute(tareaFutura);
        }

    }

    /**
     * Gets the mapa estados.
     *
     * @return the mapa estados
     */
    public Map<Integer, SistemaManejadorThreadsTaskEstado> getMapaEstados() {
        return this.mapaEstados;
    }

    /**
     * Obtener comprador.
     *
     * @param identificadorTarea
     *            buyer id
     * @return the responsable negocio comprador task
     * @throws InterruptedException
     *             de interrupted exception
     * @throws ExecutionException
     *             de execution exception
     */
    public SistemaManejadorThreadsTask obtenerResultado(final Integer identificadorTarea) throws InterruptedException,
            ExecutionException {
        if (this.getMapaEstados().containsKey(identificadorTarea)) {
            switch (this.getMapaEstados().get(identificadorTarea)) {
            case EN_ESPERA:
                // En espera utilizaremos el executor secundario
                final FutureTask<SistemaManejadorThreadsTask> futureTaskComprador = new FutureTask<SistemaManejadorThreadsTask>(
                        new SistemaManejadorThreadsTask(this, this.numeroEjecuciones, identificadorTarea));
                this.taskList.put(identificadorTarea, futureTaskComprador);
                this.executorReservado.execute(futureTaskComprador);
                return this.taskList.get(identificadorTarea).get();

            case EN_EJECUCION:
                // Terminará antes ya que ya se encuentra en ejecucion
                return this.taskList.get(identificadorTarea).get();
            case TERMINADO:
                // El valor ya se encuentra en la promesa
                return this.taskList.get(identificadorTarea).get();
            default:
                return this.taskList.get(identificadorTarea).get();
            }
        }
        return null;

    }
}
