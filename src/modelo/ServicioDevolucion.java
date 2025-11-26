package modelo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.time.LocalDate;
import java.util.UUID;

public class ServicioDevolucion {

    public void registrarDevolucion(Prestamo prestamo) {
        if (prestamo != null) {
           
            if (prestamo.getFechaDevolucion() != null) {
                
                String idDevolucion = UUID.randomUUID().toString();  
                Devolucion devolucion = new Devolucion(
                    idDevolucion,
                    prestamo.getIdPrestamo(),
                    prestamo.getIdLibro(),
                    prestamo.getIdCliente(),
                    prestamo.getFechaPrestamo(),
                    prestamo.getFechaDevolucion()
                );

               
                JSONArray devoluciones = JsonDB.leer("devoluciones.json");
                JSONObject devolucionJson = new JSONObject();
                devolucionJson.put("idDevolucion", devolucion.getIdDevolucion());
                devolucionJson.put("idPrestamo", devolucion.getIdPrestamo());
                devolucionJson.put("idLibro", devolucion.getIdLibro());
                devolucionJson.put("idCliente", devolucion.getIdCliente());
                devolucionJson.put("fechaPrestamo", devolucion.getFechaPrestamo().toString());
                devolucionJson.put("fechaDevolucion", devolucion.getFechaDevolucion().toString());

                devoluciones.add(devolucionJson);
                JsonDB.escribir("devoluciones.json", devoluciones);

                prestamo.registrarDevolucion();
                System.out.println("Devolución registrada: " + devolucion);
            } else {
                System.out.println("No se puede registrar la devolución, el préstamo no tiene una fecha de devolución válida.");
            }
        } else {
            System.out.println("El préstamo es nulo.");
        }
    }

    public JSONArray obtenerDevoluciones() {
        return JsonDB.leer("devoluciones.json");
    }
    public Devolucion buscarDevolucionPorId(String idDevolucion) {
        JSONArray devolucionesJson = JsonDB.leer("devoluciones.json");
        for (Object obj : devolucionesJson) {
            JSONObject jsonDevolucion = (JSONObject) obj;
            if (jsonDevolucion.get("idDevolucion").equals(idDevolucion)) {
                return new Devolucion(
                    (String) jsonDevolucion.get("idDevolucion"),
                    (String) jsonDevolucion.get("idPrestamo"),
                    (String) jsonDevolucion.get("idLibro"),
                    (String) jsonDevolucion.get("idCliente"),
                    LocalDate.parse((String) jsonDevolucion.get("fechaPrestamo")),
                    LocalDate.parse((String) jsonDevolucion.get("fechaDevolucion"))
                );
            }
        }
        return null;
    }
}
