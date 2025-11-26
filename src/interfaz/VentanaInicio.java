package interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VentanaInicio extends JFrame {

    class BotonAnimado extends JButton {
        private Color baseColor = new Color(0, 120, 215);
        private Color hoverColor = new Color(30, 150, 245);
        private Timer timer;

        public BotonAnimado(String text) {
            super(text);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFont(new Font("Arial", Font.BOLD, 15));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(150, 45));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    animar(baseColor, hoverColor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    animar(hoverColor, baseColor);
                }
            });
        }

        private void animar(Color from, Color to) {
            if (timer != null && timer.isRunning()) timer.stop();

            timer = new Timer(20, null);
            timer.addActionListener(new ActionListener() {
                int step = 0;
                final int steps = 10;

                @Override
                public void actionPerformed(ActionEvent e) {
                    float ratio = (float) step / steps;
                    int r = (int) (from.getRed() + ratio * (to.getRed() - from.getRed()));
                    int g = (int) (from.getGreen() + ratio * (to.getGreen() - from.getGreen()));
                    int b = (int) (from.getBlue() + ratio * (to.getBlue() - from.getBlue()));

                    setBackground(new Color(r, g, b));
                    repaint();

                    step++;
                    if (step > steps) timer.stop();
                }
            });

            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    public VentanaInicio() {

        setTitle("Sistema de Biblioteca");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel barraSuperior = new JPanel(new BorderLayout());
        barraSuperior.setBackground(new Color(10, 73, 123));
        barraSuperior.setPreferredSize(new Dimension(1000, 70));

        JLabel tituloPrincipal = new JLabel("Biblioteca Volviendo a Leer", JLabel.LEFT);
        tituloPrincipal.setForeground(Color.WHITE);
        tituloPrincipal.setFont(new Font("SansSerif", Font.BOLD, 26));
        tituloPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        barraSuperior.add(tituloPrincipal, BorderLayout.WEST);

        JPanel panelBotonesTop = new JPanel();
        panelBotonesTop.setOpaque(false);

        BotonAnimado btnLogin = new BotonAnimado("Iniciar Sesión");
        BotonAnimado btnRegister = new BotonAnimado("Registrarse");

        panelBotonesTop.add(btnLogin);
        panelBotonesTop.add(btnRegister);
        barraSuperior.add(panelBotonesTop, BorderLayout.EAST);

        add(barraSuperior, BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(null);
        panelCentral.setBackground(new Color(240, 245, 250));
        add(panelCentral, BorderLayout.CENTER);

        JLabel titulo = new JLabel("Descubre nuestra biblioteca digital");
        titulo.setBounds(50, 80, 600, 50);
        titulo.setFont(new Font("Calibri", Font.BOLD, 38));
        panelCentral.add(titulo);

        JPanel contenedorTexto = new JPanel();
        contenedorTexto.setLayout(new BorderLayout());
        contenedorTexto.setBounds(50, 230, 450, 230);

        contenedorTexto.setBackground(new Color(235, 240, 245));

        contenedorTexto.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextArea txtInfo = new JTextArea(
            "Biblioteca \"Volviendo a Leer\"\n" +
            "Fundada en 1985 con el sueño de darle la oportunidad a personas que necesitan\n" +
            "los recursos y les interese explorar lo que requieran o disfruten. Comenzamos\n" +
            "con 300 libros donados y hoy en día contamos con más de 20,000 títulos.\n" +
            "Se ofrecen salas digitales donde se prestan equipos para realizar consultas\n" +
            "sobre algún tema de interés, tareas o investigación personal.\n"
        );

        txtInfo.setLineWrap(true);
        txtInfo.setWrapStyleWord(true);
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Arial", Font.PLAIN, 15));
        txtInfo.setForeground(new Color(30, 30, 30));
        txtInfo.setOpaque(false);

        contenedorTexto.add(txtInfo, BorderLayout.CENTER);
        
        panelCentral.add(contenedorTexto);


        ImageIcon icon = new ImageIcon(getClass().getResource("/interfaz/niños.jpg"));
        Image img = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        JLabel imagenLabel = new JLabel(new ImageIcon(img));
        imagenLabel.setBounds(550, 180, 400, 300);
        panelCentral.add(imagenLabel);

        btnLogin.addActionListener(e -> {
            new VentanaLogin().setVisible(true);
            dispose();
        });

        btnRegister.addActionListener(e -> {
            new VentanaRegister().setVisible(true);
        });

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaInicio().setVisible(true);
        });
    }
}
