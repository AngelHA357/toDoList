/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.todolist.conexion;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * Clase que permite crear la conexión a la base de datos
 * @author Jose Armenta, Jose Huerta, Victor Encinas
 */
public class Conexion implements IConexion{

    // Atributos para la conexión
    public String cadenaConexion = "mongodb://127.0.0.1:27017";
    public String nombreBaseDatos = "toDoList";

    // Método para obtener la base de datos
    @Override
    public MongoDatabase obtenerBaseDatos() {
        // Crear el CodecRegistry para manejar objetos POJO
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        // Configurar los ajustes del cliente de MongoDB usando la cadena de conexión y el CodecRegistry
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(cadenaConexion))
                .codecRegistry(pojoCodecRegistry)
                .build();

        // Crear el cliente de MongoDB
        MongoClient cliente = MongoClients.create(settings);

        // Obtener la base de datos especificada
        MongoDatabase baseDatos = cliente.getDatabase(nombreBaseDatos);

        return baseDatos; // Retornar la base de datos
    }
}
