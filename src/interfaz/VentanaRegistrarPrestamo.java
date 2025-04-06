package interfaz;

import modelo.ServicioLibro;

import javax.swing.*;
import java.awt.*;

public class VentanaRegistrarPrestamo extends JFrame {
    private int libroId;

    public VentanaRegistrarPrestamo(int libroId) {
        this.libroId = libroId;

        setTitle("Registrar Préstamo");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Mostrar detalles del libro que se seleccionó
        JLabel labelLibro = new JLabel("Se ha seleccionado el libro con ID: " + libroId);
        labelLibro.setFont(new Font("Arial", Font.PLAIN, 16));

        JPanel panel = new JPanel();
        panel.add(labelLibro);

        add(panel);
        setVisible(true);

        // Lógica para registrar el préstamo
        // Aquí, puedes realizar cualquier operación para procesar el préstamo
        // como actualizar la base de datos o llamar a un servicio
        // como el servicio para marcar el libro como no disponible.
    }
}
