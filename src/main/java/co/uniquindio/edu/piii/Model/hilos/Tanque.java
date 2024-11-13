package co.uniquindio.edu.piii.Model.hilos;

/**
 * La clase Tanque representa el tanque de gasolina en una estación de servicio.
 * Tiene una capacidad máxima de 1000 galones y métodos sincronizados para 
 * permitir que los productores lo abastezcan y los clientes lo consuman de manera
 * segura y concurrente.
 */
public class Tanque {
    private int capacidadMaxima = 1000; // Capacidad máxima del tanque en galones
    private int gasolinaActual = 0;     // Cantidad actual de gasolina en el tanque

    /**
     * Método sincronizado que permite a los productores abastecer el tanque.
     * Si el tanque no tiene suficiente espacio, el productor espera hasta que haya espacio.
     * 
     * @param cantidad Cantidad de gasolina que se desea abastecer
     * @throws InterruptedException Excepción lanzada si el hilo es interrumpido
     */
    public synchronized void abastecer(int cantidad) throws InterruptedException {
        while (gasolinaActual + cantidad > capacidadMaxima) {
            // Espera hasta que haya espacio en el tanque
            wait();
        }
        gasolinaActual += cantidad; // Abastece el tanque con la cantidad especificada
        System.out.println("Abastecido con " + cantidad + " galones. Gasolina actual: " + gasolinaActual);
        notifyAll(); // Notifica a los clientes que ahora pueden consumir
    }

    /**
     * Método sincronizado que permite a los clientes consumir gasolina del tanque.
     * Si el tanque no tiene suficiente gasolina, el cliente espera hasta que haya suficiente.
     * 
     * @param cantidad Cantidad de gasolina que el cliente desea consumir
     * @throws InterruptedException Excepción lanzada si el hilo es interrumpido
     */
    public synchronized void consumir(int cantidad) throws InterruptedException {
        while (gasolinaActual < cantidad) {
            // Espera hasta que haya suficiente gasolina en el tanque
            wait();
        }
        gasolinaActual -= cantidad; // Reduce la gasolina actual por la cantidad consumida
        System.out.println("Consumido " + cantidad + " galones. Gasolina restante: " + gasolinaActual);
        notifyAll(); // Notifica a los productores que ahora pueden abastecer
    }

    /**
     * Método que devuelve la cantidad actual de gasolina en el tanque.
     * 
     * @return Gasolina actual en el tanque
     */
    public int getGasolinaActual() {
        return gasolinaActual;
    }
}