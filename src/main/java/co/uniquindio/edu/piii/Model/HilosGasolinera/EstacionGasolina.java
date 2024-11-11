package co.uniquindio.edu.piii.Model.HilosGasolinera;

import java.util.LinkedList;

public class EstacionGasolina {
    private final int CAPACIDAD_MAX = 1000;
    private int gasolinaActual = 0;
    private LinkedList<CamionProductor> productores;
    private LinkedList<ClienteConsumidor> clientes;

    public EstacionGasolina() {
        this.productores = new LinkedList<>();
        this.clientes = new LinkedList<>();
    }

    public synchronized void abastecerGasolina(int cantidad) throws InterruptedException {
        while (gasolinaActual + cantidad > CAPACIDAD_MAX) {
            System.out.println("El tanque está lleno. El camión espera...");
            wait();
        }
        gasolinaActual += cantidad;
        System.out.println("Camión depositó " + cantidad + " galones. Gasolina actual: " + gasolinaActual);
        notifyAll();
    }

    public synchronized void consumirGasolina(int cantidad) throws InterruptedException {
        while (gasolinaActual < cantidad) {
            System.out.println("Se acabó la gasolina. El cliente espera...");
            wait();
        }
        gasolinaActual -= cantidad;
        System.out.println("Cliente tanqueó " + cantidad + " galones. Gasolina actual: " + gasolinaActual);
        notifyAll();

    }
        
    public void agregarProductor(CamionProductor productor) {
        productores.add(productor);
        productor.start();
    }

    public void agregarCliente(ClienteConsumidor cliente) {
        clientes.add(cliente);
        cliente.start();
    }
}
