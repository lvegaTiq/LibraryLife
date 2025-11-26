package interfaz;

import modelo.Usuario;
import modelo.ServicioUsuario;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class VentanaRegistrarUsuario extends JFrame {

    public VentanaRegistrarUsuario() {

        setTitle("Registrar Usuario");
        setSize(500, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        Color fondo = new Color(20, 25, 30);
        Color cardColor = new Color(40, 46, 60);
        Color azul = new Color(0, 126, 255);
        Color campoColor = new Color(55, 62, 80);

        getContentPane().setBackground(fondo);

        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBackground(cardColor);
        card.setBorder(new LineBorder(new Color(70, 80, 100), 1, true));
        card.setBounds(60, 40, 370, 380);
        add(card);

        JLabel titulo = new JLabel("Registrar Usuario", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 10, 370, 40);
        card.add(titulo);

        JTextField txtNombre = crearCampo(card, "Nombre completo:", 60, campoColor);
        JTextField txtCorreo = crearCampo(card, "Correo:", 120, campoColor);
        JTextField txtUsuario = crearCampo(card, "Usuario:", 180, campoColor);

        JPasswordField txtContrasena = new JPasswordField();
        colocarCampo(card, "Contraseña:", txtContrasena, 240, campoColor);

        JTextField txtTelefono = crearCampo(card, "Teléfono:", 300, campoColor);

        JButton btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBounds(55, 340, 120, 35);
        btnRegistrar.setBackground(azul);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setBorder(new LineBorder(new Color(120, 160, 255), 1, true));

        btnRegistrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistrar.setBackground(new Color(40, 140, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistrar.setBackground(azul);
            }
        });

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(200, 340, 120, 35);
        btnCancelar.setBackground(new Color(140, 45, 55));
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setBorder(new LineBorder(new Color(255, 80, 80), 1, true));

        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(new Color(170, 45, 55));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelar.setBackground(new Color(140, 45, 55));
            }
        });

        card.add(btnRegistrar);
        card.add(btnCancelar);

        btnRegistrar.addActionListener(e -> {

            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String usuario = txtUsuario.getText().trim();
            String contrasena = new String(txtContrasena.getPassword()).trim();
            String telefono = txtTelefono.getText().trim();

            if (nombre.isEmpty() || correo.isEmpty() || usuario.isEmpty() ||
                    contrasena.isEmpty() || telefono.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Todos los campos son obligatorios.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Usuario nuevo = new Usuario(nombre, correo, usuario, contrasena, telefono);

            ServicioUsuario.registrarUsuario(nuevo);

            JOptionPane.showMessageDialog(this,
                    "Usuario registrado exitosamente.\nRol asignado: " + nuevo.getRol(),
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

            dispose();
        });

        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }



    private JTextField crearCampo(JPanel panel, String texto, int y, Color campoColor) {
        JTextField campo = new JTextField();
        colocarCampo(panel, texto, campo, y, campoColor);
        return campo;
    }

    private void colocarCampo(JPanel panel, String texto, JComponent campo, int y, Color campoColor) {

        JLabel lbl = new JLabel(texto);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lbl.setBounds(30, y - 20, 200, 20);
        panel.add(lbl);

        campo.setBounds(30, y, 310, 32);
        campo.setBackground(campoColor);
        campo.setForeground(Color.WHITE);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setBorder(new LineBorder(new Color(90, 100, 130), 1, true));

        panel.add(campo);
    }
}
