package modelo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServicioPrestamo {

    private List<Prestamo> prestamosActivos = new ArrayList<>();

    // Método para agregar un nuevo préstamo
    public void agregarPrestamo(String idLibro, String idCliente, LocalDate fechaPrestamo) {
        // Generar un ID único para el préstamo
        String idPrestamo = UUID.randomUUID().toString();

        // Crear un nuevo préstamo
        Prestamo prestamo = new Prestamo(idPrestamo, idLibro, idCliente, fechaPrestamo);
        prestamosActivos.add(prestamo);

        // Crear objeto JSON para guardar el préstamo
        JSONObject prestamoJson = new JSONObject();
        prestamoJson.put("idPrestamo", prestamo.getIdPrestamo());
        prestamoJson.put("idLibro", prestamo.getIdLibro());
        prestamoJson.put("idCliente", prestamo.getIdCliente());
        prestamoJson.put("fechaPrestamo", prestamo.getFechaPrestamo().toString());
        prestamoJson.put("fechaDevolucion", prestamo.getFechaDevolucion().toString());

        // Guardar préstamo en el archivo JSON
        JSONArray prestamos = JsonDB.leer("prestamos.json");
        prestamos.add(prestamoJson);
        JsonDB.escribir("prestamos.json", prestamos);

        System.out.println("Préstamo agregado: " + prestamo);
    }

    // Método para obtener todos los préstamos activos
    public List<Prestamo> obtenerPrestamosActivos() {
        return prestamosActivos;
    }

    // Método para buscar un préstamo por ID
    public Prestamo buscarPrestamoPorId(String idPrestamo) {
        for (Prestamo prestamo : prestamosActivos) {
            if (prestamo.getIdPrestamo().equals(idPrestamo)) {
                return prestamo;
            }
        }
        return null; // Si no se encuentra el préstamo
    }

    // Método para cargar los préstamos desde el archivo JSON
    public void cargarPrestamosDesdeJson() {
        JSONArray prestamosJson = JsonDB.leer("prestamos.json");
        for (Object obj : prestamosJson) {
            JSONObject jsonPrestamo = (JSONObject) obj;
            Prestamo prestamo = new Prestamo(
                (String) jsonPrestamo.get("idPrestamo"),
                (String) jsonPrestamo.get("idLibro"),
                (String) jsonPrestamo.get("idCliente"),
                LocalDate.parse((String) jsonPrestamo.get("fechaPrestamo"))
            );
            prestamosActivos.add(prestamo);
        }
    }
}
