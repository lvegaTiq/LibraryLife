package interfaz;

import modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaMenuAdmin extends JFrame {
    private Usuario usuario;

    public VentanaMenuAdmin(Usuario usuarioLogeado) {
        this.usuario = usuarioLogeado;
        setTitle("Menú Administrador");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Panel de Administración", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnVerUsuarios = new JButton("📋 Consultar Usuarios");
        JButton btnVerLibros = new JButton("📚 Consultar Libros");
        JButton btnRegistrarLibro = new JButton("➕ Registrar Libro");
        JButton btnLibrosPrestados = new JButton("📚 Libro Prestado");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        panelBotones.add(btnVerUsuarios);
        panelBotones.add(btnVerLibros);
        panelBotones.add(btnRegistrarLibro);
        panelBotones.add(btnLibrosPrestados);
        panelBotones.add(btnCerrarSesion);

        add(panelBotones, BorderLayout.CENTER);

        btnVerUsuarios.addActionListener(e -> new VentanaConsultaInteractiva("Usuarios", usuarioLogeado).setVisible(true));
        btnVerLibros.addActionListener(e -> new VentanaConsultaInteractiva("Libros", usuarioLogeado).setVisible(true));
        btnRegistrarLibro.addActionListener(e -> new VentanaRegistroLibro().setVisible(true));
        btnLibrosPrestados.addActionListener(e -> new VentanaPrestamoLibrosAdmin().setVisible(true));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

    }
    private void cerrarSesion() {
        dispose();
        
        new VentanaInicio().setVisible(true);
    }
}
