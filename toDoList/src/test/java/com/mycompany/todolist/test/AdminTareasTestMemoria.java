/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todolist.test;

import com.mycompany.todolist.dominio.AdminTareas;
import com.mycompany.todolist.dominio.Tarea;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Jose Armenta, Jose Huerta, Victor Encinas
 */
public class AdminTareasTestMemoria extends AdminTareas {

    private List<Tarea> tareasEnMemoria = new ArrayList<>();

    /**
     * Constructor de la clase AdminTareasTestMemoria. Inicializa el AdminTareas
     * con una conexión nula, ya que no se necesita para pruebas en memoria.
     */
    public AdminTareasTestMemoria() {
        super(null);
    }

    /**
     * Agrega una nueva tarea con la descripción dada a la lista de tareas.
     *
     * @param descripcion La descripción de la tarea a agregar.
     */
    @Override
    public void agregarTarea(String descripcion) {
        Tarea nuevaTarea = new Tarea(descripcion);
        tareasEnMemoria.add(nuevaTarea);
    }

    /**
     * Elimina la tarea con la descripción dada de la lista de tareas.
     *
     * @param descripcion La descripción de la tarea a eliminar.
     */
    @Override
    public void eliminarTarea(String descripcion) {
        Iterator<Tarea> iterator = tareasEnMemoria.iterator();
        while (iterator.hasNext()) {
            Tarea tarea = iterator.next();
            if (tarea.getDescripcion().equals(descripcion)) {
                iterator.remove();
                return; // Sale del método después de eliminar la tarea
            }
        }
    }

    /**
     * Modifica la descripción de una tarea existente en la lista de tareas.
     *
     * @param descripcionOriginal La descripción actual de la tarea a editar.
     * @param descripcionNueva La nueva descripción para la tarea.
     */
    @Override
    public void editarTarea(String descripcionOriginal, String descripcionNueva) {
        for (Tarea tarea : tareasEnMemoria) {
            if (tarea.getDescripcion().equals(descripcionOriginal)) {
                tarea.setDescripcion(descripcionNueva);
                return; // Sale del método después de editar la tarea
            }
        }
    }

    /**
     * Cambia el estado de completada de la tarea con la descripción dada a
     * verdadera.
     *
     * @param descripcion La descripción de la tarea cuyo estado se debe
     * cambiar.
     */
    @Override
    public void cambiarEstadoTarea(String descripcion) {
        for (Tarea tarea : tareasEnMemoria) {
            if (tarea.getDescripcion().equals(descripcion)) {
                tarea.setEstaCompletada(true);
                return; // Sale del método después de cambiar el estado
            }
        }
    }

    /**
     * Devuelve una lista de todas las tareas que están marcadas como
     * completadas.
     *
     * @return Una lista de tareas completadas.
     */
    @Override
    public List<Tarea> obtenerTareasCompletadas() {
        List<Tarea> tareasCompletadas = new ArrayList<>();
        for (Tarea tarea : tareasEnMemoria) {
            if (tarea.getEstaCompletada()) {
                tareasCompletadas.add(tarea);
            }
        }
        return tareasCompletadas;
    }

    /**
     * Devuelve una lista de todas las tareas que están pendientes.
     *
     * @return Una lista de tareas pendientes.
     */
    @Override
    public List<Tarea> obtenerTareasPendientes() {
        List<Tarea> tareasPendientes = new ArrayList<>();
        for (Tarea tarea : tareasEnMemoria) {
            if (!tarea.getEstaCompletada()) {
                tareasPendientes.add(tarea);
            }
        }
        return tareasPendientes;
    }

    /**
     * Devuelve una copia de la lista actual de tareas.
     *
     * @return Una lista de todas las tareas en memoria.
     */
    @Override
    public List<Tarea> getTareas() {
        return new ArrayList<>(tareasEnMemoria);
    }
}
