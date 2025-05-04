package modelo;

public class NodoUser {

    public Usuario usuario;
    public NodoUser izquierda;
    public NodoUser derecha;

    public NodoUser(Usuario usuario){
        this.usuario = usuario;
    }
}   
