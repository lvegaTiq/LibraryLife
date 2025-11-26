package interfaz;

import modelo.Libro;
import modelo.ServicioLibro;
import modelo.ServicioPrestamo;
import modelo.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VentanaPrestamoLibrosAdmin extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public VentanaPrestamoLibrosAdmin() {

        setTitle("Préstamos Registrados");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] columnas = {
                "ID Préstamo",
                "Usuario",
                "ID Libro",
                "Título",
                "Autor",
                "Fecha Préstamo",
                "Fecha Límite",
                "Devuelto"
        };

        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);

        cargarPrestamos();

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JButton btnRegresar = new JButton("Regresar");
        btnRegresar.addActionListener(e -> dispose());

        JPanel panel = new JPanel();
        panel.add(btnRegresar);

        add(panel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void cargarPrestamos() {

        modeloTabla.setRowCount(0);

        List<Prestamo> prestamos = ServicioPrestamo.obtenerTodosLosPrestamos();

        for (Prestamo p : prestamos) {

            Libro libro = ServicioLibro.obtenerLibroPorId(p.getIdLibro());

            modeloTabla.addRow(new Object[]{
                    p.getIdPrestamo(),
                    p.getIdCliente(),
                    p.getIdLibro(),
                    libro != null ? libro.getTitulo() : "Desconocido",
                    libro != null ? libro.getAutor() : "Desconocido",
                    p.getFechaPrestamo(),
                    p.getFechaDevolucion(),
                    p.isDevuelto() ? "Sí" : "No"
            });
        }
    }
}
