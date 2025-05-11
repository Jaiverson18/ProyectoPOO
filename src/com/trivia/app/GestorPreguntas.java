package com.trivia.app;

import com.trivia.modelo.Pregunta;
import com.trivia.modelo.Pregunta.Estado;
import com.trivia.modelo.Categoria;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;
import java.io.*;
import java.lang.reflect.Type;

public class GestorPreguntas {
    private static final String FILE_NAME = "preguntas.json";
    private List<Pregunta> preguntas;
    private Gson gson = new Gson();
    private Scanner sc = new Scanner(System.in);

    public GestorPreguntas() {
        preguntas = cargarPreguntas();
    }

    private List<Pregunta> cargarPreguntas() {
        List<Pregunta> lista = new ArrayList<>();
        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                Type listType = new TypeToken<ArrayList<Pregunta>>(){}.getType();
                lista = gson.fromJson(br, listType);
                br.close();
            }
        } catch (Exception e) {
            System.out.println("Error al cargar preguntas: " + e.getMessage());
        }
        return lista;
    }

    private void guardarPreguntas() {
        try {
            Writer writer = new FileWriter(FILE_NAME);
            gson.toJson(preguntas, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println("Error al guardar preguntas: " + e.getMessage());
        }
    }

    private void agregarPregunta() {
        System.out.print("Ingrese el texto de la pregunta: ");
        String texto = sc.nextLine();

        System.out.print("Ingrese las respuestas válidas separadas por comas: ");
        String respuestasInput = sc.nextLine();
        List<String> respuestas = new ArrayList<>();
        for (String s : respuestasInput.split(",")) {
            respuestas.add(s.trim());
        }

        System.out.println("Seleccione la categoría:");
        Categoria[] categorias = Categoria.values();
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + ". " + categorias[i].getNombre());
        }
        int catIndex = sc.nextInt();
        sc.nextLine();
        Categoria categoria = categorias[catIndex - 1];

        System.out.print("Ingrese el autor de la pregunta: ");
        String autor = sc.nextLine();
        System.out.print("Ingrese el tiempo máximo de respuesta (segundos): ");
        int tiempo = sc.nextInt();
        sc.nextLine();

        Pregunta p = new Pregunta(texto, respuestas, categoria, autor, tiempo);
        preguntas.add(p);
        guardarPreguntas();
        System.out.println("Pregunta agregada y en estado 'esperandoaprobacion'.");
    }

    private void modificarPregunta() {
        listarPreguntas();
        System.out.print("Ingrese el número de la pregunta a modificar: ");
        int indice = sc.nextInt() - 1;
        sc.nextLine();
        if (indice < 0 || indice >= preguntas.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        Pregunta p = preguntas.get(indice);
        if (p.getEstado() == Estado.aprobado) {
            System.out.println("No se puede modificar una pregunta aprobada.");
            return;
        }
        System.out.println("1. Modificar texto");
        System.out.println("2. Modificar respuestas válidas");
        System.out.println("3. Modificar categoría");
        System.out.println("4. Modificar tiempo máximo de respuesta");
        System.out.print("Seleccione opción: ");
        int opcion = sc.nextInt();
        sc.nextLine();
        switch (opcion) {
            case 1:
                System.out.print("Nuevo texto: ");
                p.setTextoPregunta(sc.nextLine());
                break;
            case 2:
                System.out.print("Ingrese las nuevas respuestas válidas separadas por comas: ");
                String resInput = sc.nextLine();
                List<String> nuevasRespuestas = new ArrayList<>();
                for (String s : resInput.split(",")) {
                    nuevasRespuestas.add(s.trim());
                }
                p.setRespuestas(nuevasRespuestas);
                break;
            case 3:
                System.out.println("Seleccione la nueva categoría:");
                Categoria[] categorias = Categoria.values();
                for (int i = 0; i < categorias.length; i++) {
                    System.out.println((i + 1) + ". " + categorias[i].getNombre());
                }
                int catIndex = sc.nextInt();
                sc.nextLine();
                p.setCategoria(categorias[catIndex - 1]);
                break;
            case 4:
                System.out.print("Nuevo tiempo máximo (segundos): ");
                p.setTiempoMaximoRespuesta(sc.nextInt());
                sc.nextLine();
                break;
            default:
                System.out.println("Opción inválida.");
                break;
        }
        guardarPreguntas();
        System.out.println("Pregunta modificada.");
    }

    private void consultarPreguntas() {
        listarPreguntas();
    }

    private void eliminarPregunta() {
        listarPreguntas();
        System.out.print("Ingrese el número de la pregunta a eliminar: ");
        int indice = sc.nextInt() - 1;
        sc.nextLine();
        if (indice < 0 || indice >= preguntas.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        preguntas.remove(indice);
        guardarPreguntas();
        System.out.println("Pregunta eliminada.");
    }

    private void aprobarPreguntas() {
        System.out.print("Ingrese su nombre de usuario: ");
        String usuarioActual = sc.nextLine();
        List<Pregunta> pendientes = new ArrayList<>();
        for (Pregunta p : preguntas) {
            if (p.getEstado() == Estado.esperandoaprobacion && !p.getAutor().equalsIgnoreCase(usuarioActual)) {
                pendientes.add(p);
            }
        }
        if (pendientes.isEmpty()) {
            System.out.println("No hay preguntas pendientes para aprobación.");
            return;
        }
        for (Pregunta p : pendientes) {
            System.out.println(p);
            System.out.print("Aprobar (a) o rechazar (r): ");
            String decision = sc.nextLine();
            if (decision.equalsIgnoreCase("a")) {
                p.setEstado(Estado.aprobado);
                System.out.println("Pregunta aprobada.");
            } else if (decision.equalsIgnoreCase("r")) {
                p.setEstado(Estado.rechazada);
                System.out.println("Pregunta rechazada.");
            } else {
                System.out.println("Decisión inválida, se mantiene en estado esperandoaprobacion.");
            }
        }
        guardarPreguntas();
    }

    private void modificarTiempoPregunta() {
        listarPreguntas();
        System.out.print("Ingrese el número de la pregunta para modificar su tiempo: ");
        int indice = sc.nextInt() - 1;
        sc.nextLine();
        if (indice < 0 || indice >= preguntas.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        Pregunta p = preguntas.get(indice);
        if (p.getEstado() == Estado.aprobado) {
            System.out.println("No se puede modificar una pregunta aprobada.");
            return;
        }
        System.out.print("Ingrese el nuevo tiempo máximo (segundos): ");
        p.setTiempoMaximoRespuesta(sc.nextInt());
        sc.nextLine();
        guardarPreguntas();
        System.out.println("Tiempo máximo modificado.");
    }

    private void listarPreguntas() {
        if (preguntas.isEmpty()) {
            System.out.println("No hay preguntas registradas.");
            return;
        }
        for (int i = 0; i < preguntas.size(); i++) {
            System.out.println((i + 1) + ". " + preguntas.get(i));
        }
    }

    private void menu() {
        int opcion;
        do {
            System.out.println("\nMenú:");
            System.out.println("1. Agregar pregunta nueva");
            System.out.println("2. Modificar pregunta");
            System.out.println("3. Consultar preguntas");
            System.out.println("4. Eliminar pregunta");
            System.out.println("5. Aprobar/Rechazar preguntas pendientes");
            System.out.println("6. Modificar tiempo máximo de respuesta");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    agregarPregunta();
                    break;
                case 2:
                    modificarPregunta();
                    break;
                case 3:
                    consultarPreguntas();
                    break;
                case 4:
                    eliminarPregunta();
                    break;
                case 5:
                    aprobarPreguntas();
                    break;
                case 6:
                    modificarTiempoPregunta();
                    break;
                case 7:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 7);
    }

    public static void main(String[] args) {
        new GestorPreguntas().menu();
    }
}
