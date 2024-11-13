package co.uniquindio.edu.piii.Model.hilos;

/**
 * La clase SimulacionEstacion configura y ejecuta la simulación de la estación de servicio.
 * Crea un tanque compartido, inicializa productores y clientes, y permite detener la simulación
 * después de un tiempo predefinido.
 */
public class SimulacionEstacion {
    public static void main(String[] args) {
        // Crear el tanque compartido entre productores y clientes
        Tanque tanque = new Tanque();

        // Crear y empezar hilos para productores (1 a 4 camiones cisterna)
        Productor productor1 = new Productor(tanque);
        Productor productor2 = new Productor(tanque);
        productor1.start();
        productor2.start();

        // Crear y empezar hilos para clientes con diferentes consumos
        Cliente vehiculo1 = new Cliente(tanque, 10); // vehículo
        Cliente moto1 = new Cliente(tanque, 4);      // motocicleta
        vehiculo1.start();
        moto1.start();

        // Detener la simulación después de un tiempo determinado (20 segundos en este caso)
        try {
            Thread.sleep(20000); // Tiempo de ejecución de la simulación
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Interrumpir todos los hilos al finalizar la simulación
        productor1.interrupt();
        productor2.interrupt();
        vehiculo1.interrupt();
        moto1.interrupt();
        System.out.println("Simulación finalizada.");
    }
}