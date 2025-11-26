package interfaz;

import javax.swing.*;
import java.awt.*;
import modelo.Usuario;
import modelo.ServicioUsuario;

public class VentanaRegister extends JFrame {

    public VentanaRegister() {
        setTitle("Registro de Usuario");
        setSize(450, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        JLabel lblTitulo = new JLabel("Formulario de Registro", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(60, 60, 60));
        panel.add(lblTitulo, gbc);

        JTextField txtNombre = new JTextField();
        JTextField txtCorreo = new JTextField();
        JTextField txtUsuario = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JTextField txtTelefono = new JTextField();
        JButton btnRegistrar = new JButton("Registrar");

        gbc.gridy = 1; addCampo(panel, gbc, "Nombre completo:", txtNombre);
        gbc.gridy = 2; addCampo(panel, gbc, "Correo:", txtCorreo);
        gbc.gridy = 3; addCampo(panel, gbc, "Usuario:", txtUsuario);
        gbc.gridy = 4; addCampo(panel, gbc, "Contraseña:", txtPassword);
        gbc.gridy = 5; addCampo(panel, gbc, "Teléfono:", txtTelefono);

        gbc.gridy = 6;
        btnRegistrar.setBackground(new Color(33, 150, 243));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(btnRegistrar, gbc);

        add(panel);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String usuario = txtUsuario.getText().trim();
            String contrasena = new String(txtPassword.getPassword());
            String telefono = txtTelefono.getText().trim();

            if (nombre.isEmpty() || correo.isEmpty() || usuario.isEmpty() || contrasena.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor completa todos los campos");
                return;
            }

            if (!correo.contains("@")) {
                JOptionPane.showMessageDialog(this, "Por favor ingresa un correo válido.");
                return;
            }

            Usuario nuevoUsuario = new Usuario(nombre, correo, usuario, contrasena, telefono);

            if (ServicioUsuario.login(usuario, contrasena) != null) {
                JOptionPane.showMessageDialog(this, "El usuario ya existe.");
            } else {
                ServicioUsuario.registrarUsuario(nuevoUsuario);
                JOptionPane.showMessageDialog(this, "Registro exitoso. Ahora puedes iniciar sesión.");
                dispose(); 
            }
        });
    }

    private void addCampo(JPanel panel, GridBagConstraints gbc, String label, JComponent campo) {
        JPanel contenedor = new JPanel(new BorderLayout(5, 5));
        contenedor.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 14));
        campo.setPreferredSize(new Dimension(200, 30));
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
        contenedor.add(lbl, BorderLayout.NORTH);
        contenedor.add(campo, BorderLayout.CENTER);
        panel.add(contenedor, gbc);
    }
}
