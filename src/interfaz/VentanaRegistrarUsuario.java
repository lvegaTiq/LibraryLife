package interfaz;

import modelo.Usuario;
import modelo.ServicioUsuario;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistrarUsuario extends JFrame {

    public VentanaRegistrarUsuario() {

        setTitle("Registrar Usuario");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblNombre = new JLabel("Nombre completo:");
        JTextField txtNombre = new JTextField();

        JLabel lblCorreo = new JLabel("Correo:");
        JTextField txtCorreo = new JTextField();

        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField();

        JLabel lblContrasena = new JLabel("Contraseña:");
        JPasswordField txtContrasena = new JPasswordField();

        JLabel lblTelefono = new JLabel("Teléfono:");
        JTextField txtTelefono = new JTextField();

        panel.add(lblNombre);
        panel.add(txtNombre);

        panel.add(lblCorreo);
        panel.add(txtCorreo);

        panel.add(lblUsuario);
        panel.add(txtUsuario);

        panel.add(lblContrasena);
        panel.add(txtContrasena);

        panel.add(lblTelefono);
        panel.add(txtTelefono);

        JButton btnRegistrar = new JButton("Registrar");
        JButton btnCancelar = new JButton("Cancelar");

        panel.add(btnRegistrar);
        panel.add(btnCancelar);

        add(panel, BorderLayout.CENTER);

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
}
