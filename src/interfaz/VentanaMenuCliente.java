// VentanaMenuCliente.java
package interfaz;

import modelo.Libro;
import modelo.ServicioLibro;
import modelo.Usuario;

import javax.swing.*;
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

            // Pestaña Libros
            JButton btnPrestarLibro = new JButton("Prestar Libro");
            btnPrestarLibro.addActionListener(e -> mostrarLibrosDisponibles());

            JPanel panelLibros = new JPanel();
            panelLibros.add(btnPrestarLibro);

            pestañas.addTab("📚 Libros", panelLibros);

            // Pestaña Perfil
            JPanel panelPerfil = new JPanel(new GridLayout(6, 1, 10, 10));
            panelPerfil.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
            panelPerfil.add(new JLabel("Nombre: " + usuario.getNombreCompleto()));
            panelPerfil.add(new JLabel("Correo: " + usuario.getCorreo()));
            panelPerfil.add(new JLabel("Usuario: " + usuario.getUsuario()));
            panelPerfil.add(new JLabel("Teléfono: " + usuario.getTelefono()));
            panelPerfil.add(new JLabel("Rol: " + usuario.getRol()));

            pestañas.addTab("👤 Mi perfil", panelPerfil);

            // Pestaña de Cerrar sesión
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
        new VentanaInicio().setVisible(true); // Aquí se abriría la ventana de inicio de sesión.
    }

    private void mostrarLibrosDisponibles() {
        // Obtener la lista de libros disponibles
        List<Libro> librosDisponibles = ServicioLibro.obtenerLibrosDisponibles();
        
        // Verificar si la lista está vacía
        if (librosDisponibles == null || librosDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la lista de libros.");
            return;
        }
        
        // Filtrar los libros que están disponibles (disponible == true)
        List<Libro> librosDisponiblesFiltro = librosDisponibles.stream()
                .filter(Libro::isDisponible) // Solo libros disponibles
                .collect(Collectors.toList());
        
        // Verificar si hay libros disponibles para prestar
        if (librosDisponiblesFiltro.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay libros disponibles para préstamo.");
            return;
        }
        
        // Crear un array para mostrar títulos y autores
        String[] librosArray = new String[librosDisponiblesFiltro.size()];
        for (int i = 0; i < librosDisponiblesFiltro.size(); i++) {
            Libro libro = librosDisponiblesFiltro.get(i);
            librosArray[i] = libro.getTitulo() + " - " + libro.getAutor();  // Título y autor
        }
        
        // Mostrar un cuadro de diálogo para seleccionar un libro
        String libroSeleccionado = (String) JOptionPane.showInputDialog(this,
                "Selecciona el libro para prestar",
                "Préstamo de Libro",
                JOptionPane.QUESTION_MESSAGE,
                null,
                librosArray,
                librosArray[0]);
        
        if (libroSeleccionado != null) {
            // Buscar el libro en la lista filtrada de libros disponibles
            Libro libro = null;
            for (Libro l : librosDisponiblesFiltro) {
                if (libroSeleccionado.contains(l.getTitulo())) {
                    libro = l;  // Encontrar el libro seleccionado por el usuario
                    break;
                }
            }
            
            if (libro != null) {
                // Llamar al método para actualizar la disponibilidad del libro
                ServicioLibro.actualizarDisponibilidad(libro.getId(), false);  // Marcar como no disponible
                JOptionPane.showMessageDialog(this, "El libro '" + libro.getTitulo() + "' ha sido prestado exitosamente.");
            }
        }
    }

}
