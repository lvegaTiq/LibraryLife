package modelo;

public class Usuario {
    private String id;  // Nuevo campo ID
    private String nombreCompleto;
    private String correo;
    private String usuario;
    private String contrasena;
    private String telefono;
    private String rol;

    // Constructor con ID opcional
    public Usuario(String id, String nombreCompleto, String correo, String usuario, String contrasena, String telefono) {
        this.id = id;  // Asignar el ID generado automáticamente
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.rol = correo.endsWith("@bibliograf.com") ? "administrador" : "cliente";
    }

    // Constructor sin ID (para crear un nuevo usuario, el ID se genera automáticamente)
    public Usuario(String nombreCompleto, String correo, String usuario, String contrasena, String telefono) {
        this.id = null;  // Este ID será asignado más tarde en ServicioUsuario
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.rol = correo.endsWith("@bibliograf.com") ? "administrador" : "cliente";
    }

    // Métodos getter para todos los campos
    public String getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCorreo() { return correo; }
    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
    public String getTelefono() { return telefono; }
    public String getRol() { return rol; }
}
