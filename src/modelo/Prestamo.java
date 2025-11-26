package modelo;
import java.time.LocalDate;
public class Prestamo {

    private String idPrestamo;
    private String idLibro;
    private String idCliente;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean devuelto;

    // Constructor
    public Prestamo(String idPrestamo, String idLibro, String idCliente, LocalDate fechaPrestamo) {
        this.idPrestamo = idPrestamo;
        this.idLibro = idLibro;
        this.idCliente = idCliente;
        this.fechaPrestamo = fechaPrestamo;
        this.devuelto = false;
    }

    public void registrarDevolucion() {
        this.devuelto = true; 
        this.fechaDevolucion = LocalDate.now(); 
    }

    // Getters y Setters
    public String getIdPrestamo() {
        return idPrestamo;
    }

    public String getIdLibro() {
        return idLibro;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }
}
