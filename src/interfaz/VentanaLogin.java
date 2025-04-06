package interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.json.simple.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import modelo.Usuario;

import java.io.*;
import java.nio.file.*;
import java.io.IOException;
import org.json.*;


public class VentanaLogin extends JFrame {

    class BotonAnimado extends JButton {
        private Color colorInicial = new Color(170, 206, 217);
        private Color colorHover = new Color(153, 200, 215);
        private Timer timer;

        public BotonAnimado(String text) {
            super(text);
            setBackground(colorInicial);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFont(new Font("Arial", Font.BOLD, 14));

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    animarColor(colorInicial, colorHover);
                }

                public void mouseExited(MouseEvent e) {
                    animarColor(colorHover, colorInicial);
                }
            });
        }

        private void animarColor(Color from, Color to) {
            if (timer != null && timer.isRunning()) timer.stop();

            final int steps = 10;
            final int delay = 20;

            timer = new Timer(delay, null);
            timer.addActionListener(new ActionListener() {
                int step = 0;

                public void actionPerformed(ActionEvent e) {
                    float ratio = (float) step / steps;
                    int r = (int) (from.getRed() + ratio * (to.getRed() - from.getRed()));
                    int g = (int) (from.getGreen() + ratio * (to.getGreen() - from.getGreen()));
                    int b = (int) (from.getBlue() + ratio * (to.getBlue() - from.getBlue()));
                    setBackground(new Color(r, g, b));
                    repaint();

                    step++;
                    if (step > steps) {
                        timer.stop();
                    }
                }
            });

            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    public VentanaLogin() {
        setTitle("Login");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(231, 233, 232));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel titulo = new JLabel("Iniciar Sesión");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(0, 0, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        JTextField usuarioField = new JTextField(15);
        panel.add(usuarioField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        JPasswordField passField = new JPasswordField(15);
        panel.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        BotonAnimado btnLogin = new BotonAnimado("Iniciar sesión");
        panel.add(btnLogin, gbc);

        btnLogin.addActionListener(e -> {
            String usuario = usuarioField.getText();
            String contrasena = new String(passField.getPassword());
                
            try {
                String contenido = new String(Files.readAllBytes(Paths.get("usuarios.json")));
                JSONParser parser = new JSONParser();
                JSONArray usuarios = (JSONArray) parser.parse(contenido);
            
                boolean encontrado = false;
            
                for (Object obj : usuarios) {
                    JSONObject user = (JSONObject) obj;
                
                    String userName = (String) user.get("usuario");
                    String password = (String) user.get("contrasena");
                    String rol = (String) user.get("rol");
                    String nombreCompleto = (String) user.get("nombreCompleto");
                    String correo = (String) user.get("correo");
                    String telefono = (String) user.get("telefono");
                
                    if (userName.equals(usuario) && password.equals(contrasena)) {
                        encontrado = true;
                        JOptionPane.showMessageDialog(this, "Bienvenido, " + usuario);
                        dispose();
                    
                        // Crear el objeto Usuario
                        Usuario usuarioLogeado = new Usuario(nombreCompleto, correo, userName, telefono, rol);
                    
                        if (rol.equalsIgnoreCase("administrador")) {
                            new VentanaMenuAdmin(usuarioLogeado).setVisible(true);
                        } else if (rol.equalsIgnoreCase("cliente")) {
                            new VentanaMenuCliente(usuarioLogeado).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(this, "Rol desconocido");
                        }
                    
                        break;
                    }
                }
            
                if (!encontrado) {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            
            } catch (IOException | ParseException ex) {
                JOptionPane.showMessageDialog(this, "Error leyendo archivo JSON: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        


        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaLogin().setVisible(true));
    }
}
