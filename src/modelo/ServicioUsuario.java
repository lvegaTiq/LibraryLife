package modelo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.UUID;

public class ServicioUsuario {

    // Método para registrar un nuevo usuario
    public static void registrarUsuario(Usuario usuario) {
        JSONArray usuarios = JsonDB.leer("usuarios.json");

        // Generar un ID único para el usuario
        String id = UUID.randomUUID().toString();  // Usando UUID para generar un ID único

        // Crear un nuevo objeto JSON con los datos del usuario
        JSONObject nuevo = new JSONObject();
        nuevo.put("id", id);
        nuevo.put("nombreCompleto", usuario.getNombreCompleto());
        nuevo.put("correo", usuario.getCorreo());
        nuevo.put("usuario", usuario.getUsuario());
        nuevo.put("contrasena", usuario.getContrasena());
        nuevo.put("telefono", usuario.getTelefono());
        nuevo.put("rol", usuario.getRol());

        // Añadir el nuevo usuario al archivo JSON
        usuarios.add(nuevo);
        JsonDB.escribir("usuarios.json", usuarios);
    }

    // Método de login
    public static Usuario login(String usuario, String contrasena) {
        JSONArray usuarios = JsonDB.leer("usuarios.json");

        for (Object obj : usuarios) {
            JSONObject jsonUser = (JSONObject) obj;
            if (jsonUser.get("usuario").equals(usuario) && jsonUser.get("contrasena").equals(contrasena)) {
                return new Usuario(
                    (String) jsonUser.get("id"), // Obtener el ID del usuario desde el JSON
                    (String) jsonUser.get("nombreCompleto"),
                    (String) jsonUser.get("correo"),
                    (String) jsonUser.get("usuario"),
                    (String) jsonUser.get("contrasena"),
                    (String) jsonUser.get("telefono")
                );
            }
        }

        return null; // Si no se encuentra el usuario
    }
}
