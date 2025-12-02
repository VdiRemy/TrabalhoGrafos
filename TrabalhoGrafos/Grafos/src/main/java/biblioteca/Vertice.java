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
public class Vertice<T> implements Comparable<Vertice<T>>{
    private T valor;
    private ArrayList<Aresta> destinos;
    
    //auxiliares para algoritmos
    public float distancia_minima = Float.MAX_VALUE; //começa com infinito
    public Vertice<T> pai = null; //para reconstruir o caminho (de onde eu vim)
    public boolean visitado = false; //para evitar repetição    
    
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

    public ArrayList<Aresta> get_destinos() {
        return this.destinos;
    }
    
    public void limpar(){
        this.distancia_minima = Float.MAX_VALUE; //começa com infinito
        this.pai = null; //para reconstruir o caminho (de onde eu vim)
        this.visitado = false; //para evitar repetição  
        }
    
    //ensina o java a ordenar os vertices. usado pela fila de prioridade (ou sort) 
    //para colocar sempre quem tem a menor distancia_minima primeiro
    @Override
    public int compareTo(Vertice<T> outro) {
        return Float.compare(this.distancia_minima, outro.distancia_minima);
    }
    //faz o print mostrar o nome da cidade (valor) em vez do codigo de memoria 
    //quando mandamos imprimir o objeto ou a lista
    @Override
    public String toString() { return valor.toString(); }
}
