package com.mycompany.todolist.test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mycompany.todolist.conexion.IConexion;
import com.mycompany.todolist.dominio.AdminTareas;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Clase de pruebas para AdminTareas. Contiene pruebas unitarias y de
 * integración para validar las funcionalidades de agregar, eliminar, editar y
 * marcar tareas como completadas y ver si se guardan correctamente en la base
 * de datos seleccionada. Se utiliza Mockito para ayudarnos a simular y
 * verificar que funcione la interacción con la base de datos.
 *
 * @author Jose Armenta, Jose Huerta, Victor Encinas
 */
public class AdminTareasTestBD {

    private IConexion conexion;
    private MongoDatabase database;
    private MongoCollection<Document> coleccion;
    private AdminTareas adminTareas;

    public AdminTareasTestBD() {
    }

    @BeforeEach
    void setUp() {
        conexion = Mockito.mock(IConexion.class);
        database = Mockito.mock(MongoDatabase.class);
        coleccion = Mockito.mock(MongoCollection.class);
        when(conexion.obtenerBaseDatos()).thenReturn(database);
        when(database.getCollection("tareas")).thenReturn(coleccion);
        adminTareas = new AdminTareas(conexion);
    }

    /**
     * Prueba para agregar una tarea y verificar que ha sido agregada
     * correctamente.
     */
    @Test
    void testAgregarTarea() {
        adminTareas.agregarTarea("Una tarea");
        verify(coleccion, times(1)).insertOne(any(Document.class));
    }

    /**
     * Prueba para eliminar una tarea y verificar que ha sido eliminada
     * correctamente.
     */
    @Test
    public void testEliminarTarea() {
        adminTareas.agregarTarea("Tarea 1");
        adminTareas.eliminarTarea("Tarea 1");
        verify(coleccion, times(1)).deleteOne((Bson) any(Document.class));
    }

    /**
     * Prueba para editar una tarea y verificar que ha sido actualizada
     * correctamente.
     */
    @Test
    public void testEditarTarea() {
        adminTareas.agregarTarea("Tarea 2");
        adminTareas.editarTarea("Tarea 2", "Tarea 2 editada");
        verify(coleccion, times(1)).updateOne((Bson)any(Document.class), (Bson)any(Document.class));
    }

    /**
     * Prueba para editar el estado de una tarea y verificar que ha sido
     * marcado correctamente.
     */
    @Test
    public void testCambiarEstadoTarea() {
        adminTareas.agregarTarea("Tarea 3");
        adminTareas.cambiarEstadoTarea("Tarea 3");
        verify(coleccion, times(1)).updateOne(any(Document.class), any(Document.class));
    }

    
}
