package modelo;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.UUID;

public class ServicioUsuario {

    public static void registrarUsuario(Usuario usuario) {
        JSONArray usuarios = JsonDB.leer("usuarios.json");

        String id = UUID.randomUUID().toString();  

        JSONObject nuevo = new JSONObject();
        nuevo.put("id", id);
        nuevo.put("nombreCompleto", usuario.getNombreCompleto());
        nuevo.put("correo", usuario.getCorreo());
        nuevo.put("usuario", usuario.getUsuario());
        nuevo.put("contrasena", usuario.getContrasena());
        nuevo.put("telefono", usuario.getTelefono());
        nuevo.put("rol", usuario.getRol());

        usuarios.add(nuevo);
        JsonDB.escribir("usuarios.json", usuarios);
    }

    public static Usuario login(String usuario, String contrasena) {
        JSONArray usuarios = JsonDB.leer("usuarios.json");

        for (Object obj : usuarios) {
            JSONObject jsonUser = (JSONObject) obj;
            if (jsonUser.get("usuario").equals(usuario) && jsonUser.get("contrasena").equals(contrasena)) {
                return new Usuario(
                    (String) jsonUser.get("id"), 
                    (String) jsonUser.get("nombreCompleto"),
                    (String) jsonUser.get("correo"),
                    (String) jsonUser.get("usuario"),
                    (String) jsonUser.get("contrasena"),
                    (String) jsonUser.get("telefono")
                );
            }
        }

        return null;
    }
}
