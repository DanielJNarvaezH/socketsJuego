package co.uniquindio.edu.piii.Model.HilosGasolinera;

public class CamionProductor extends Thread {
    private static final int CAPACIDAD_MAX = 100;
    private int capacidadActual = CAPACIDAD_MAX;
    private EstacionGasolina estacion;

    public CamionProductor(EstacionGasolina estacion) {
        this.estacion = estacion;
    }

    @Override
    public void run() {
        while (capacidadActual > 0) {
            try {
                estacion.abastecerGasolina(20);
                capacidadActual -= 20;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
