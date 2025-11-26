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
        
            Libro libro = new Libro(titulo, autor, categoria, true);
            
            agregarLibro(libro);
        
            JOptionPane.showMessageDialog(this, "Libro registrado correctamente");
            dispose();
        });

    }

    public static void agregarLibro(Libro libro) {
        try {
            JSONParser parser = new JSONParser();
            JSONArray array;

            try {
                FileReader reader = new FileReader("Libros.json");
                array = (JSONArray) parser.parse(reader);
            } catch (Exception e) {
                array = new JSONArray();
            }

            JSONObject nuevo = new JSONObject();
            nuevo.put("id", libro.getId());
            nuevo.put("titulo", libro.getTitulo());
            nuevo.put("autor", libro.getAutor());
            nuevo.put("categoria", libro.getCategoria());
            nuevo.put("disponible", libro.isDisponible());

            array.add(nuevo);

            try (FileWriter writer = new FileWriter("Libros.json")) {
                writer.write(array.toJSONString());
                writer.flush();
            }

        } catch (Exception e) {
            System.out.println("Error al agregar libro: " + e.getMessage());
        }
    }

}
