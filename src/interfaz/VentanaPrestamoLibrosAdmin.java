package interfaz;

import modelo.Libro;
import modelo.ServicioLibro;
import modelo.ServicioPrestamo;
import modelo.Prestamo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class VentanaPrestamoLibrosAdmin extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public VentanaPrestamoLibrosAdmin() {

        setTitle("Préstamos Registrados");
        setSize(1200, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        Color fondo = new Color(18, 22, 33);
        Color sidebarColor = new Color(28, 32, 44);
        Color cardColor = new Color(40, 46, 60);
        Color tablaHeaderColor = new Color(65, 73, 95);
        Color tablaRow = new Color(50, 58, 75);
        Color azul = new Color(0, 122, 255);

        getContentPane().setBackground(fondo);

        JPanel sidebar = new JPanel();
        sidebar.setBackground(sidebarColor);
        sidebar.setLayout(null);
        sidebar.setBounds(0, 0, 85, 680);

        sidebar.add(crearIcono("📘", 28, 100));
        sidebar.add(crearIcono("📚", 28, 180));
        sidebar.add(crearIcono("👤", 28, 260));
        sidebar.add(crearIcono("⚙️", 28, 340));

        add(sidebar);

        JLabel titulo = new JLabel("📘 Préstamos Registrados");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(110, 20, 600, 40);
        add(titulo);

        JPanel linea = new JPanel();
        linea.setBackground(new Color(70, 80, 100));
        linea.setBounds(110, 65, 1000, 2);
        add(linea);

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(cardColor);
        card.setBorder(new LineBorder(new Color(70, 80, 100), 1, true));
        card.setBounds(110, 90, 1000, 500);
        add(card);


        String[] columnas = {
                "ID Préstamo", "Usuario", "ID Libro", "Título", "Autor",
                "Fecha Préstamo", "Fecha Límite", "Devuelto"
        };

        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);

        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(28);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setBackground(tablaRow);
        tabla.setForeground(Color.WHITE);
        tabla.setSelectionBackground(new Color(70, 80, 120));

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(tablaHeaderColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        cargarPrestamos();

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        card.add(scroll, BorderLayout.CENTER);

        JButton btnRegresar = new JButton("⬅ Regresar");
        btnRegresar.setBounds(950, 610, 160, 40);
        btnRegresar.setBackground(azul);
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRegresar.setBorder(new LineBorder(azul.brighter(), 2, true));

        btnRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegresar.setBackground(new Color(40, 100, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegresar.setBackground(azul);
            }
        });

        btnRegresar.addActionListener(e -> dispose());

        add(btnRegresar);

        setVisible(true);
    }

    private JLabel crearIcono(String emoji, int x, int y) {
        JLabel lbl = new JLabel(emoji, SwingConstants.CENTER);
        lbl.setBounds(x, y, 35, 35);
        lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        lbl.setForeground(Color.WHITE);
        return lbl;
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
