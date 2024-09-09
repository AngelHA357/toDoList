package com.mycompany.todolist.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jose Armenta, Jose Huerta, Victor Encinas
 */
public class AdminTareas {

    private List<Tarea> tareas; // Lista que almacena las tareas

    /**
     * Constructor por defecto que inicializa la lista de tareas.
     */
    public AdminTareas() {
        this.tareas = new ArrayList<>(); // Inicializa la lista de tareas
    }

    /**
     * Agrega una nueva tarea a la lista.
     * 
     * @param descripcion Descripción de la tarea a agregar.
     */
    public void agregarTarea(String descripcion) {
        tareas.add(new Tarea(descripcion)); // Crea una nueva tarea con la descripción proporcionada y la agrega a la lista
    }

    /**
     * Edita la descripción de una tarea existente.
     * 
     * @param descripcion Descripción actual de la tarea que se desea editar.
     * @param newDescripcion Nueva descripción para la tarea.
     */
    public void editarTarea(String descripcion, String newDescripcion) {
        // Busca la tarea usando la descripción actual y actualiza su descripción
        for (Tarea tarea : tareas) {
            if (tarea.getDescripcion().equals(descripcion)) {
                tarea.setDescripcion(newDescripcion); // Cambia la descripción de la tarea
                break; // Sale del ciclo una vez encontrada y editada la tarea
            }
        }
    }

    /**
     * Elimina una tarea de la lista según su descripción.
     * 
     * @param descripcion Descripción de la tarea que se desea eliminar.
     */
    public void eliminarTarea(String descripcion) {
        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getDescripcion().equals(descripcion)) {
                tareas.remove(i); // Elimina la tarea en el índice encontrado
                break; // Sale del ciclo una vez eliminada la tarea
            }
        }
    }

    /**
     * Devuelve la lista completa de tareas.
     * 
     * @return La lista de tareas.
     */
    public List<Tarea> getTareas() {
        return tareas; // Retorna la lista de tareas
    }

    /**
     * Imprime la lista de tareas en la consola con índices.
     */
    public void listaTareas() {
        for (int i = 0; i < tareas.size(); i++) {
            System.out.println((i + 1) + ". " + tareas.get(i)); // Muestra cada tarea con un índice
        }
    }

    /**
     * Obtiene una lista de todas las tareas completadas.
     * 
     * @return La lista de tareas completadas.
     */
    public List<Tarea> obtenerTareasCompletadas() {
        List<Tarea> tareasCompletadas = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (tarea.isEstaCompletada()) {
                tareasCompletadas.add(tarea); // Agrega las tareas completadas a la nueva lista
            }
        }
        return tareasCompletadas; // Retorna la lista de tareas completadas
    }

    /**
     * Obtiene una lista de todas las tareas pendientes.
     * 
     * @return La lista de tareas pendientes.
     */
    public List<Tarea> obtenerTareasPendientes() {
        List<Tarea> tareasPendientes = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (!tarea.isEstaCompletada()) {
                tareasPendientes.add(tarea); // Agrega las tareas pendientes a la nueva lista
            }
        }
        return tareasPendientes; // Retorna la lista de tareas pendientes
    }

}

