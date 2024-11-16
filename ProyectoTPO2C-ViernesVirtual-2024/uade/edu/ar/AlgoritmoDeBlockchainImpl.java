package uade.edu.ar;

import uade.edu.progra3.AlgoritmoDeBlockchain;
import uade.edu.progra3.model.Bloque;
import uade.edu.progra3.model.Transaccion;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del algoritmo de blockchain
 */
public class AlgoritmoDeBlockchainImpl implements AlgoritmoDeBlockchain {

    @Override
    public List<List<Bloque>> construirBlockchain(List<Transaccion> transacciones, // Algoritmo Base
                                                  int maxTamanioBloque,
                                                  int maxValorBloque,
                                                  int maxTransacciones,
                                                  int maxBloques) {
        List<List<Bloque>> soluciones = new ArrayList<>();
        List<Bloque> bloquesActuales = new ArrayList<>();
        List<Transaccion> transaccionesActuales = new ArrayList<>();
        Bloque bloqueActual = new Bloque();
        construirBlockchainRecursivo(transacciones, maxTamanioBloque, maxValorBloque, maxTransacciones, // se llama recursivamente al metodo countruirBlockchainRecursivo
                0, transaccionesActuales, soluciones, bloquesActuales,bloqueActual);
        return soluciones;
    }

    private void construirBlockchainRecursivo(List<Transaccion> transacciones, int maxTamanioBloque,// Backtracking
                                              int maxValorBloque, int maxTransacciones, int indice,
                                              List<Transaccion> transaccionesActuales, List<List<Bloque>> soluciones,
                                              List<Bloque> bloquesActuales,Bloque bloqueActual) {
        // SI los datos son valido para un bloque , se crea un nuevo bloque
        if (esBloqueValido(transaccionesActuales, maxValorBloque, maxTamanioBloque, maxTransacciones)) {

            bloquesActuales.add(bloqueActual); // Agrego el nuevo bloque solo si no existe en la lista
            soluciones.add(bloquesActuales); // Se agrega como posible solucion

            // Intento Agregar mas transacciones al bloque actual
            for (int i = indice; i < transacciones.size(); i++) {
                Transaccion transaccion = transacciones.get(i);
                // Pregunta si se puede guardar la transaccion
                if (sePuedeAgregar(transaccion, transaccionesActuales)) { // Verifica si cumple con los permisos para guardarolo
                    transaccionesActuales.add(transaccion);// Agrega la transaccion a la lista transacciones
                    bloqueActual.setTransacciones(transaccionesActuales); // Agrega las transacciones al bloque
                    // Explora el siguiente nivel del arbol de combinaciones
                    construirBlockchainRecursivo(transacciones, maxTamanioBloque, maxValorBloque, maxTransacciones,
                            i + 1, transaccionesActuales, soluciones, bloquesActuales, bloqueActual);
                    transaccionesActuales.remove(transaccion);
                    bloqueActual.setTransacciones(transaccionesActuales); // Saca la ultima transaccion del bloque

                }
            }
            if (!bloquesActuales.isEmpty()) {
                bloquesActuales.remove(bloqueActual);
            }
        }

    }



    private boolean esBloqueValido(List<Transaccion> bloque, int maxTamanioBloque, int maxValorBloque,
                                   int maxTransacciones) {
        int tamanioTotal = calcularTamanioTotal(bloque);
        int valorTotal = calcularValorTotal(bloque);
        int numTransacciones = bloque.size();
        return tamanioTotal <= maxTamanioBloque && valorTotal <= maxValorBloque &&
                numTransacciones <= maxTransacciones && valorTotal % 10 == 0;
    }

    private int calcularTamanioTotal(List<Transaccion> bloque) {
        int tamanioTotal = 0;
        for (Transaccion transaccion : bloque) {
            tamanioTotal += transaccion.getTamanio();
        }
        return tamanioTotal;
    }

    private int calcularValorTotal(List<Transaccion> bloque) {
        int valorTotal = 0;
        for (Transaccion transaccion : bloque) {
            valorTotal += transaccion.getValor();
        }
        return valorTotal;
    }

    private boolean sePuedeAgregar(Transaccion transaccion, List<Transaccion> transacciones) {
        Transaccion dependencia = transaccion.getDependencia();
        if (dependencia != null && !transacciones.contains(dependencia)) {
            return false;
        }
        return transaccion.getFirmasActuales() >= transaccion.getFirmasRequeridas();
    }

}
