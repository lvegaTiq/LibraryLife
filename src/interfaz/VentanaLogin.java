package interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.nio.file.*;
import modelo.Usuario;
import org.json.simple.*;
import org.json.simple.parser.*;

public class VentanaLogin extends JFrame {

    public VentanaLogin() {

        setTitle("Login");
        setSize(500, 420);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel fondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(40, 50, 70),
                        0, getHeight(), new Color(15, 20, 35)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        fondo.setLayout(null);
        add(fondo);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);
        loginPanel.setBounds(110, 60, 280, 280);

        loginPanel.setBackground(new Color(0, 0, 0));
        loginPanel.setBorder(
                new LineBorder(new Color(255, 255, 255, 80), 2, true)
        );

        fondo.add(loginPanel);

        JLabel titulo = new JLabel("Login", SwingConstants.CENTER);
        titulo.setBounds(0, 10, 280, 40);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.white);
        loginPanel.add(titulo);

        JTextField usuarioField = crearCampoTexto("Username");
        usuarioField.setBounds(35, 70, 210, 40);
        loginPanel.add(usuarioField);

        JPasswordField passField = crearCampoPassword("Password");
        passField.setBounds(35, 125, 210, 40);
        loginPanel.add(passField);

        JButton btnLogin = new JButton("Iniciar sesión");
        btnLogin.setBounds(35, 190, 210, 40);
        btnLogin.setFocusPainted(false);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnLogin.setBackground(new Color(70, 130, 250));
        btnLogin.setBorder(new LineBorder(new Color(90,140,255), 2, true));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLogin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnLogin.setBackground(new Color(90,150,255)); }
            public void mouseExited(MouseEvent e) { btnLogin.setBackground(new Color(70,130,250)); }
        });

        loginPanel.add(btnLogin);

        btnLogin.addActionListener(e -> {
            String usuario = usuarioField.getText().trim();
            String contrasena = new String(passField.getPassword()).trim();

            try {
                String contenido = Files.readString(Paths.get("usuarios.json"));
                JSONParser parser = new JSONParser();
                JSONArray usuarios = (JSONArray) parser.parse(contenido);
            
                for (Object obj : usuarios) {
                    JSONObject user = (JSONObject) obj;

                    if (user.get("usuario").equals(usuario) &&
                        user.get("contrasena").equals(contrasena)) {

                        String rol = (String) user.get("rol");

                        Usuario usuarioLogeado = new Usuario(
                                (String) user.get("nombreCompleto"),
                                (String) user.get("correo"),
                                (String) user.get("usuario"),
                                (String) user.get("contrasena"),
                                (String) user.get("telefono"),
                                rol
                        );

                        JOptionPane.showMessageDialog(this, "Bienvenido " + usuario);

                        dispose();

                        if (rol.equalsIgnoreCase("administrador")) 
                            new VentanaMenuAdmin(usuarioLogeado).setVisible(true);
                        else
                            new VentanaMenuCliente(usuarioLogeado).setVisible(true);

                        return;
                    }
                }

                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos",
                        "Error", JOptionPane.ERROR_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en login: " + ex.getMessage());
            }
        });
    }



    private JTextField crearCampoTexto(String placeholder) {
        JTextField campo = new JTextField(placeholder);
        campo.setForeground(Color.GRAY);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setOpaque(false);
        campo.setBorder(new MatteBorder(0, 0, 2, 0, new Color(200,200,200)));

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

        campo.setCaretColor(Color.WHITE);
        return campo;
    }

    private JPasswordField crearCampoPassword(String placeholder) {
        JPasswordField campo = new JPasswordField(placeholder);
        campo.setForeground(Color.GRAY);
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campo.setOpaque(false);
        campo.setBorder(new MatteBorder(0, 0, 2, 0, new Color(200,200,200)));

        campo.setEchoChar((char) 0);

        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                String text = new String(campo.getPassword());
                if (text.equals(placeholder)) {
                    campo.setText("");
                    campo.setForeground(Color.WHITE);
                    campo.setEchoChar('•');
                }
            }

            public void focusLost(FocusEvent e) {
                if (campo.getPassword().length == 0) {
                    campo.setText(placeholder);
                    campo.setForeground(Color.GRAY);
                    campo.setEchoChar((char) 0);
                }
            }
        });

        campo.setCaretColor(Color.WHITE);
        return campo;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaLogin().setVisible(true));
    }
}
