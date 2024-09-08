package com.mycompany.todolist.dominio;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jose Armenta, Jose Huerta, Victor Encinas
 */
public class AdminTareas {
    private List<Tarea> tareas;

    public AdminTareas() {
        this.tareas = new ArrayList<>();
    }

    public void agregarTarea(String descripcion) {
        tareas.add(new Tarea(descripcion));
    }

    public void editarTarea(String descripcion, String newDescripcion) {
        // Corregido: Buscar la tarea usando la descripción actual, no la nueva
        for (Tarea tarea : tareas) {
            if (tarea.getDescripcion().equals(descripcion)) {
                tarea.setDescripcion(newDescripcion);
                break; // salir del ciclo una vez encontrada la tarea
            }
        }
    }

    public void eliminarTarea(String descripcion) {
        // Corregido: Buscar y eliminar la tarea usando la descripción actual
        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getDescripcion().equals(descripcion)) {
                tareas.remove(i);
                break; // salir del ciclo una vez eliminada la tarea
            }
        }
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void listaTareas() {
        for (int i = 0; i < tareas.size(); i++) {
            System.out.println((i + 1) + ". " + tareas.get(i));
        }
    }
    
}
