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
    public List<List<Bloque>> construirBlockchain(List<Transaccion> transacciones,
                                                  int maxTamanioBloque,
                                                  int maxValorBloque,
                                                  int maxTransacciones,
                                                  int maxBloques) {
        List<List<Bloque>> soluciones = new ArrayList<>();
        List<Bloque> bloquesActuales = new ArrayList<>();
        construirBlockchainRecursivo(transacciones, maxTamanioBloque, maxValorBloque, maxTransacciones,
                0, new ArrayList<>(), soluciones, bloquesActuales);
        return soluciones;
    }

    private void construirBlockchainRecursivo(List<Transaccion> transacciones, int maxTamanioBloque,
                                              int maxValorBloque, int maxTransacciones, int indice,
                                              List<Transaccion> bloqueActual, List<List<Bloque>> soluciones,
                                              List<Bloque> bloquesActuales) {
        if (esBloqueValido(bloqueActual, maxValorBloque, maxTamanioBloque, maxTransacciones)) {
            Bloque nuevoBloque = new Bloque();
            nuevoBloque.setTransacciones(new ArrayList<>(bloqueActual));
            nuevoBloque.setTamanioTotal(calcularTamanioTotal(bloqueActual));
            nuevoBloque.setValorTotal(calcularValorTotal(bloqueActual));
            bloquesActuales.add(nuevoBloque);

            if (indice == transacciones.size()) {
                soluciones.add(new ArrayList<>(bloquesActuales));
            } else {
                for (int i = indice; i < transacciones.size(); i++) {
                    Transaccion transaccion = transacciones.get(i);
                    if (sePuedeAgregar(transaccion, bloqueActual)) {
                        bloqueActual.add(transaccion);
                        construirBlockchainRecursivo(transacciones, maxTamanioBloque, maxValorBloque, maxTransacciones,
                                i + 1, bloqueActual, soluciones, bloquesActuales);
                        bloqueActual.remove(bloqueActual.size() - 1);
                    }
                }
                bloquesActuales.remove(bloquesActuales.size() - 1);
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

    private boolean sePuedeAgregar(Transaccion transaccion, List<Transaccion> bloqueActual) {
        Transaccion dependencia = transaccion.getDependencia();
        if (dependencia != null && !bloqueActual.contains(dependencia)) {
            return false;
        }

        if (transaccion.getFirmasActuales() < transaccion.getFirmasRequeridas()) {
            return false;
        }
        return true;
    }
}
