package co.uniquindio.edu.piii.Model.HilosGasolinera;

public class ClienteConsumidor extends Thread {
    private int consumo;
    private EstacionGasolina estacion;

    public ClienteConsumidor(String tipo, EstacionGasolina estacion) {
        this.estacion = estacion;
        this.consumo = tipo.equalsIgnoreCase("vehiculo") ? 10 : 4;
    }

    @Override
    public void run() {
        try {
            estacion.consumirGasolina(consumo);
            Thread.sleep(1500); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
