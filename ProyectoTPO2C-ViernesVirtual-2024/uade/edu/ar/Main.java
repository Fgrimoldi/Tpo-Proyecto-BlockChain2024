package uade.edu.ar;

import uade.edu.ar.util.TransaccionUtils;
import uade.edu.progra3.AlgoritmoDeBlockchain;
import uade.edu.progra3.model.Bloque;
import uade.edu.progra3.model.Transaccion;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AlgoritmoDeBlockchain algoritmoDeBlockchain = new AlgoritmoDeBlockchainImpl();
        List<Transaccion> transacciones = TransaccionUtils.crearTransaccionesSimples(10, 100, 1000, 5);
        List<List<Bloque>> blockchain = algoritmoDeBlockchain.construirBlockchain(transacciones, 1000, 10, 5, 5);

        // Mostrar bloques por pantalla
        for (int i = 0; i < blockchain.size(); i++) {
            System.out.println("Cadena de bloques " + (i + 1) + ":");
            List<Bloque> cadena = blockchain.get(i);

            for (int j = 0; j < blockchain.size(); j++) {
                Bloque bloque = cadena.get(j);
                System.out.println(" Bloque " + (j + 1) + ":");
                System.out.println("     Tamaño total: " + bloque.getTamanioTotal());
                System.out.println("     Valor total:  " + bloque.getValorTotal());
                System.out.println("      Transacciones:");

                for (Transaccion transaccion : bloque.getTransacciones()) {
                    System.out.println("       Tamaño: " + transaccion.getTamanio() +
                            ", Valor: " + transaccion.getValor());

                }
            }
            System.out.println();
        }
    }
}

