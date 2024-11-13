package co.uniquindio.edu.piii.Model.sockets.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Tanqueo implements Serializable {
    private String clienteId;
    private String tipoVehiculo;
    private double cantidadGalones;
    private LocalDateTime fechaHora;

    public Tanqueo(String clienteId, String tipoVehiculo, double cantidadGalones, LocalDateTime fechaHora) {
        this.clienteId = clienteId;
        this.tipoVehiculo = tipoVehiculo;
        this.cantidadGalones = cantidadGalones;
        this.fechaHora = fechaHora;
    }

    // Getters y setters
    @Override
    public String toString() {
        return "ID Cliente: " + clienteId + ", Tipo Vehículo: " + tipoVehiculo + 
               ", Galones: " + cantidadGalones + ", Fecha y Hora: " + fechaHora;
    }
}

