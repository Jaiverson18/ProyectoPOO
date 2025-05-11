package com.trivia.modelo;

import java.util.List;

public class Pregunta {

    private String TextoPregunta;
    private List<String> Respuestas;
    private Categoria Categoria;
    private String Autor;
    private Estado Estado;
    private int TiempoMaximoRespuesta;

    public Pregunta(String TextoPregunta, List<String> Respuestas, Categoria Categoria, String Autor, int TiempoMaximoRespuesta) {
        this.TextoPregunta = TextoPregunta;
        this.Respuestas = Respuestas;
        this.Categoria = Categoria;
        this.Autor = Autor;
        this.TiempoMaximoRespuesta = TiempoMaximoRespuesta;
        this.Estado = Estado.esperandoaprobacion;
    }

    public String getTextoPregunta() {
        return TextoPregunta;
    }

    public void setTextoPregunta(String TextoPregunta) {
        if (this.Estado == Estado.aprobado) {
            System.out.println("No se puede modificar una pregunta aprobada.");
            return;
        }
        this.TextoPregunta = TextoPregunta;
    }

    public List<String> getRespuestas() {
        return Respuestas;
    }

    public void setRespuestas(List<String> Respuestas) {
        if (this.Estado == Estado.aprobado) {
            System.out.println("No se puede modificar una pregunta aprobada.");
            return;
        }
        this.Respuestas = Respuestas;
    }

    public Categoria getCategoria() {
        return Categoria;
    }

    public void setCategoria(Categoria Categoria) {
        if (this.Estado == Estado.aprobado) {
            System.out.println("No se puede modificar una pregunta aprobada.");
            return;
        }
        this.Categoria = Categoria;
    }

    public String getAutor() {
        return Autor;
    }

    public Estado getEstado() {
        return Estado;
    }

    public void setEstado(Estado Estado) {
        this.Estado = Estado;
    }

    public int getTiempoMaximoRespuesta() {
        return TiempoMaximoRespuesta;
    }

    public void setTiempoMaximoRespuesta(int TiempoMaximoRespuesta) {
        if (this.Estado == Estado.aprobado) {
            System.out.println("No se puede modificar una pregunta aprobada.");
            return;
        }
        this.TiempoMaximoRespuesta = TiempoMaximoRespuesta;
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "TextoPregunta='" + TextoPregunta + '\'' +
                ", Respuestas=" + Respuestas +
                ", Categoria=" + Categoria +
                ", Autor='" + Autor + '\'' +
                ", Estado=" + Estado +
                ", TiempoMaximoRespuesta=" + TiempoMaximoRespuesta +
                '}';
    }

    public enum Estado {
        aprobado,
        rechazada,
        esperandoaprobacion
    }
}
