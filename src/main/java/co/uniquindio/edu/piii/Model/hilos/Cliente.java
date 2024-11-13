package co.uniquindio.edu.piii.Model.hilos;

/**
 * La clase Cliente representa a un cliente (vehículo o motocicleta) que consume gasolina.
 * Los vehículos consumen 10 galones en cada carga, y las motocicletas consumen 4 galones.
 */
public class Cliente extends Thread {
    private Tanque tanque; // Referencia al tanque que se consume
    private int consumo;   // Cantidad de gasolina que consume este cliente

    /**
     * Constructor de la clase Cliente.
     * 
     * @param tanque El tanque de gasolina compartido
     * @param consumo Cantidad de gasolina que consume este cliente
     */
    public Cliente(Tanque tanque, int consumo) {
        this.tanque = tanque;
        this.consumo = consumo;
    }

    /**
     * Método que ejecuta el ciclo de consumo del cliente.
     * Intenta consumir gasolina repetidamente con un tiempo de espera entre cada carga.
     */
    @Override
    public void run() {
        try {
            while (true) {
                tanque.consumir(consumo); // Consume gasolina según el tipo de cliente
                Thread.sleep(500); // Simula el tiempo de espera antes de volver a cargar
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Interrumpe el hilo si es necesario
        }
    }
}