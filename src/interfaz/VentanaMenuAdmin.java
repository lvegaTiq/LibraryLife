package interfaz;

import modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class VentanaMenuAdmin extends JFrame {

    private Usuario usuario;

    public VentanaMenuAdmin(Usuario usuarioLogeado) {
        this.usuario = usuarioLogeado;

        setTitle("Panel de Administración");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        Color fondo = new Color(20, 25, 35);
        Color sidebarColor = new Color(30, 36, 50);
        Color cardColor = new Color(40, 48, 65);
        Color azulBoton = new Color(0, 122, 255);

        getContentPane().setBackground(fondo);

        JPanel sidebar = new JPanel();
        sidebar.setBackground(sidebarColor);
        sidebar.setBounds(0, 0, 200, 600);
        sidebar.setLayout(new GridLayout(8, 1, 0, 10));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        sidebar.add(crearBotonMenu("📋 Usuarios"));
        sidebar.add(crearBotonMenu("➕ Crear Usuario"));
        sidebar.add(crearBotonMenu("📚 Libros"));
        sidebar.add(crearBotonMenu("➕ Registrar Libro"));
        sidebar.add(crearBotonMenu("📘 Libros Prestados"));

        JButton btnCerrar = crearBotonMenu("🚪 Cerrar Sesión");
        sidebar.add(btnCerrar);

        add(sidebar);

        JLabel lblWelcome = new JLabel("Bienvenido, " + usuario.getNombreCompleto());
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setBounds(230, 20, 500, 40);
        add(lblWelcome);

        JLabel lblFecha = new JLabel("Administrador — " + usuario.getCorreo());
        lblFecha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFecha.setForeground(new Color(180, 180, 180));
        lblFecha.setBounds(230, 55, 400, 30);
        add(lblFecha);

        JPanel cardUsuarios = crearCard("Gestionar Usuarios", "Ver, editar o inactivar usuarios.", cardColor);
        cardUsuarios.setBounds(230, 120, 300, 130);
        add(cardUsuarios);

        JPanel cardLibros = crearCard("Gestionar Libros", "Consulta y administración de libros.", cardColor);
        cardLibros.setBounds(550, 120, 300, 130);
        add(cardLibros);

        JPanel cardPrestamos = crearCard("Préstamos", "Monitoreo de libros prestados.", cardColor);
        cardPrestamos.setBounds(230, 280, 300, 130);
        add(cardPrestamos);

        JPanel cardRegistrar = crearCard("Registrar Libro", "Añadir nuevos libros al sistema.", cardColor);
        cardRegistrar.setBounds(550, 280, 300, 130);
        add(cardRegistrar);

        sidebar.getComponent(0).addMouseListener(click(e -> new VentanaConsultaInteractiva("Usuarios", usuario).setVisible(true)));
        sidebar.getComponent(1).addMouseListener(click(e -> new VentanaRegistrarUsuario().setVisible(true)));
        sidebar.getComponent(2).addMouseListener(click(e -> new VentanaConsultaInteractiva("Libros", usuario).setVisible(true)));
        sidebar.getComponent(3).addMouseListener(click(e -> new VentanaRegistroLibro().setVisible(true)));
        sidebar.getComponent(4).addMouseListener(click(e -> new VentanaPrestamoLibrosAdmin().setVisible(true)));

        btnCerrar.addActionListener(e -> {
            dispose();
            new VentanaInicio().setVisible(true);
        });

        setVisible(true);
    }


    private JButton crearBotonMenu(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(40, 48, 65));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        boton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { boton.setBackground(new Color(60, 70, 90)); }
            public void mouseExited(MouseEvent e) { boton.setBackground(new Color(40, 48, 65)); }
        });

        return boton;
    }

    private JPanel crearCard(String titulo, String desc, Color color) {
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBackground(color);
        card.setBorder(BorderFactory.createLineBorder(new Color(60, 70, 90), 1, true));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(15, 10, 400, 30);
        card.add(lblTitulo);

        JLabel lblDesc = new JLabel("<html>" + desc + "</html>");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDesc.setForeground(new Color(210, 210, 210));
        lblDesc.setBounds(15, 40, 260, 60);
        card.add(lblDesc);

        return card;
    }

    private MouseAdapter click(ActionListener e) {
        return new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) { e.actionPerformed(null); }
        };
    }
}
