package interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modelo.Usuario;
import modelo.ServicioUsuario;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;



public class VentanaRegister extends JFrame {

    public VentanaRegister() {
        setTitle("Registro de Usuario");
        setSize(500, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel fondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(45, 55, 75),
                        0, getHeight(), new Color(15, 20, 35)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        fondo.setLayout(null);
        add(fondo);

        JPanel panel = new JPanel(null);
        panel.setBounds(100, 60, 300, 460);
        panel.setBackground(new Color(0,0,0));
        panel.setBorder(new LineBorder(new Color(255, 255, 255, 80), 2, true));
        fondo.add(panel);

        JLabel lblTitulo = new JLabel("Registro", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setBounds(0, 20, 300, 40);
        panel.add(lblTitulo);

        JTextField txtNombre = crearCampoTexto("Nombre completo");
        txtNombre.setBounds(40, 80, 220, 40);
        panel.add(txtNombre);

        JTextField txtCorreo = crearCampoTexto("Correo");
        txtCorreo.setBounds(40, 140, 220, 40);
        panel.add(txtCorreo);

        JTextField txtUsuario = crearCampoTexto("Usuario");
        txtUsuario.setBounds(40, 200, 220, 40);
        panel.add(txtUsuario);

        JPasswordField txtPassword = crearCampoPassword("Contraseña");
        txtPassword.setBounds(40, 260, 220, 40);
        panel.add(txtPassword);

        JTextField txtTelefono = crearCampoTexto("Teléfono");
        txtTelefono.setBounds(40, 320, 220, 40);
        panel.add(txtTelefono);

        JButton btnRegistrar = new JButton("Crear cuenta");
        btnRegistrar.setBounds(40, 390, 220, 40);
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setBackground(new Color(70, 130, 250));
        btnRegistrar.setBorder(new LineBorder(new Color(90,140,255), 2, true));
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRegistrar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnRegistrar.setBackground(new Color(90,150,255));
            }
            public void mouseExited(MouseEvent e) {
                btnRegistrar.setBackground(new Color(70,130,250));
            }
        });

        panel.add(btnRegistrar);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            String correo = txtCorreo.getText().trim();
            String usuario = txtUsuario.getText().trim();
            String contrasena = String.valueOf(txtPassword.getPassword());
            String telefono = txtTelefono.getText().trim();

            if (nombre.isEmpty() || correo.isEmpty() || usuario.isEmpty() ||
                contrasena.isEmpty() || telefono.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa todos los campos.");
                return;
            }

            if (!correo.contains("@")) {
                JOptionPane.showMessageDialog(this, "Correo inválido.");
                return;
            }

            Usuario nuevo = new Usuario(nombre, correo, usuario, contrasena, telefono);

            if (ServicioUsuario.login(usuario, contrasena) != null) {
                JOptionPane.showMessageDialog(this, "El usuario ya existe.");
                return;
            }

            ServicioUsuario.registrarUsuario(nuevo);
            JOptionPane.showMessageDialog(this, "Registro exitoso. Ahora puedes iniciar sesión.");
            dispose();
        });
    }

    private JTextField crearCampoTexto(String placeholder) {
        JTextField campo = new JTextField(placeholder);
        campo.setForeground(Color.GRAY);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setOpaque(false);
        campo.setBorder(new MatteBorder(0, 0, 2, 0, new Color(200,200,200)));
        campo.setCaretColor(Color.WHITE);

        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent e) {
                if (campo.getText().isEmpty()) {
                    campo.setText(placeholder);
                    campo.setForeground(Color.GRAY);
                }
            }
        });

        return campo;
    }

    private JPasswordField crearCampoPassword(String placeholder) {
        JPasswordField campo = new JPasswordField(placeholder);
        campo.setForeground(Color.GRAY);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setOpaque(false);
        campo.setBorder(new MatteBorder(0, 0, 2, 0, new Color(200,200,200)));
        campo.setEchoChar((char) 0);
        campo.setCaretColor(Color.WHITE);

        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (new String(campo.getPassword()).equals(placeholder)) {
                    campo.setText("");
                    campo.setEchoChar('•');
                    campo.setForeground(Color.WHITE);
                }
            }
            public void focusLost(FocusEvent e) {
                if (campo.getPassword().length == 0) {
                    campo.setText(placeholder);
                    campo.setEchoChar((char) 0);
                    campo.setForeground(Color.GRAY);
                }
            }
        });

        return campo;
    }
}
