package modelo;

import java.util.UUID;

public class Libro {
    private String id; 
    private String titulo;
    private String autor;
    private String categoria;
    private boolean disponible;

    // Constructor actualizado con ID
    public Libro(String titulo, String autor, String categoria, boolean disponible) {
        this.id = UUID.randomUUID().toString(); // Genera un ID único para cada libro
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.disponible = disponible;
    }

    // Métodos Getters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getCategoria() { return categoria; }
    public boolean isDisponible() { return disponible; }
}
