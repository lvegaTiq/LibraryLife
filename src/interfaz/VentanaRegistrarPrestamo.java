package interfaz;

import modelo.Libro;
import modelo.ServicioLibro;
import modelo.ServicioPrestamo;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class VentanaRegistrarPrestamo extends JFrame {

    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;

    public VentanaRegistrarPrestamo() {

        setTitle("Registrar Préstamo");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        Color azulFondo = new Color(30, 60, 155);
        Color grisSuave = new Color(242, 245, 250); 
        Color cardColor = Color.WHITE;
        Color azulPrimary = new Color(30, 105, 240);

        JPanel sidebar = new JPanel();
        sidebar.setBackground(azulFondo);
        sidebar.setBounds(0, 0, 150, 650);
        sidebar.setLayout(null);
        add(sidebar);

        JLabel tituloSide = new JLabel("<html><center>BIBLIO<br>ADMIN</center></html>");
        tituloSide.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tituloSide.setForeground(Color.WHITE);
        tituloSide.setHorizontalAlignment(SwingConstants.CENTER);
        tituloSide.setBounds(10, 40, 130, 80);
        sidebar.add(tituloSide);

        JPanel panelMain = new JPanel();
        panelMain.setBackground(grisSuave);
        panelMain.setBounds(150, 0, 850, 650);
        panelMain.setLayout(null);
        add(panelMain);

        JLabel titulo = new JLabel("Registrar Préstamo");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setForeground(new Color(30, 30, 30));
        titulo.setBounds(30, 20, 400, 40);
        panelMain.add(titulo);

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(cardColor);
        card.setBounds(30, 90, 800, 380);
        card.setBorder(new LineBorder(new Color(210, 210, 210), 1, true));
        panelMain.add(card);

        String[] columnas = {"ID", "Título", "Autor", "Categoría", "Disponible"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaLibros = new JTable(modeloTabla);

        tablaLibros.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaLibros.setRowHeight(28);
        tablaLibros.setBackground(Color.WHITE);
        tablaLibros.setForeground(Color.DARK_GRAY);
        tablaLibros.setSelectionBackground(new Color(220, 230, 255));

        JTableHeader header = tablaLibros.getTableHeader();
        header.setBackground(new Color(230, 230, 230));
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        cargarLibros();

        JScrollPane scroll = new JScrollPane(tablaLibros);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        card.add(scroll, BorderLayout.CENTER);

        JLabel labelUsuario = new JLabel("Usuario:");
        labelUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        labelUsuario.setBounds(30, 500, 100, 30);
        panelMain.add(labelUsuario);

        JTextField campoUsuario = new JTextField();
        campoUsuario.setBounds(120, 500, 260, 35);
        campoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        campoUsuario.setBackground(Color.WHITE);
        campoUsuario.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
        panelMain.add(campoUsuario);

        JButton btnRegistrar = new JButton("Registrar Préstamo");
        btnRegistrar.setBounds(600, 495, 220, 45);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBackground(azulPrimary);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorder(new LineBorder(azulPrimary.darker(), 2, true));

        btnRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistrar.setBackground(new Color(25, 90, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistrar.setBackground(azulPrimary);
            }
        });

        panelMain.add(btnRegistrar);

        btnRegistrar.addActionListener(e -> {

            int fila = tablaLibros.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un libro.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String usuario = campoUsuario.getText().trim();
            if (usuario.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String idLibro = modeloTabla.getValueAt(fila, 0).toString();
            boolean disponible = modeloTabla.getValueAt(fila, 4).toString().equals("Sí");

            if (!disponible) {
                JOptionPane.showMessageDialog(this, "El libro no está disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ServicioPrestamo.registrarPrestamo(idLibro, usuario);
            ServicioLibro.actualizarDisponibilidad(idLibro, false);

            JOptionPane.showMessageDialog(this, "Préstamo registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            modeloTabla.setRowCount(0);
            cargarLibros();
        });

        setVisible(true);
    }

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
