/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.todolist.conexion;

import com.mongodb.client.MongoDatabase;

/**
 *
 * @author JoseH
 */
public interface IConexion {
    public MongoDatabase obtenerBaseDatos();
}
