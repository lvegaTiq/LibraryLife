package interfaz;

import modelo.ArbolUser;
import modelo.Usuario;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.List;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class VentanaConsultaInteractiva extends JFrame {

    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JSONArray datosJSON;
    private String tipoArchivo;

    private JTextArea areaArbol;

    public VentanaConsultaInteractiva(String tipo, Usuario usuarioLogeado) {

        setTitle("Gestión de " + tipo);
        setSize(1200, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        Color fondo = new Color(18, 22, 33);
        Color sidebarColor = new Color(28, 32, 44);
        Color cardColor = new Color(40, 46, 60);
        Color tablaHeader = new Color(65, 73, 95);
        Color tablaRow = new Color(50, 58, 75);
        Color azul = new Color(0, 122, 255);

        getContentPane().setBackground(fondo);

        JPanel sidebar = new JPanel();
        sidebar.setBackground(sidebarColor);
        sidebar.setLayout(null);
        sidebar.setBounds(0, 0, 80, 650);

        JLabel icono1 = crearIcono("📘", 25, 50);
        JLabel icono2 = crearIcono("👤", 25, 120);
        JLabel icono3 = crearIcono("⚙️", 25, 190);
        JLabel icono4 = crearIcono("📁", 25, 260);

        sidebar.add(icono1);
        sidebar.add(icono2);
        sidebar.add(icono3);
        sidebar.add(icono4);

        add(sidebar);

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(cardColor);
        card.setBounds(100, 40, 750, 520);
        card.setBorder(new LineBorder(new Color(70, 80, 100), 1, true));
        add(card);

        tipoArchivo = tipo.equals("Libros") ? "libros.json" : "usuarios.json";
        cargarDatos();

        tabla.setRowHeight(28);
        tabla.setBackground(tablaRow);
        tabla.setForeground(Color.WHITE);
        tabla.setSelectionBackground(new Color(70, 80, 100));

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(tablaHeader);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        card.add(scroll, BorderLayout.CENTER);

        JPanel panelArbol = new JPanel();
        panelArbol.setLayout(new BorderLayout());
        panelArbol.setBackground(cardColor);
        panelArbol.setBounds(870, 40, 300, 520);
        panelArbol.setBorder(new LineBorder(new Color(70, 80, 100), 1, true));
        add(panelArbol);

        JLabel lblArbol = new JLabel("Árbol Usuarios", SwingConstants.CENTER);
        lblArbol.setForeground(Color.WHITE);
        lblArbol.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panelArbol.add(lblArbol, BorderLayout.NORTH);

        areaArbol = new JTextArea();
        areaArbol.setEditable(false);
        areaArbol.setBackground(new Color(30, 34, 50));
        areaArbol.setForeground(Color.WHITE);
        areaArbol.setFont(new Font("Consolas", Font.PLAIN, 14));
        panelArbol.add(new JScrollPane(areaArbol), BorderLayout.CENTER);

        cargarArbol(tipoArchivo);

        JButton btnActualizar = crearBoton("Actualizar", 100, 580, azul);
        JButton btnInactivar = crearBoton("Inactivar", 260, 580, new Color(200, 50, 50));
        JButton btnRegresar = crearBoton("Regresar", 420, 580, new Color(100, 100, 100));

        add(btnActualizar);
        add(btnInactivar);
        add(btnRegresar);

        btnActualizar.addActionListener(e -> actualizarSeleccionado());
        btnInactivar.addActionListener(e -> inactivarSeleccionado());
        btnRegresar.addActionListener(e -> {
            new VentanaMenuAdmin(usuarioLogeado).setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private JLabel crearIcono(String emoji, int x, int y) {
        JLabel lbl = new JLabel(emoji, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        lbl.setBounds(x, y, 30, 30);
        lbl.setForeground(Color.WHITE);
        return lbl;
    }

    private JButton crearBoton(String texto, int x, int y, Color color) {
        JButton btn = new JButton(texto);
        btn.setBounds(x, y, 130, 35);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(new LineBorder(color.brighter(), 1, true));
        return btn;
    }

    private void cargarDatos() {

        modeloTabla = new DefaultTableModel();

        if (tipoArchivo.equals("libros.json")) {
            modeloTabla.setColumnIdentifiers(new String[]{"Título", "Autor", "Categoría", "Disponible"});
        } else {
            modeloTabla.setColumnIdentifiers(new String[]{"Nombre", "Email", "Usuario", "Teléfono"});
        }

        try {
            Path path = Paths.get(tipoArchivo);
            if (!Files.exists(path)) Files.writeString(path, "[]");

            JSONParser parser = new JSONParser();
            datosJSON = (JSONArray) parser.parse(Files.readString(path));

            for (Object obj : datosJSON) {
                JSONObject item = (JSONObject) obj;

                if (tipoArchivo.equals("libros.json")) {
                    modeloTabla.addRow(new Object[]{
                            item.get("titulo"),
                            item.get("autor"),
                            item.get("categoria"),
                            item.get("disponible")
                    });
                } else {
                    modeloTabla.addRow(new Object[]{
                            item.get("nombreCompleto"),
                            item.get("correo"),
                            item.get("usuario"),
                            item.get("telefono")
                    });
                }
            }

            tabla = new JTable(modeloTabla);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage());
        }
    }

    private void cargarArbol(String tipoArchivo) {

        if (!tipoArchivo.equals("usuarios.json")) {
            areaArbol.setText("Vista de árbol solo disponible para usuarios.");
            return;
        }

        ArbolUser arbol = new ArbolUser();

        for (Object obj : datosJSON) {
            JSONObject item = (JSONObject) obj;

            String nombre = (String) item.get("nombreCompleto");
            String correo = (String) item.get("correo");
            String user = (String) item.get("usuario");
            String tel = (String) item.get("telefono");

            Usuario u = new Usuario(nombre, correo, user, "", tel);
            arbol.insertar(u);
        }

        areaArbol.setText("");
        arbol.inOrden(areaArbol);
    }

    private void actualizarSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila.");
            return;
        }

        String v1 = JOptionPane.showInputDialog("Nuevo valor:", tabla.getValueAt(row, 0));
        if (v1 != null) tabla.setValueAt(v1, row, 0);
    }

    private void inactivarSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila.");
            return;
        }

        tabla.setValueAt(false, row, 3);
    }
}
