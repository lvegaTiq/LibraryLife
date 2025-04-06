package interfaz;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import modelo.Libro;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class VentanaRegistroLibro extends JFrame {

    public VentanaRegistroLibro() {
        setTitle("Registrar Libro");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel lblTitulo = new JLabel("Título:");
        JTextField txtTitulo = new JTextField();
        JLabel lblAutor = new JLabel("Autor:");
        JTextField txtAutor = new JTextField();
        JLabel lblCategoria = new JLabel("Categoría:");
        JTextField txtCategoria = new JTextField();
        JLabel lblAnio = new JLabel("Año:");
        JTextField txtAnio = new JTextField();

        JButton btnGuardar = new JButton("Guardar");

        add(lblTitulo); add(txtTitulo);
        add(lblAutor); add(txtAutor);
        add(lblCategoria); add(txtCategoria);
        add(lblAnio); add(txtAnio);
        add(new JLabel()); add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            String categoria = txtCategoria.getText();
            String anio = txtAnio.getText();

            if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty() || anio.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
                return;
            }

            // Crear objeto Libro con ID generado automáticamente
            Libro libro = new Libro(titulo, autor, categoria, true);
            
            // Guardar el libro en el archivo JSON
            agregarLibro(libro, anio);

        });
    }

    // Método para guardar el libro en el archivo JSON
    private void agregarLibro(Libro libro, String anio) {
        // Ahora, guardamos el libro en el archivo JSON
        JSONObject libroJSON = new JSONObject();
        libroJSON.put("id", libro.getId());  // Añadir el ID
        libroJSON.put("titulo", libro.getTitulo());
        libroJSON.put("autor", libro.getAutor());
        libroJSON.put("categoria", libro.getCategoria());
        libroJSON.put("anio", anio);
        libroJSON.put("disponible", true);

        try {
            Path path = Paths.get("libros.json");
            JSONArray libros = new JSONArray();

            // Verificar si el archivo JSON ya existe
            if (Files.exists(path)) {
                // Leer el contenido existente del archivo
                String contenido = Files.readString(path);
                JSONParser parser = new JSONParser();
                libros = (JSONArray) parser.parse(contenido);
            }

            // Agregar el nuevo libro al JSON
            libros.add(libroJSON);

            // Escribir el contenido actualizado en el archivo
            Files.writeString(path, libros.toJSONString());

            JOptionPane.showMessageDialog(this, "Libro registrado exitosamente.");
            dispose(); // Cerrar la ventana después de guardar

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar el libro: " + ex.getMessage());
        }
    }
}
