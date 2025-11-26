package modelo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class ServicioLibro {

    private static List<Libro> libros = new ArrayList<>();
    private static final String ARCHIVO = "Libros.json";

    public static void cargarLibros() {
        libros = obtenerTodos();
    }

    public static List<Libro> obtenerTodos() {
        List<Libro> lista = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(ARCHIVO);
            JSONArray jsonArray = (JSONArray) parser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject jsonLibro = (JSONObject) obj;

                String id = (String) jsonLibro.get("id");
                String titulo = (String) jsonLibro.get("titulo");
                String autor = (String) jsonLibro.get("autor");
                String categoria = (String) jsonLibro.get("categoria");
                boolean disponible = (boolean) jsonLibro.get("disponible");

                Libro libro = new Libro(id, titulo, autor, categoria, disponible);
                lista.add(libro);
            }

        } catch (Exception e) {
            System.out.println("Error al leer libros: " + e.getMessage());
        }

        return lista;
    }


    public static void agregarLibro(Libro libro) {
        try {
            List<Libro> lista = obtenerTodos();
            lista.add(libro);

            JSONArray jsonArray = new JSONArray();
            for (Libro l : lista) {
                JSONObject json = new JSONObject();
                json.put("id", l.getId());
                json.put("titulo", l.getTitulo());
                json.put("autor", l.getAutor());
                json.put("categoria", l.getCategoria());
                json.put("disponible", l.isDisponible());
                jsonArray.add(json);
            }

            FileWriter writer = new FileWriter(ARCHIVO);
            writer.write(jsonArray.toJSONString());
            writer.close();

        } catch (Exception e) {
            System.out.println("Error al agregar libro: " + e.getMessage());
        }
    }

    public static void actualizarDisponibilidad(String id, boolean disponible) {
        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(ARCHIVO);
            JSONArray array = (JSONArray) parser.parse(reader);

            for (Object obj : array) {
                JSONObject libro = (JSONObject) obj;
                if (id.equals(libro.get("id"))) {
                    libro.put("disponible", disponible);
                    break;
                }
            }

            try (FileWriter writer = new FileWriter(ARCHIVO)) {
                writer.write(array.toJSONString());
            }

        } catch (Exception e) {
            System.out.println("Error al actualizar disponibilidad: " + e.getMessage());
        }
    }


    public static List<Libro> obtenerLibrosDisponibles() {
        List<Libro> disponibles = new ArrayList<>();

        for (Libro libro : libros) {
            if (libro.isDisponible()) {
                disponibles.add(libro);
            }
        }

        return disponibles;
    }
    

    public static boolean prestarLibro(String idLibro, String usuario) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray libros = (JSONArray) parser.parse(new FileReader("Libros.json"));
        
            JSONObject libroEncontrado = null;
        
            for (Object obj : libros) {
                JSONObject libro = (JSONObject) obj;
            
                String id = (String) libro.get("id");
            
                if (id.equals(idLibro)) {
                    libroEncontrado = libro;
                    break;
                }
            }
        
            if (libroEncontrado == null) {
                System.out.println("Libro no encontrado");
                return false;
            }
        
            boolean disponible = (boolean) libroEncontrado.get("disponible");
        
            if (!disponible) {
                System.out.println("Libro NO disponible");
                return false;
            }
        
            libroEncontrado.put("disponible", false);
        
            try (FileWriter writer = new FileWriter("Libros.json")) {
                writer.write(libros.toJSONString());
                writer.flush();
            }
        
            ServicioPrestamo sp = new ServicioPrestamo();
            sp.agregarPrestamo(
                idLibro,
                usuario,
                java.time.LocalDate.now()
            );
        
            System.out.println("Préstamo registrado correctamente.");
            return true;
        
        } catch (Exception e) {
            System.out.println("ERROR en prestarLibro: " + e.getMessage());
            return false;
        }
    }



}
