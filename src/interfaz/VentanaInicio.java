package interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaInicio extends JFrame {

    // Clase interna para el botón con transición y bordes redondeados
    class BotonAnimado extends JButton {
        private Color colorInicial = new Color(178, 210, 197);
        private Color colorHover = new Color(140, 180, 160);
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
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Border radius
            super.paintComponent(g);
            g2.dispose();
        }
    }

    public VentanaInicio() {
        setTitle("Bienvenido al sistema de biblioteca");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(245, 245, 245));
        panel.setBounds(0, 0, 900, 700);

        JLabel titulo = new JLabel("Sistema de Biblioteca");
        titulo.setBounds(50, 30, 400, 30);
        titulo.setFont(new Font("Calibri", Font.BOLD, 24));
        panel.add(titulo);

        // Usamos los nuevos botones animados
        BotonAnimado btnLogin = new BotonAnimado("Iniciar Sesión");
        btnLogin.setBounds(500, 20, 150, 40);
        panel.add(btnLogin);

        BotonAnimado btnRegister = new BotonAnimado("Registrarse");
        btnRegister.setBounds(655, 20, 150, 40);
        panel.add(btnRegister);

        btnLogin.addActionListener(e -> {
            new VentanaLogin().setVisible(true);  
            dispose();
        });
        btnRegister.addActionListener(e -> {
            VentanaRegister registro = new VentanaRegister();
            registro.setVisible(true);
        });
        
        add(panel);
    
        ImageIcon icon = new ImageIcon(getClass().getResource("/interfaz/niños.jpg"));
        Image img = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JLabel imagenLabel = new  JLabel(scaledIcon);
        imagenLabel.setBounds(530, 220, 300, 200);

        JLabel lblInfo = new JLabel("Sobre la biblioteca");
        lblInfo.setBounds(50, 50, 300, 305);
        lblInfo.setFont(new Font("Calibri", Font.BOLD, 20));
        panel.add(lblInfo);

        JPanel contenedorTexto = new JPanel();
        contenedorTexto.setLayout(new BorderLayout());
        contenedorTexto.setBounds(50, 230, 450, 200);
        contenedorTexto.setBackground(new Color(250, 250, 250));
        contenedorTexto.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea txtInfo = new JTextArea(
            "Biblioteca \"Volviendo a Leer\"\n\n"+
            "Fundada en 1985 con el sueño de darle la oportunidad a personas que necesitan los recursos y les interese explorar los recursos que necesiten o les guste. Comenzamos con 300 libros donados y hoy en dia contamos con mas de 20,000 titulos. \n\n"+
            "Se ofrecen salas digitales en donde se prestan equipos para que se puedan hacer consultas sobre algun tema de interes o algo que desee buscar.\n\n"
        );
        txtInfo.setLineWrap(true);
        txtInfo.setWrapStyleWord(true);
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        txtInfo.setBounds(50, 230, 450, 200);
        contenedorTexto.add(txtInfo, BorderLayout.CENTER);
        panel.add(imagenLabel);
        panel.add(contenedorTexto);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaInicio ventana = new VentanaInicio();
            ventana.setVisible(true);
        });
    }
}
