/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca;

import java.util.ArrayList;

/**
 *
 * @author VdirRemy
 * @param <T>
 */
public class Vertice<T> {
    private T valor;
    private ArrayList<Aresta> destinos;
    
    public Vertice(T valor){
        this.valor = valor;
        //lista para receber destinos
        this.destinos = new ArrayList<>();
    }
    
    public void adicionar_destino(Aresta aresta){
        this.destinos.add(aresta);
    }
    
    /**
     *
     * @return valor
     */
    public T get_valor() {
        return this.valor;
    }

    ArrayList<Aresta> get_destinos() {
        return this.destinos;
    }

    
    
}
