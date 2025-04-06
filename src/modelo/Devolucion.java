package modelo;

import java.time.LocalDate;

public class Devolucion {

    private String idDevolucion;
    private String idPrestamo;
    private String idLibro;
    private String idCliente;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Devolucion(String idDevolucion, String idPrestamo, String idLibro, String idCliente, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        this.idDevolucion = idDevolucion;
        this.idPrestamo = idPrestamo;
        this.idLibro = idLibro;
        this.idCliente = idCliente;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public String getIdDevolucion() {
        return idDevolucion;
    }

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

    @Override
    public String toString() {
        return "Devolución { " +
               "idDevolucion='" + idDevolucion + '\'' +
               ", idPrestamo='" + idPrestamo + '\'' +
               ", idLibro='" + idLibro + '\'' +
               ", idCliente='" + idCliente + '\'' +
               ", fechaPrestamo=" + fechaPrestamo +
               ", fechaDevolucion=" + fechaDevolucion +
               " }";
    }
}
