package modelo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.*;
import java.util.*;

// Servicio para manejar los libros
public class ServicioLibro {
    
    private static List<Libro> libros = new ArrayList<>();
    private static final String ARCHIVO = "libros.json"; // Ruta del archivo JSON

    // Método para obtener todos los libros desde el archivo
    public static List<Libro> obtenerTodos() {
        List<Libro> lista = new ArrayList<>();
    
        try {
            JSONParser parser = new JSONParser();
            // Leer el archivo y parsear el contenido como un JSONArray
            FileReader reader = new FileReader(ARCHIVO);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);
    
            // Iterar a través de los elementos del JSONArray
            for (Object obj : jsonArray) {
                JSONObject jsonLibro = (JSONObject) obj;
    
                String titulo = (String) jsonLibro.get("titulo");
                String autor = (String) jsonLibro.get("autor");
                String categoria = (String) jsonLibro.get("categoria");
                boolean disponible = (boolean) jsonLibro.get("disponible");
    
                // Crear un nuevo objeto Libro y agregarlo a la lista
                Libro libro = new Libro(titulo, autor, categoria, disponible);
                lista.add(libro);
            }
        } catch (Exception e) {
            System.out.println("Error al leer libros: " + e.getMessage());
        }
    
        return lista;
    }
    
    // Método para agregar un libro al archivo JSON
    public static void agregarLibro(Libro libro) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject;

            // Intentar leer el archivo si ya existe
            try {
                FileReader reader = new FileReader(ARCHIVO);
                jsonObject = (JSONObject) parser.parse(reader);
            } catch (Exception e) {
                // Si el archivo no existe o está vacío, creamos un nuevo objeto JSON
                jsonObject = new JSONObject();
            }

            // Crear un objeto JSON para el nuevo libro
            JSONObject nuevo = new JSONObject();
            nuevo.put("titulo", libro.getTitulo());
            nuevo.put("autor", libro.getAutor());
            nuevo.put("categoria", libro.getCategoria());
            nuevo.put("disponible", libro.isDisponible());

            // Generar un ID único para cada libro
            String id = UUID.randomUUID().toString(); // Genera un ID único

            // Añadir el libro al objeto JSON con el ID como clave
            jsonObject.put(id, nuevo);

            // Escribir el contenido actualizado de vuelta al archivo
            try (FileWriter writer = new FileWriter(ARCHIVO)) {
                writer.write(jsonObject.toJSONString());
                writer.flush();
            }

        } catch (Exception e) {
            System.out.println("Error al agregar libro: " + e.getMessage());
        }
    }

    // Método para actualizar la disponibilidad de un libro (ej. después de un préstamo)
    public static void actualizarDisponibilidad(String id, boolean disponible) {
        try {
            JSONParser parser = new JSONParser();
            // Leer el archivo y parsear el contenido
            FileReader reader = new FileReader(ARCHIVO);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            // Buscar el libro por su ID y actualizar la disponibilidad
            JSONObject libro = (JSONObject) jsonObject.get(id);
            if (libro != null) {
                libro.put("disponible", disponible);

                // Escribir el contenido actualizado de vuelta al archivo
                try (FileWriter writer = new FileWriter(ARCHIVO)) {
                    writer.write(jsonObject.toJSONString());
                    writer.flush();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar disponibilidad: " + e.getMessage());
        }
    }

    public static JSONObject obtenerLibroPorId(int id) {
        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(ARCHIVO);
            JSONArray libros = (JSONArray) parser.parse(reader);
    
            for (Object obj : libros) {
                JSONObject libro = (JSONObject) obj;
                if ((int) libro.get("id") == id) {
                    return libro;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener libro por ID: " + e.getMessage());
        }
        return null;
    }
    public static List<Libro> obtenerLibrosDisponibles() {
        List<Libro> disponibles = new ArrayList<>();
        
        // Imprimir para depuración
        System.out.println("Total libros registrados: " + libros.size());
        
        for (Libro libro : libros) {
            System.out.println("Libro: " + libro.getTitulo() + " - Disponible: " + libro.isDisponible());
            if (libro.isDisponible()) {
                disponibles.add(libro);
            }
        }
        
        // Si no hay libros disponibles, muestra un mensaje
        if (disponibles.isEmpty()) {
            System.out.println("No hay libros disponibles.");
        }
        
        return disponibles;
    }

    
}
