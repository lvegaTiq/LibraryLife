package modelo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServicioPrestamo {

    private static final String ARCHIVO = "prestamos.json";

    // -------------------------------
    // 1. REGISTRAR PRÉSTAMO
    // -------------------------------
    public static void registrarPrestamo(String idLibro, String idCliente) {

        JSONObject prestamoJson = new JSONObject();
        prestamoJson.put("idPrestamo", UUID.randomUUID().toString());
        prestamoJson.put("idLibro", idLibro);
        prestamoJson.put("idCliente", idCliente);
        prestamoJson.put("fechaPrestamo", LocalDate.now().toString());
        prestamoJson.put("fechaLimite", LocalDate.now().plusDays(7).toString());
        prestamoJson.put("devuelto", false);

        JSONArray prestamos = JsonDB.leer(ARCHIVO);
        prestamos.add(prestamoJson);
        JsonDB.escribir(ARCHIVO, prestamos);

        System.out.println("✔ Préstamo registrado correctamente");
    }

    // -------------------------------------
    // 2. OBTENER PRÉSTAMOS POR USUARIO
    // -------------------------------------
    public static List<Prestamo> obtenerPrestamosPorUsuario(String usuario) {

        List<Prestamo> lista = new ArrayList<>();
        JSONArray prestamos = JsonDB.leer(ARCHIVO);

        for (Object obj : prestamos) {
            JSONObject json = (JSONObject) obj;

            String idCliente = (String) json.get("idCliente");

            if (idCliente.equals(usuario)) {
                Prestamo p = new Prestamo(
                        (String) json.get("idPrestamo"),
                        (String) json.get("idLibro"),
                        idCliente,
                        LocalDate.parse((String) json.get("fechaPrestamo"))
                );
                // agregar fecha límite
                p.setFechaDevolucion(LocalDate.parse((String) json.get("fechaLimite")));

                lista.add(p);
            }
        }

        return lista;
    }

    // -------------------------------------
    // 3. MARCAR DEVUELTO
    // -------------------------------------
    public static void marcarComoDevuelto(String idLibro) {

        JSONArray array = JsonDB.leer(ARCHIVO);

        for (Object obj : array) {
            JSONObject json = (JSONObject) obj;

            if (json.get("idLibro").equals(idLibro)) {
                json.put("devuelto", true);
                json.put("fechaDevolucionReal", LocalDate.now().toString());
                break;
            }
        }

        JsonDB.escribir(ARCHIVO, array);
    }
}
