package interfaz;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import modelo.Libro;

public class VentanaRegistroLibro extends JFrame {

    public VentanaRegistroLibro() {

        setTitle("Registrar Libro");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        Color fondo = new Color(20, 25, 35);
        Color cardColor = new Color(40, 48, 65);
        Color inputColor = new Color(55, 62, 80);
        Color azulBoton = new Color(0, 122, 255);
        Color texto = new Color(230, 230, 230);

        getContentPane().setBackground(fondo);

        JLabel titulo = new JLabel("📘 Registrar Nuevo Libro");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(30, 20, 400, 40);
        add(titulo);

        JPanel linea = new JPanel();
        linea.setBackground(new Color(80, 90, 110));
        linea.setBounds(30, 65, 530, 2);
        add(linea);

        JPanel card = new JPanel();
        card.setBackground(cardColor);
        card.setBorder(new LineBorder(new Color(70, 80, 100), 1, true));
        card.setBounds(30, 90, 530, 280);
        card.setLayout(null);
        add(card);

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(30, 20, 200, 25);
        lblTitulo.setForeground(texto);
        lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        card.add(lblTitulo);

        JTextField txtTitulo = new JTextField();
        txtTitulo.setBounds(150, 20, 330, 30);
        estilizarInput(txtTitulo, inputColor, texto);
        card.add(txtTitulo);

        JLabel lblAutor = new JLabel("Autor:");
        lblAutor.setBounds(30, 70, 200, 25);
        lblAutor.setForeground(texto);
        lblAutor.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        card.add(lblAutor);

        JTextField txtAutor = new JTextField();
        txtAutor.setBounds(150, 70, 330, 30);
        estilizarInput(txtAutor, inputColor, texto);
        card.add(txtAutor);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(30, 120, 200, 25);
        lblCategoria.setForeground(texto);
        lblCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        card.add(lblCategoria);

        JTextField txtCategoria = new JTextField();
        txtCategoria.setBounds(150, 120, 330, 30);
        estilizarInput(txtCategoria, inputColor, texto);
        card.add(txtCategoria);

        JLabel lblAnio = new JLabel("Año:");
        lblAnio.setBounds(30, 170, 200, 25);
        lblAnio.setForeground(texto);
        lblAnio.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        card.add(lblAnio);

        JTextField txtAnio = new JTextField();
        txtAnio.setBounds(150, 170, 330, 30);
        estilizarInput(txtAnio, inputColor, texto);
        card.add(txtAnio);

        JButton btnGuardar = new JButton("Guardar Libro");
        btnGuardar.setBounds(330, 220, 150, 40);
        btnGuardar.setBackground(azulBoton);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorder(new LineBorder(azulBoton.brighter(), 2, true));

        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardar.setBackground(new Color(50, 100, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardar.setBackground(azulBoton);
            }
        });

        card.add(btnGuardar);

        btnGuardar.addActionListener(e -> {

            String tituloLibro = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            String categoria = txtCategoria.getText().trim();
            String anio = txtAnio.getText().trim();

            if (tituloLibro.isEmpty() || autor.isEmpty() || categoria.isEmpty() || anio.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
                return;
            }

            Libro libro = new Libro(tituloLibro, autor, categoria, true);
            agregarLibro(libro);

            JOptionPane.showMessageDialog(this, "📘 Libro registrado correctamente");
            dispose();
        });

        setVisible(true);
    }

    private void estilizarInput(JTextField field, Color fondo, Color texto) {
        field.setBackground(fondo);
        field.setForeground(texto);
        field.setCaretColor(Color.WHITE);
        field.setBorder(new LineBorder(new Color(120, 130, 150), 1, true));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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

            FileWriter writer = new FileWriter("Libros.json");
            writer.write(array.toJSONString());
            writer.close();

        } catch (Exception e) {
            System.out.println("Error al agregar libro: " + e.getMessage());
        }
    }
}
