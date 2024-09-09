/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todolist;

import com.mycompany.todolist.conexion.Conexion;
import com.mycompany.todolist.conexion.IConexion;
import com.mycompany.todolist.guis.ListaTareasFrame;

/**
 *
 * @author Jose Armenta, Jose Huerta, Victor Encinas
 */
public class ToDoList {
    public static void main(String args[]){
        IConexion conexion = new Conexion();
        ListaTareasFrame mainFrame = new ListaTareasFrame(conexion);
        mainFrame.setVisible(true);
    }
    
}
