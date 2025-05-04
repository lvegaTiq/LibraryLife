package modelo;

import javax.swing.JTextArea;

public class ArbolUser {
    private NodoUser raiz; 

    public void insertar(Usuario usuario) {
        raiz = insertarRec(raiz, usuario);
    }

    private NodoUser insertarRec(NodoUser nodo, Usuario usuario) {
        if (nodo == null) {
            return new NodoUser(usuario);
        }

        if (usuario.getUsuario().compareTo(nodo.usuario.getUsuario()) < 0) {
            nodo.izquierda = insertarRec(nodo.izquierda, usuario);
        } else {
            nodo.derecha = insertarRec(nodo.derecha, usuario);
        }
        return nodo;
    }

    public Usuario buscar(String nombreUsuario, String contrasena) {
        return buscarRec(raiz, nombreUsuario, contrasena);
    }

    private Usuario buscarRec(NodoUser nodo, String nombreUsuario, String contrasena) {
        if (nodo == null) return null;

        int cmp = nombreUsuario.compareTo(nodo.usuario.getUsuario());

        if (cmp == 0 && nodo.usuario.getContrasena().equals(contrasena)) {
            return nodo.usuario;
        } else if (cmp < 0) {
            return buscarRec(nodo.izquierda, nombreUsuario, contrasena);
        } else {
            return buscarRec(nodo.derecha, nombreUsuario, contrasena);
        }
    }

    public void inOrden(JTextArea area) {
        inOrdenRec(raiz, area);
    }

    private void inOrdenRec(NodoUser nodo, JTextArea area) {
        if (nodo != null) {
            inOrdenRec(nodo.izquierda, area);
            if (nodo.usuario != null) {
                area.append("Usuario: " + nodo.usuario.getUsuario() + "\n");
            }
            inOrdenRec(nodo.derecha, area);
        }
    }
}
