package com.mycompany.todolist.dominio;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mycompany.todolist.conexion.IConexion;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author Jose Armenta, Jose Huerta, Victor Encinas
 */
public class AdminTareas {

    public String nombreColeccion = "tareas";
    static final Logger logger = Logger.getLogger(AdminTareas.class.getName());
    private IConexion conexion; // Lista que almacena las tareas

    
    /**
     * Constructor por defecto que establece la conexión con la base de datos
     */
    public AdminTareas(IConexion conexion) {
        this.conexion = conexion;
    }
    
    /**
     * Agrega una nueva tarea a la lista.
     * 
     * @param descripcion Descripción de la tarea a agregar.
     */
    public void agregarTarea(String descripcion) {
        try {
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Tarea> coleccion = base.getCollection(nombreColeccion, Tarea.class);

            Tarea nuevaTarea = new Tarea(descripcion);
            coleccion.insertOne(nuevaTarea); // Inserta la tarea en la colección

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al agregar tarea", ex);
        }
    }


    /**
     * Edita la descripción de una tarea existente.
     * 
     * @param descripcion Descripción actual de la tarea que se desea editar.
     * @param newDescripcion Nueva descripción para la tarea.
     */
    public void editarTarea(String descripcion, String newDescripcion) {
        try {
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Tarea> coleccion = base.getCollection(nombreColeccion, Tarea.class);

            // Busca y actualiza la tarea en la colección
            coleccion.updateOne(Filters.eq("descripcion", descripcion),
                    Updates.set("descripcion", newDescripcion));

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al editar tarea", ex);
        }
    }

    /**
     * Elimina una tarea de la lista según su descripción.
     * 
     * @param descripcion Descripción de la tarea que se desea eliminar.
     */
    public void eliminarTarea(String descripcion) {
        try {
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Tarea> coleccion = base.getCollection(nombreColeccion, Tarea.class);

            // Elimina la tarea que coincide con la descripción
            coleccion.deleteOne(Filters.eq("descripcion", descripcion));

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al eliminar tarea", ex);
        }
    }
    
    /**
     * Cambia el estado de una tarea según su estado anterior.
     * 
     * @param descripcion Descripción de la tarea a actualizar.
     */
    public void cambiarEstadoTarea(String descripcion) {
        Bson filtro = Filters.eq("descripcion", descripcion);
        try {
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Tarea> coleccion = base.getCollection(nombreColeccion, Tarea.class);

            // Buscar el documento que coincida con el filtro
            Tarea tarea = coleccion.find(filtro).first();
            
            // Crear la actualización para cambiar el estado a completada o pendiente
            if (tarea.getEstaCompletada()) {
                coleccion.updateOne(Filters.eq("descripcion", descripcion), Updates.set("estaCompletada", false));
            } else {
                coleccion.updateOne(Filters.eq("descripcion", descripcion), Updates.set("estaCompletada", true));
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al actualizar tarea", ex);
        }
    }

    /**
     * Devuelve la lista completa de tareas.
     * 
     * @return La lista de tareas.
     */
    public List<Tarea> getTareas() {
        List<Tarea> tareas = new ArrayList<>();
        try {
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Tarea> coleccion = base.getCollection(nombreColeccion, Tarea.class);

            // Recupera todas las tareas de la colección
            coleccion.find().into(tareas);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al obtener la lista de tareas", ex);
        }
        return tareas;
    }

    

    /**
     * Obtiene una lista de todas las tareas completadas.
     * 
     * @return La lista de tareas completadas.
     */
    public List<Tarea> obtenerTareasCompletadas() {
        List<Tarea> tareasCompletadas = new ArrayList<>();
        try {
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Tarea> coleccion = base.getCollection(nombreColeccion, Tarea.class);

            // Filtra y obtiene las tareas completadas
            coleccion.find(Filters.eq("estaCompletada", true)).into(tareasCompletadas);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al obtener tareas completadas", ex);
        }
        return tareasCompletadas;
    }

    /**
     * Obtiene una lista de todas las tareas pendientes.
     * 
     * @return La lista de tareas pendientes.
     */
    public List<Tarea> obtenerTareasPendientes() {
        List<Tarea> tareasPendientes = new ArrayList<>();
        try {
            MongoDatabase base = conexion.obtenerBaseDatos();
            MongoCollection<Tarea> coleccion = base.getCollection(nombreColeccion, Tarea.class);

            // Filtra y obtiene las tareas pendientes
            coleccion.find(Filters.eq("estaCompletada", false)).into(tareasPendientes);

        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error al obtener tareas pendientes", ex);
        }
        return tareasPendientes;
    }

}

