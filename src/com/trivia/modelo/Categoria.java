package com.trivia.modelo;

public enum Categoria {
    geografia("Geografia", "Azul"),
    historia("Historia", "Amarillo"),
    deportesPasatiempos("Deportes y pasatiempos", "Naranja"),
    cienciasYNaturalesa("Ciencias y Naturalesa", "Verde"),
    arteYLiteratura("Arte y Literatura", "Morado"),
    entretenimiento("Entretenimiento", "Rosado");

    private final String nombre;
    private final String color;

    Categoria(String nombre, String color){
        this.nombre = nombre;
        this.color = color;
    }
    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString(){
        return nombre + "("+ color + ")";
    }
}
