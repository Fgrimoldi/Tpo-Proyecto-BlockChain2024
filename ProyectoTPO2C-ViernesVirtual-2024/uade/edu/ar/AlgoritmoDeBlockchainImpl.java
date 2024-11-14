package uade.edu.ar;

import uade.edu.progra3.AlgoritmoDeBlockchain;
import uade.edu.progra3.model.Bloque;
import uade.edu.progra3.model.Transaccion;


import java.util.ArrayList;
import java.util.List;
/**
 * @author AlejandroFoglino
 */
public class AlgoritmoDeBlockchainImpl implements AlgoritmoDeBlockchain {


    @Override
    public List<List<Bloque>> construirBlockchain(List<Transaccion> transacciones,
                                                  int maxTamanioBloque,
                                                  int maxValorBloque,
                                                  int maxTransacciones,
                                                  int maxBloques) {
        List<List<Bloque>>soluciones= new ArrayList<>();
        List<Bloque>bloquesActuales = new ArrayList<>();
        List<Transaccion> bloqueActual = new ArrayList<>();

        return null;
    }
    private void construirBLockchainRecurisvo(List<Transaccion> transaccions,int maxTamanioBloque,int maxValorBloque,
                                              int maxTransacciones, int indice,List<Transaccion> bloqueActual,
                                              List<List<Bloque>> soluciones,List<Bloque>bloquesActuales){
        if(esBloequeValido())


    }
    private boolean esBloequeValido(List<Transaccion> bloque, int maxTamanioBloque,int maxValorBloque,
                                    int maxTransacciones){ // Analizo el bloque entero
        int tamnioTotal = calcularTamanioTotal(bloque);
        int valorTotal = calcularValorTotal(bloque);
        int numeTransacciones = bloque.size();

    }
    private int calcularTamanioTotal(List<Transaccion> bloque){
        int tamnioTotal = 0;
        for (Transaccion transaccion : bloque){
            tamnioTotal += transaccion.getTamanio();
        }
        return tamnioTotal;
    }
    private int calcularValorTotal(List<Transaccion> bloque){
        int valorTotal = 0;
        for (Transaccion transaccion : bloque){
            valorTotal += transaccion.getValor();
        }
        return valorTotal;
    }
    private boolean sePuedeAgregar(Transaccion transaccion, List<Transaccion> bloque){
        for (Transaccion dependencia : transaccion.getDependencia()){
            if(!bloque.contains(dependencia)) {
                return false;
            }
        }
        return true;
    }
}
