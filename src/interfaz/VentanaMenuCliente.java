// VentanaMenuCliente.java
package interfaz;

import modelo.Libro;
import modelo.ServicioLibro;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaMenuCliente extends JFrame {
    private Usuario usuario;

    public VentanaMenuCliente(Usuario usuarioLogeado) {
        try {
            this.usuario = usuarioLogeado;

            setTitle("Menú Cliente");
            setSize(700, 500);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JTabbedPane pestañas = new JTabbedPane();

            // Pestaña Librosprestar
            JButton btnPrestarLibro = new JButton("Prestar Libro");
            btnPrestarLibro.addActionListener(e -> mostrarLibrosDisponibles());

            JPanel panelLibros = new JPanel();
            panelLibros.add(btnPrestarLibro);

            pestañas.addTab("📚 Libros", panelLibros);

            JPanel panelPerfil = new JPanel(new GridLayout(6, 1, 10, 10));
            panelPerfil.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
            panelPerfil.add(new JLabel("Nombre: " + usuario.getNombreCompleto()));
            panelPerfil.add(new JLabel("Correo: " + usuario.getCorreo()));
            panelPerfil.add(new JLabel("Usuario: " + usuario.getUsuario()));
            panelPerfil.add(new JLabel("Teléfono: " + usuario.getTelefono()));
            panelPerfil.add(new JLabel("Rol: " + usuario.getRol()));

            pestañas.addTab("👤 Mi perfil", panelPerfil);

            JButton btnCerrarSesion = new JButton("Cerrar sesión");
            btnCerrarSesion.addActionListener(e -> cerrarSesion());
            pestañas.addTab("Cerrar sesión", btnCerrarSesion);

            add(pestañas);
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar el menú cliente: " + e.getMessage());
        }
    }

    private void cerrarSesion() {
        dispose();
        new VentanaInicio().setVisible(true);
    }
    private void mostrarLibrosDisponibles() {
        
        JDialog ventana = new JDialog(this, "Libros Disponibles", true);
        ventana.setSize(750, 400);
        ventana.setLocationRelativeTo(this);
        
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Titulo", "Autor", "Estado", "Acción"}, 0
        );
    
        JTable tabla = new JTable(modelo);
        ventana.add(new JScrollPane(tabla));
    
        // ✔ AQUÍ EL CAMBIO IMPORTANTE
        List<Libro> libros = ServicioLibro.obtenerTodos();  // NO obtenerLibrosDisponibles()
    
        // Filtrar disponibles
        libros = libros.stream()
                .filter(Libro::isDisponible)
                .collect(Collectors.toList());
    
        for (Libro libro : libros) {
            modelo.addRow(new Object[]{
                    libro.getId(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.isDisponible() ? "Disponible" : "No disponible",
                    "Prestar"
            });
        }
    
        tabla.getColumn("Acción").setCellRenderer(new ButtonRenderer());
        tabla.getColumn("Acción").setCellEditor(new ButtonEditor(new JCheckBox()));
    
        ventana.setVisible(true);
    }

}

class ButtonRenderer extends JButton implements TableCellRenderer {

    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int col) {
        String estado = table.getValueAt(row, 3).toString();

        setText(estado.equals("Disponible") ? "Prestar" : "No disponible");
        setEnabled(estado.equals("Disponible"));

        return this;
    }
}
class ButtonEditor extends DefaultCellEditor {

    private JButton button;
    private String idLibro;
    private String titulo;
    private boolean clicked;

    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);

        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int col) {

        idLibro = table.getValueAt(row, 0).toString();
        titulo = table.getValueAt(row, 1).toString();

        String estado = table.getValueAt(row, 3).toString();
        button.setText(estado.equals("Disponible") ? "Prestar" : "No disponible");
        button.setEnabled(estado.equals("Disponible"));

        clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            registrarPrestamo();
        }
        clicked = false;
        return "Prestar";
    }

    private void registrarPrestamo() {

        ServicioLibro.actualizarDisponibilidad(idLibro, false);

        JOptionPane.showMessageDialog(button,
                "El libro '" + titulo + "' se ha prestado correctamente.");
    }
}
