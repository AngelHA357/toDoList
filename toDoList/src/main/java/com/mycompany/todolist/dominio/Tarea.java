/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todolist.dominio;

/**
 *
 * @author Jose Armenta, Jose Huerta, Victor Encinas
 * Clase que representa una tarea con una descripción y el estado de completada.
 */
public class Tarea {

    private String descripcion;
    private boolean estaCompletada;

    public Tarea(){
        
    }
    
    /**
     * Constructor que crea una nueva tarea con la descripción dada. La tarea se
     * inicializa como no completada.
     *
     * @param descripcion La descripción de la tarea.
     */
    public Tarea(String descripcion) {
        this.descripcion = descripcion;
        this.estaCompletada = false;
    }

    /**
     * Obtiene la descripción de la tarea.
     *
     * @return La descripción de la tarea.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Indica si la tarea está completada.
     *
     * @return true si la tarea está completada, false en caso contrario.
     */
    public boolean getEstaCompletada() {
        return estaCompletada;
    }

    /**
     * Establece la nueva descripción de la tarea.
     *
     * @param descripcion La nueva descripción de la tarea.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Establece el estado de la tarea como completada o no completada.
     *
     * @param estaCompletada true si la tarea está completada, false en caso
     * contrario.
     */
    public void setEstaCompletada(boolean estaCompletada) {
        this.estaCompletada = estaCompletada;
    }

    /**
     * Devuelve la representación en forma de cadena de la tarea, que es su
     * descripción.
     *
     * @return La descripción de la tarea como cadena.
     */
    @Override
    public String toString() {
        return descripcion;
    }
}
   
