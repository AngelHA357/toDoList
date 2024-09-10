/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.todolist.test;

import com.mycompany.todolist.conexion.IConexion;
import com.mycompany.todolist.dominio.AdminTareas;
import com.mycompany.todolist.dominio.Tarea;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase de pruebas para AdminTareas. Contiene pruebas unitarias y de
 * integración para validar las funcionalidades de agregar, eliminar, editar y
 * marcar tareas como completadas.
 *
 * @author Jose Armenta, Jose Huerta, Victor Encinas
 */
public class AdminTareasTest {

    private AdminTareasTestMemoria adminTareas;

    public AdminTareasTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        adminTareas = new AdminTareasTestMemoria();
    }

    @After
    public void tearDown() {
    }

    /**
     * Prueba para agregar una tarea y verificar que ha sido agregada
     * correctamente.
     */
    @Test
    public void testAgregarTarea() {

        String descripcion = "Una tarea";

        adminTareas.agregarTarea(descripcion);

        List<Tarea> tareas = adminTareas.getTareas();
        assertEquals(1, tareas.size());
        assertEquals(descripcion, tareas.get(0).getDescripcion());
    }

    /**
     * Prueba para eliminar una tarea y verificar que ha sido eliminada
     * correctamente.
     */
    @Test
    public void testEliminarTarea() {

        String descripcion = "Una tarea";
        adminTareas.agregarTarea(descripcion);

        adminTareas.eliminarTarea(descripcion);

        List<Tarea> tareas = adminTareas.getTareas();
        assertTrue(tareas.isEmpty()); // Verifica que la lista de tareas esté vacía
    }

    /**
     * Prueba para editar una tarea y verificar que ha sido actualizada
     * correctamente.
     */
    @Test
    public void testEditarTarea() {

        String descripcionOriginal = "Una tarea";
        String descripcionNueva = "Una tarea editada";
        adminTareas.agregarTarea(descripcionOriginal);

        adminTareas.editarTarea(descripcionOriginal, descripcionNueva);

        List<Tarea> tareas = adminTareas.getTareas();
        assertEquals(1, tareas.size()); // Asegura que solo hay una tarea
        assertEquals(descripcionNueva, tareas.get(0).getDescripcion()); // Verifica la descripción actualizada
    }

    /**
     * Prueba para marcar una tarea como completada y verificar su estado.
     */
    @Test
    public void testMarcarComoCompletada() {

        String descripcion = "Una tarea";
        adminTareas.agregarTarea(descripcion);
        Tarea tarea = adminTareas.getTareas().get(0);

        adminTareas.cambiarEstadoTarea(descripcion);

        assertTrue(tarea.getEstaCompletada()); // Verifica que la tarea esté marcada como completada
    }

    /**
     * Prueba para verificar que el filtrado de tareas completadas funciona
     * correctamente.
     */
    @Test
    public void testObtenerTareasCompletadas() {

        adminTareas.agregarTarea("Una tarea");
        adminTareas.agregarTarea("Segunda tarea");
        adminTareas.agregarTarea("Tercera tarea");

        adminTareas.cambiarEstadoTarea("Una tarea");
        adminTareas.cambiarEstadoTarea("Tercera tarea");

        List<Tarea> tareasCompletadas = adminTareas.obtenerTareasCompletadas();
        assertEquals(2, tareasCompletadas.size());
        assertTrue(tareasCompletadas.stream().allMatch(Tarea::getEstaCompletada));
    }

    /**
     * Prueba para verificar que el filtrado de tareas pendientes funciona
     * correctamente.
     */
    @Test
    public void testObtenerTareasPendientes() {

        adminTareas.agregarTarea("Una tarea");
        adminTareas.agregarTarea("Segunda tarea");
        adminTareas.agregarTarea("Tercera tarea");

        // Se marca la primera tarea como completada
        adminTareas.cambiarEstadoTarea(adminTareas.getTareas().get(0).getDescripcion());

        // Se obtienen las tareas pendientes
        List<Tarea> tareasPendientes = adminTareas.obtenerTareasPendientes();

        assertEquals(2, tareasPendientes.size());

        assertTrue(tareasPendientes.stream().noneMatch(Tarea::getEstaCompletada));
    }

    /**
     * Prueba para verificar que cuando no hay tareas completadas, la lista
     * devuelta está vacía.
     */
    @Test
    public void testTareasCompletadasConTodasPendientes() {
        adminTareas.agregarTarea("Una tarea");
        adminTareas.agregarTarea("Segunda tarea");

        List<Tarea> tareasCompletadas = adminTareas.obtenerTareasCompletadas();
        assertTrue(tareasCompletadas.isEmpty());
    }

    /**
     * Prueba para verificar que cuando todas las tareas están completadas, el
     * filtrado de tareas pendientes devuelve una lista vacía.
     */
    @Test
    public void testTareasPendienteConTodasCompletadas() {
        adminTareas.agregarTarea("Una tarea");
        adminTareas.agregarTarea("Segunda tarea");

        adminTareas.cambiarEstadoTarea(adminTareas.getTareas().get(0).getDescripcion());
        adminTareas.cambiarEstadoTarea(adminTareas.getTareas().get(1).getDescripcion());

        List<Tarea> tareasPendientes = adminTareas.obtenerTareasPendientes();
        assertTrue(tareasPendientes.isEmpty());
    }

    /**
     * Prueba para agregar varias tareas y verificar el número de tareas en la
     * lista.
     */
    @Test
    public void testAgregarVariasTareas() {
        adminTareas.agregarTarea("Una tarea");
        adminTareas.agregarTarea("Segunda tarea");
        assertEquals(2, adminTareas.getTareas().size());
    }

    /**
     * Prueba para asegurar que no se puede agregar una tarea con descripción
     * vacía. Se espera una excepción IllegalArgumentException.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAgregarTareasSinDescripcion() {
        adminTareas.agregarTarea("");
    }

    /**
     * Prueba para eliminar una tarea que no existe. Verifica que la lista de
     * tareas no cambie.
     *
     */
    @Test
    public void testEliminarTareaInexistente() {
        adminTareas.agregarTarea("Una tarea");
        adminTareas.eliminarTarea("Segunda tarea"); // Esta tarea no existe
        assertEquals(1, adminTareas.getTareas().size()); // La lista no debe cambiar
    }

    /**
     * Prueba para editar una tarea que no existe. Verifica que ninguna tarea se
     * modifique.
     */
    @Test
    public void testEditarTareaInexistente() {
        adminTareas.agregarTarea("Una tarea");
        adminTareas.editarTarea("Segunda tarea", "tarea editada"); // Esta tarea no existe
        assertEquals("Una tarea", adminTareas.getTareas().get(0).getDescripcion()); // No debe cambiar la lista
    }

    /**
     * Prueba para intentar marcar una tarea inexistente como completada. Se
     * espera una excepción IndexOutOfBoundsException.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testMarcarTareaInexistenteComoCompletada() {
        adminTareas.agregarTarea("Una tarea");
        Tarea tarea = adminTareas.getTareas().get(1); // No existe tarea
        adminTareas.cambiarEstadoTarea("Una tarea");
    }

    /**
     * Prueba de integración que verifica agregar, editar y completar tareas en
     * secuencia.
     */
    @Test
    public void testIntegracionAgregarEditarCompletar() {
        adminTareas.agregarTarea("Una tarea");
        adminTareas.agregarTarea("Segunda tarea");
        adminTareas.agregarTarea("Tercera tarea");

        assertEquals(3, adminTareas.getTareas().size());
        assertEquals("Una tarea", adminTareas.getTareas().get(0).getDescripcion());
        assertEquals("Segunda tarea", adminTareas.getTareas().get(1).getDescripcion());
        assertEquals("Tercera tarea", adminTareas.getTareas().get(2).getDescripcion());

        adminTareas.editarTarea("Segunda tarea", "Segunda tarea Editada");
        assertEquals("Segunda tarea Editada", adminTareas.getTareas().get(1).getDescripcion());

        Tarea tarea1 = adminTareas.getTareas().get(0);
        adminTareas.cambiarEstadoTarea(tarea1.getDescripcion());
        assertTrue(tarea1.getEstaCompletada());
    }

    /**
     * Prueba de integración que verifica agregar y eliminar tareas en
     * secuencia.
     *
     */
    @Test
    public void testIntegracionAgregarEliminarVerificar() {
        adminTareas.agregarTarea("Una tarea");
        adminTareas.agregarTarea("Segunda tarea");
        adminTareas.agregarTarea("Tercera tarea");

        // Eliminar la segunda tarea
        adminTareas.eliminarTarea("Segunda tarea");

        assertEquals(2, adminTareas.getTareas().size());
        assertEquals("Una tarea", adminTareas.getTareas().get(0).getDescripcion());
        assertEquals("Tercera tarea", adminTareas.getTareas().get(1).getDescripcion());
    }

}
