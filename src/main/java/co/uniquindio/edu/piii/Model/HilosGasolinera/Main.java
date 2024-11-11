package co.uniquindio.edu.piii.Model.HilosGasolinera;

public class Main {
    public static void main(String[] args) {
        EstacionGasolina estacion = new EstacionGasolina();

        for (int i = 0; i < 3; i++) { 
            CamionProductor productor = new CamionProductor(estacion);
            estacion.agregarProductor(productor);
        }

        for (int i = 0; i < 11; i++) { 
            ClienteConsumidor cliente = new ClienteConsumidor(i % 2 == 0 ? "vehiculo" : "moto", estacion);
            estacion.agregarCliente(cliente);
        }
    }
}
