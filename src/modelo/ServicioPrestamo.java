package modelo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServicioPrestamo {

    private List<Prestamo> prestamosActivos = new ArrayList<>();

    public void agregarPrestamo(String idLibro, String idCliente, LocalDate fechaPrestamo) {

        String idPrestamo = UUID.randomUUID().toString();

        Prestamo prestamo = new Prestamo(idPrestamo, idLibro, idCliente, fechaPrestamo);
        prestamosActivos.add(prestamo);

        JSONObject prestamoJson = new JSONObject();
        prestamoJson.put("idPrestamo", prestamo.getIdPrestamo());
        prestamoJson.put("idLibro", prestamo.getIdLibro());
        prestamoJson.put("idCliente", prestamo.getIdCliente());
        prestamoJson.put("fechaPrestamo", prestamo.getFechaPrestamo().toString());
        prestamoJson.put("fechaDevolucion", prestamo.getFechaDevolucion().toString());

        JSONArray prestamos = JsonDB.leer("prestamos.json");
        prestamos.add(prestamoJson);
        JsonDB.escribir("prestamos.json", prestamos);

        System.out.println("Préstamo agregado: " + prestamo);
    }

    public List<Prestamo> obtenerPrestamosActivos() {
        return prestamosActivos;
    }

    public Prestamo buscarPrestamoPorId(String idPrestamo) {
        for (Prestamo prestamo : prestamosActivos) {
            if (prestamo.getIdPrestamo().equals(idPrestamo)) {
                return prestamo;
            }
        }
        return null;
    }

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
