package interfaz;

import modelo.Libro;
import modelo.ServicioLibro;
import modelo.ServicioPrestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaRegistrarPrestamo extends JFrame {

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    public VentanaRegistrarPrestamo() {

        setTitle("Registrar Préstamo");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // ------------------------
        //    TABLA DE LIBROS
        // ------------------------
        String[] columnas = {"ID", "Título", "Autor", "Categoría", "Disponible"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaLibros = new JTable(modeloTabla);

        cargarLibros();

        JScrollPane scroll = new JScrollPane(tablaLibros);
        add(scroll, BorderLayout.CENTER);

        // ------------------------
        //  PANEL INFERIOR (Usuario + Botón)
        // ------------------------
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new GridLayout(2, 2, 10, 10));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel labelUsuario = new JLabel("Usuario que toma el préstamo:");
        JTextField campoUsuario = new JTextField();

        JButton btnRegistrar = new JButton("Registrar Préstamo");

        panelInferior.add(labelUsuario);
        panelInferior.add(campoUsuario);

        // espacio vacío estético
        panelInferior.add(new JLabel(""));
        panelInferior.add(btnRegistrar);

        add(panelInferior, BorderLayout.SOUTH);


        // ------------------------
        //  ACCIÓN DEL BOTÓN
        // ------------------------
        btnRegistrar.addActionListener(e -> {

            int fila = tablaLibros.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar un libro de la tabla.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String usuario = campoUsuario.getText().trim();
            if (usuario.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe ingresar el nombre del usuario.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String idLibro = tablaLibros.getValueAt(fila, 0).toString();
            String disponible = tablaLibros.getValueAt(fila, 4).toString();

            // Validar disponibilidad
            if (!disponible.equals("Sí")) {
                JOptionPane.showMessageDialog(this,
                        "El libro NO está disponible.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 1️⃣ Registrar préstamo en JSON
            ServicioPrestamo.registrarPrestamo(idLibro, usuario);

            // 2️⃣ Cambiar disponibilidad del libro
            ServicioLibro.actualizarDisponibilidad(idLibro, false);

            JOptionPane.showMessageDialog(this,
                    "¡Préstamo registrado correctamente!",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            // Actualizamos la tabla
            modeloTabla.setRowCount(0);
            cargarLibros();
        });

        setVisible(true);
    }


    // ------------------------
    // Cargar la tabla de libros
    // ------------------------
    private void cargarLibros() {
        List<Libro> libros = ServicioLibro.obtenerTodos();

        for (Libro l : libros) {
            modeloTabla.addRow(new Object[]{
                    l.getId(),
                    l.getTitulo(),
                    l.getAutor(),
                    l.getCategoria(),
                    l.isDisponible() ? "Sí" : "No"
            });
        }
    }
}
