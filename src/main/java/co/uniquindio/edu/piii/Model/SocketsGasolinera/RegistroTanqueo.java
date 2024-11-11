package co.uniquindio.edu.piii.Model.SocketsGasolinera;

import java.io.Serializable;

public class RegistroTanqueo implements Serializable {
    private String idCliente;
    private String tipoVehiculo;
    private double galones;
    private String fechaHora;

    public RegistroTanqueo(String idCliente, String tipoVehiculo, double galones, String fechaHora) {
        this.idCliente = idCliente;
        this.tipoVehiculo = tipoVehiculo;
        this.galones = galones;
        this.fechaHora = fechaHora;
    }

    // Getters y setters
    public String getIdCliente() { return idCliente; }
    public String getTipoVehiculo() { return tipoVehiculo; }
    public double getGalones() { return galones; }
    public String getFechaHora() { return fechaHora; }
}

