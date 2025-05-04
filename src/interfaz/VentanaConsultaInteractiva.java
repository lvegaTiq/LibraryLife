package interfaz;

import modelo.ArbolUser;
import modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.*;
import org.json.simple.parser.*;

public class VentanaConsultaInteractiva extends JFrame {
    private Usuario usuario;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JSONArray datosJSON;
    private String tipoArchivo;

    // Agregar JTextArea para mostrar el árbol
    private JTextArea areaArbol;

    public VentanaConsultaInteractiva(String tipo, Usuario usuarioLogeado) {
        setTitle("Gestión de " + tipo);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        tipoArchivo = tipo.equals("Libros") ? "libros.json" : "usuarios.json";

        cargarDatos();

        JPanel panelBotones = new JPanel();
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnInactivar = new JButton("Inactivar");
        JButton btnRegresar = new JButton("Regresar");

        panelBotones.add(btnRegresar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnInactivar);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Crear el área de texto para mostrar el árbol
        areaArbol = new JTextArea(10, 40);
        areaArbol.setEditable(false);
        JScrollPane scrollArbol = new JScrollPane(areaArbol);
        add(scrollArbol, BorderLayout.EAST); // Agregar el panel al lado derecho

        // Crear instancia del árbol
        ArbolUser arbolUser = new ArbolUser();

        // Insertar usuarios desde el JSON en el árbol
        for (Object obj : datosJSON) {
            JSONObject item = (JSONObject) obj;
            String nombre = (String) item.get("nombreCompleto");
            String email = (String) item.get("correo");
            String usuarioNombre = (String) item.get("usuario");
            String telefono = (String) item.get("telefono");
            String contrasena = ""; // Asigna si lo tienes en el JSON

            Usuario usuario = new Usuario(nombre, email, usuarioNombre, telefono, contrasena);
            arbolUser.insertar(usuario);
        }

        // Mostrar el árbol en orden en el área
        areaArbol.setText(""); // Limpiar antes
        arbolUser.inOrden(areaArbol); // Mostrar árbol en orden

        btnActualizar.addActionListener(e -> actualizarSeleccionado());
        btnInactivar.addActionListener(e -> inactivarSeleccionado());
        btnRegresar.addActionListener(e -> {
            VentanaMenuAdmin ventanaMenuAdmin = new VentanaMenuAdmin(usuarioLogeado);
            ventanaMenuAdmin.setVisible(true);
            dispose(); // Cierra la ventana actual si es necesario
        });
    }

    private void cargarDatos() {
        modeloTabla = new DefaultTableModel();
        
        if (tipoArchivo.equals("libros.json")) {
            modeloTabla.setColumnIdentifiers(new String[]{"Título", "Autor", "Categoría", "Disponible"});
        } else if (tipoArchivo.equals("usuarios.json")) {
            modeloTabla.setColumnIdentifiers(new String[]{"Nombre", "Email", "usuario", "telefono"});
        }
    
        try {
            Path path = Paths.get(tipoArchivo);
            // Verificar si el archivo existe, si no, crearlo vacío
            if (!Files.exists(path)) {
                Files.writeString(path, "[]"); // Crear un archivo JSON vacío si no existe
            }
    
            String contenido = Files.readString(path);
            JSONParser parser = new JSONParser();
            datosJSON = (JSONArray) parser.parse(contenido);
    
            for (Object obj : datosJSON) {
                JSONObject item = (JSONObject) obj;
                if (tipoArchivo.equals("libros.json")) {
                    String titulo = (String) item.get("titulo");
                    String autor = (String) item.get("autor");
                    String categoria = (String) item.get("categoria");
                    boolean disponible = (boolean) item.get("disponible");
                    modeloTabla.addRow(new Object[]{titulo, autor, categoria, disponible});
                } else if (tipoArchivo.equals("usuarios.json")) {
                    String nombre = (String) item.get("nombreCompleto");
                    String email = (String) item.get("correo");
                    String usuario = (String) item.get("usuario");
                    String telefono = (String) item.get("telefono");
                    modeloTabla.addRow(new Object[]{nombre, email, usuario, telefono});
                }
            }
    
            tabla = new JTable(modeloTabla);
    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar: " + e.getMessage());
        }
    }
    

    private void guardarCambios() {
        try {
            JSONArray nuevoArray = new JSONArray();
    
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                JSONObject obj = new JSONObject();
                if (tipoArchivo.equals("libros.json")) {
                    obj.put("titulo", modeloTabla.getValueAt(i, 0));
                    obj.put("autor", modeloTabla.getValueAt(i, 1));
                    obj.put("categoria", modeloTabla.getValueAt(i, 2));
                    obj.put("disponible", modeloTabla.getValueAt(i, 3));
                } else if (tipoArchivo.equals("usuarios.json")) {
                    obj.put("nombreCompleto", modeloTabla.getValueAt(i, 0)); // Corregido
                    obj.put("correo", modeloTabla.getValueAt(i, 1)); // Corregido
                    obj.put("usuario", modeloTabla.getValueAt(i, 2)); // Corregido
                    obj.put("telefono", modeloTabla.getValueAt(i, 3)); // Corregido
                }
                nuevoArray.add(obj);
            }
    
            Files.writeString(Paths.get(tipoArchivo), nuevoArray.toJSONString());
            cargarDatos(); // Recargar los datos después de guardar
    
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar cambios: " + e.getMessage());
        }
    }
    

    private void actualizarSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para actualizar.");
            return;
        }

        String campo1 = tipoArchivo.equals("libros.json") ? "Título" : "Nombre";
        String campo2 = tipoArchivo.equals("libros.json") ? "Autor" : "Email";
        String campo3 = tipoArchivo.equals("libros.json") ? "Categoría" : "Rol";
        String campo4 = tipoArchivo.equals("libros.json") ? "Disponible" : "Activo";

        String valor1 = JOptionPane.showInputDialog("Actualizar " + campo1 + ":", tabla.getValueAt(row, 0));
        String valor2 = JOptionPane.showInputDialog("Actualizar " + campo2 + ":", tabla.getValueAt(row, 1));
        String valor3 = JOptionPane.showInputDialog("Actualizar " + campo3 + ":", tabla.getValueAt(row, 2));
        String valor4 = JOptionPane.showInputDialog("Actualizar " + campo4 + ":", tabla.getValueAt(row, 3));

        if (valor1 != null && valor2 != null && valor3 != null && valor4 != null) {
            modeloTabla.setValueAt(valor1, row, 0);
            modeloTabla.setValueAt(valor2, row, 1);
            modeloTabla.setValueAt(valor3, row, 2);
            modeloTabla.setValueAt(valor4, row, 3);
            guardarCambios();
        }
    }

    private void inactivarSeleccionado() {
        int row = tabla.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para inactivar.");
            return;
        }

        modeloTabla.setValueAt(false, row, 3); // Cambiar el valor de "Disponible" o "Activo" a false
        guardarCambios();
    }
}
