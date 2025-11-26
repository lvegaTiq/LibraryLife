package interfaz;

import modelo.Libro;
import modelo.Prestamo;
import modelo.ServicioLibro;
import modelo.ServicioPrestamo;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaMenuCliente extends JFrame {

    public Usuario usuario;

    public VentanaMenuCliente(Usuario usuarioLogeado) {
        this.usuario = usuarioLogeado;

        setTitle("Menú Cliente");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane pestañas = new JTabbedPane();

        // Panel Libros
        JPanel panelLibros = new JPanel();

        JButton btnPrestar = new JButton("Prestar Libro");
        btnPrestar.addActionListener(e -> mostrarLibrosDisponibles());
        panelLibros.add(btnPrestar);

        JButton btnDevolver = new JButton("Devolver Libro");
        btnDevolver.addActionListener(e -> mostrarLibrosPrestados());
        panelLibros.add(btnDevolver);

        pestañas.addTab("📚 Libros", panelLibros);

        // Panel perfil
        JPanel panelPerfil = new JPanel(new GridLayout(6, 1, 10, 10));
        panelPerfil.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelPerfil.add(new JLabel("Nombre: " + usuario.getNombreCompleto()));
        panelPerfil.add(new JLabel("Correo: " + usuario.getCorreo()));
        panelPerfil.add(new JLabel("Usuario: " + usuario.getUsuario()));
        panelPerfil.add(new JLabel("Teléfono: " + usuario.getTelefono()));
        panelPerfil.add(new JLabel("Rol: " + usuario.getRol()));
        pestañas.addTab("👤 Mi perfil", panelPerfil);

        // Cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.addActionListener(e -> {
            dispose();
            new VentanaInicio().setVisible(true);
        });
        pestañas.addTab("Cerrar sesión", btnCerrarSesion);

        add(pestañas);
        setVisible(true);
    }

    // ==========================================================
    // MOSTRAR LIBROS DISPONIBLES PARA PRESTAR
    // ==========================================================
    private void mostrarLibrosDisponibles() {

        JDialog ventana = new JDialog(this, "Libros Disponibles", true);
        ventana.setSize(750, 400);
        ventana.setLocationRelativeTo(this);

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Titulo", "Autor", "Estado", "Acción"}, 0
        );

        JTable tabla = new JTable(modelo);
        ventana.add(new JScrollPane(tabla));

        // Cargar todos los libros disponibles
        List<Libro> libros = ServicioLibro.obtenerTodos()
                .stream()
                .filter(Libro::isDisponible)
                .collect(Collectors.toList());

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

    // ==========================================================
    // MOSTRAR LIBROS PRESTADOS PARA DEVOLVER
    // ==========================================================
    private void mostrarLibrosPrestados() {

        JDialog ventana = new JDialog(this, "Mis Libros Prestados", true);
        ventana.setSize(800, 400);
        ventana.setLocationRelativeTo(this);

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Título", "Autor", "Fecha Préstamo", "Fecha Límite", "Acción"}, 0
        );

        JTable tabla = new JTable(modelo);
        ventana.add(new JScrollPane(tabla));

        List<Prestamo> lista = ServicioPrestamo.obtenerPrestamosPorUsuario(usuario.getUsuario());

        for (Prestamo p : lista) {

            Libro libro = ServicioLibro.obtenerLibroPorId(p.getIdLibro());

            modelo.addRow(new Object[]{
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    p.getFechaPrestamo(),
                    p.getFechaDevolucion(), // FECHA LÍMITE
                    "Devolver"
            });
        }

        tabla.getColumn("Acción").setCellRenderer(new ButtonRendererDevolver());
        tabla.getColumn("Acción").setCellEditor(new ButtonEditorDevolver(new JCheckBox(), this));

        ventana.setVisible(true);
    }
}


// ============================================================================
// RENDERER / EDITOR PRESTAR
// ============================================================================
class ButtonRendererPrestar extends JButton implements TableCellRenderer {
    public ButtonRendererPrestar() { setOpaque(true); }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
                                                   boolean focus, int row, int col) {
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
        button = new JButton();
        button.setOpaque(true);
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
        if (clicked) registrarPrestamo();
        clicked = false;
        return "Prestar";
    }

    private void registrarPrestamo() {

        ServicioPrestamo.registrarPrestamo(idLibro, ventana.usuario.getUsuario());

        ServicioLibro.actualizarDisponibilidad(idLibro, false);

        JOptionPane.showMessageDialog(button,
                "📚 El libro '" + titulo + "' ha sido prestado correctamente.");

        SwingUtilities.getWindowAncestor(button).dispose();
    }
}


class ButtonRendererDevolver extends JButton implements TableCellRenderer {
    public ButtonRendererDevolver() { setOpaque(true); }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
                                                   boolean focus, int row, int col) {
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
        button = new JButton();
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean selected, int row, int col) {

        idLibro = table.getValueAt(row, 0).toString();
        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {

        if (clicked) devolverLibro();

        clicked = false;
        return "Devolver";
    }

    private void devolverLibro() {

        ServicioPrestamo.marcarComoDevuelto(idLibro);

        ServicioLibro.actualizarDisponibilidad(idLibro, true);

        JOptionPane.showMessageDialog(button, "📖 Libro devuelto correctamente.");

        SwingUtilities.getWindowAncestor(button).dispose();
    }
}
