/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.todolist.conexion;

import com.mongodb.client.MongoDatabase;

/**
 * Interfaz que contiene el método para crear la conexión a la base de datos.
 * @author Jose Armenta, Jose Huerta, Victor Encinas
 */
public interface IConexion {
    public MongoDatabase obtenerBaseDatos();
}
