package modelo;

public class Usuario {
    private String id;
    private String nombreCompleto;
    private String correo;
    private String usuario;
    private String contrasena;
    private String telefono;
    private String rol;

    public Usuario(String id, String nombreCompleto, String correo, String usuario, String contrasena, String telefono) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.rol = correo.endsWith("@bibliograf.com") ? "administrador" : "cliente";
    }

    public Usuario(String nombreCompleto, String correo, String usuario, String contrasena, String telefono) {
        this.id = null;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.rol = correo.endsWith("@bibliograf.com") ? "administrador" : "cliente";
    }

    public String getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCorreo() { return correo; }
    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
    public String getTelefono() { return telefono; }
    public String getRol() { return rol; }
}
