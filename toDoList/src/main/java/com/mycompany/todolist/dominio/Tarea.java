/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todolist.dominio;

/**
 *
 * @author Jose Armenta, Jose Huerta, Victor Encinas
 */
public class Tarea {
    private String descripcion;
    private boolean estaCompletada;

    public Tarea(String descripcion) {
        this.descripcion = descripcion;
        this.estaCompletada = false;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean isEstaCompletada() {
        return estaCompletada;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCompletada(boolean estaCompletada) {
        this.estaCompletada = estaCompletada;
    }
    
    @Override
    public String toString(){
        return descripcion;
    }
    
}
