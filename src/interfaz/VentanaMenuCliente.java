package interfaz;

import modelo.Libro;
import modelo.Prestamo;
import modelo.ServicioLibro;
import modelo.ServicioPrestamo;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaMenuCliente extends JFrame {

    public Usuario usuario;

    public VentanaMenuCliente(Usuario usuarioLogeado) {
        this.usuario = usuarioLogeado;

        setTitle("Menú del Cliente");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel fondo = crearFondoDegradado();
        fondo.setLayout(new BorderLayout());
        add(fondo);

        JLabel titulo = new JLabel("Bienvenido, " + usuario.getNombreCompleto(), SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        fondo.add(titulo, BorderLayout.NORTH);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pestañas.setBackground(new Color(40, 40, 40));
        pestañas.setForeground(Color.WHITE);

        pestañas.add("📚 Libros", crearPanelLibros());
        pestañas.add("👤 Mi Perfil", crearPanelPerfil());
        pestañas.add("⛔ Cerrar sesión", crearPanelLogout());

        fondo.add(pestañas, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel crearFondoDegradado() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(40, 60, 95),
                        0, getHeight(), new Color(20, 25, 45)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
    }

    private JPanel crearPanelLibros() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        JButton btnPrestar = crearBoton("📚 Prestar Libro");
        btnPrestar.addActionListener(e -> mostrarLibrosDisponibles());

        JButton btnDevolver = crearBoton("📕 Devolver Libro");
        btnDevolver.addActionListener(e -> mostrarLibrosPrestados());

        panel.add(btnPrestar);
        panel.add(btnDevolver);

        return panel;
    }

    private JPanel crearPanelPerfil() {

        JPanel fondo = new JPanel();
        fondo.setOpaque(false);
        fondo.setLayout(null);
        
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBackground(Color.WHITE);
        card.setBounds(150, 40, 600, 380);
        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        ImageIcon icono = new ImageIcon("usuario.png");
        Image img = icono.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel foto = new JLabel(new ImageIcon(img));
        foto.setBounds(30, 20, 80, 80);
        card.add(foto);

        JLabel lblNombre = new JLabel(usuario.getNombreCompleto());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblNombre.setBounds(130, 25, 400, 30);
        card.add(lblNombre);

        JLabel lblCorreo = new JLabel(usuario.getCorreo());
        lblCorreo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblCorreo.setForeground(new Color(120,120,120));
        lblCorreo.setBounds(130, 55, 400, 30);
        card.add(lblCorreo);

        JButton btnEditar = new JButton("Edit");
        btnEditar.setBounds(480, 35, 70, 30);
        btnEditar.setBackground(new Color(73, 134, 255));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFocusPainted(false);
        btnEditar.setBorder(BorderFactory.createEmptyBorder());
        card.add(btnEditar);

        int xL = 30, xR = 300, y = 130, w = 250, h = 40;

        card.add(crearCampoInfo("Usuario:", usuario.getUsuario(), xL, y, w, h));
        card.add(crearCampoInfo("Teléfono:", usuario.getTelefono(), xR, y, w, h));

        y += 70;
        card.add(crearCampoInfo("Rol:", usuario.getRol(), xL, y, w, h));
        card.add(crearCampoInfo("ID Interno:", usuario.getId(), xR, y, w, h));

        fondo.add(card);
        return fondo;
    }

    private JPanel crearCampoInfo(String label, String valor, int x, int y, int w, int h) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 246, 248));
        panel.setBounds(x, y, w, h);
        panel.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setBounds(10, 0, w, 15);
        panel.add(lbl);

        JLabel txt = new JLabel(valor);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBounds(10, 15, w, 25);
        panel.add(txt);

        return panel;
    }


    private JPanel crearPanelLogout() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);

        JButton btnSalir = crearBoton("Cerrar sesión");
        btnSalir.addActionListener(e -> {
            dispose();
            new VentanaInicio().setVisible(true);
        });

        panel.add(btnSalir);
        return panel;
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setBackground(new Color(80, 140, 230));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(100, 160, 250));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(80, 140, 230));
            }
        });

        return btn;
    }

    private void mostrarLibrosDisponibles() {
        JDialog ventana = new JDialog(this, "Libros Disponibles", true);
        ventana.setSize(800, 400);
        ventana.setLocationRelativeTo(this);

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Título", "Autor", "Estado", "Acción"}, 0
        );

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(28);

        ventana.add(new JScrollPane(tabla));

        List<Libro> libros = ServicioLibro.obtenerTodos()
                .stream().filter(Libro::isDisponible).collect(Collectors.toList());

        for (Libro libro : libros) {
            modelo.addRow(new Object[]{
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    "Disponible",
                    "Prestar"
            });
        }

        tabla.getColumn("Acción").setCellRenderer(new ButtonRendererPrestar());
        tabla.getColumn("Acción").setCellEditor(new ButtonEditorPrestar(new JCheckBox(), this));

        ventana.setVisible(true);
    }

    private void mostrarLibrosPrestados() {
        JDialog ventana = new JDialog(this, "Libros Prestados", true);
        ventana.setSize(800, 400);
        ventana.setLocationRelativeTo(this);

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Título", "Autor", "Fecha Préstamo", "Fecha Límite", "Acción"}, 0
        );

        JTable tabla = new JTable(modelo);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(28);

        ventana.add(new JScrollPane(tabla));

        List<Prestamo> prestamos = ServicioPrestamo.obtenerPrestamosPorUsuario(usuario.getUsuario());

        for (Prestamo p : prestamos) {
            Libro libro = ServicioLibro.obtenerLibroPorId(p.getIdLibro());
            modelo.addRow(new Object[]{
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    p.getFechaPrestamo(),
                    p.getFechaDevolucion(),
                    "Devolver"
            });
        }

        tabla.getColumn("Acción").setCellRenderer(new ButtonRendererDevolver());
        tabla.getColumn("Acción").setCellEditor(new ButtonEditorDevolver(new JCheckBox(), this));

        ventana.setVisible(true);
    }
}

class ButtonRendererPrestar extends JButton implements TableCellRenderer {
    public ButtonRendererPrestar() {
        setOpaque(true);
        setBackground(new Color(70, 130, 250));
        setForeground(Color.WHITE);
        setBorder(null);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean selected, boolean focus,
                                                   int row, int col) {
        setText("Prestar");
        return this;
    }
}

class ButtonEditorPrestar extends DefaultCellEditor {

    private JButton button;
    private String idLibro;
    private String titulo;
    private boolean clicked;
    private VentanaMenuCliente ventana;

    public ButtonEditorPrestar(JCheckBox checkBox, VentanaMenuCliente ventana) {
        super(checkBox);
        this.ventana = ventana;

        button = new JButton("Prestar");
        button.setOpaque(true);
        button.setBackground(new Color(70, 130, 250));
        button.setForeground(Color.WHITE);
        button.addActionListener(e -> fireEditingStopped());
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean selected, int row, int col) {

        idLibro = table.getValueAt(row, 0).toString();
        titulo  = table.getValueAt(row, 1).toString();
        clicked = true;

        return button;
    }

    public Object getCellEditorValue() {

        if (clicked) {
            ServicioPrestamo.registrarPrestamo(idLibro, ventana.usuario.getUsuario());
            ServicioLibro.actualizarDisponibilidad(idLibro, false);

            JOptionPane.showMessageDialog(button,
                    "📘 El libro '" + titulo + "' ha sido prestado correctamente.");
        }

        clicked = false;
        return "Prestar";
    }
}



class ButtonRendererDevolver extends JButton implements TableCellRenderer {
    public ButtonRendererDevolver() {
        setOpaque(true);
        setBackground(new Color(230, 100, 80));
        setForeground(Color.WHITE);
        setBorder(null);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean selected, boolean focus,
                                                   int row, int col) {
        setText("Devolver");
        return this;
    }
}

class ButtonEditorDevolver extends DefaultCellEditor {

    private JButton button;
    private String idLibro;
    private VentanaMenuCliente ventana;
    private boolean clicked;

    public ButtonEditorDevolver(JCheckBox box, VentanaMenuCliente ventana) {
        super(box);
        this.ventana = ventana;

        button = new JButton("Devolver");
        button.setOpaque(true);
        button.setBackground(new Color(230, 100, 80));
        button.setForeground(Color.WHITE);
        button.addActionListener(e -> fireEditingStopped());
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean selected, int row, int col) {

        idLibro = table.getValueAt(row, 0).toString();
        clicked = true;
        return button;
    }

    public Object getCellEditorValue() {
        if (clicked) {
            ServicioPrestamo.marcarComoDevuelto(idLibro);
            ServicioLibro.actualizarDisponibilidad(idLibro, true);

            JOptionPane.showMessageDialog(button, "📖 Libro devuelto correctamente.");
        }

        clicked = false;
        return "Devolver";
    }
}
