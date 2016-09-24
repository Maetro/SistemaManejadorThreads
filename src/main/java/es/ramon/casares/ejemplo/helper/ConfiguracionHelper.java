/**
 * ConfiguracionHelper.java 24-sep-2016
 *
 * Copyright 2016 RAMON CASARES.
 * @author Ramon.Casares.Porto@gmail.com
 */
package es.ramon.casares.ejemplo.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Class ConfiguracionHelper.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ConfiguracionHelper {

    /** The distancia entre snapshots. */
    private Integer numeroIteraciones;

    /** Los segundos entre instantes. */
    private Integer numeroTareas;

    /**
     * Gets the numero iteraciones.
     *
     * @return the numero iteraciones
     */
    public Integer getNumeroIteraciones() {
        return this.numeroIteraciones;
    }

    /**
     * Gets the numero tareas.
     *
     * @return the numero tareas
     */
    public Integer getNumeroTareas() {
        return this.numeroTareas;
    }

    /**
     * Sets the numero iteraciones.
     *
     * @param numeroIteraciones
     *            the new numero iteraciones
     */
    public void setNumeroIteraciones(final Integer numeroIteraciones) {
        this.numeroIteraciones = numeroIteraciones;
    }

    /**
     * Sets the numero tareas.
     *
     * @param numeroTareas
     *            the new numero tareas
     */
    public void setNumeroTareas(final Integer numeroTareas) {
        this.numeroTareas = numeroTareas;
    }
}
