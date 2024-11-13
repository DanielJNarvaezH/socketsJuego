package co.uniquindio.edu.piii.Model.hilos;

/**
 * La clase Productor representa a un camión cisterna que abastece gasolina al tanque.
 * Cada productor tiene una cantidad inicial de 100 galones de gasolina, y en cada ciclo
 * abastece el tanque con 20 galones hasta que su carga se agota.
 */
public class Productor extends Thread {
    private Tanque tanque; // Referencia al tanque que se abastece
    private int carga = 100; // Carga inicial de gasolina del productor

    /**
     * Constructor de la clase Productor.
     * 
     * @param tanque El tanque de gasolina compartido entre los hilos
     */
    public Productor(Tanque tanque) {
        this.tanque = tanque;
    }

    /**
     * Método que ejecuta el ciclo de abastecimiento del productor.
     * Abastece el tanque con 20 galones hasta que se agota su carga inicial.
     */
    @Override
    public void run() {
        try {
            while (carga > 0) {
                tanque.abastecer(20); // Abastece 20 galones en cada ciclo
                carga -= 20;          // Reduce la carga del productor
                Thread.sleep(1000);   // Simula el tiempo entre abastecimientos
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Interrumpe el hilo si es necesario
        }
    }
}