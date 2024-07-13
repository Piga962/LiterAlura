package com.example.LiterAlura.service;

public interface iConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
