/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ArvoreBinaria;

import com.mycompany.ArvoreBinaria.lib.IArvoreBinaria;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author victoriocarvalho
 */
public class ArvoreBinaria<T> implements IArvoreBinaria<T> {
    
    protected No<T> raiz = null;
    protected Comparator<T> comparador; 
  
    public ArvoreBinaria(Comparator<T> comp) {
        comparador = comp;
    }
    
    @Override
    public void adicionar(T novoValor) {
        //Se a raiz está nula, vamos alocá-la e armazenar a informação nela
        No<T> novoNo = new No<>(novoValor);
        if (raiz == null){
            
            raiz = novoNo;
            novoNo.setFilhoEsquerda(null);
            novoNo.setFilhoDireita(null);
        }
        else{
            No<T> atual = raiz;
            while(true){
                //Se a raiz não for nula e a informação for menor que a armazenada na raiz
                //vamos inserir na sub-arvore esquerda
                int cmp = comparador.compare(novoValor, atual.getValor());

                if (cmp < 0){
                    if (atual.getFilhoEsquerda()==null) {
                        atual.setFilhoEsquerda(novoNo);
                        return;
                    }
                    atual = atual.getFilhoEsquerda();
                } else {
                    if (atual.getFilhoDireita()==null){
                        atual.setFilhoDireita(novoNo);
                        return;
                    }
                    atual = atual.getFilhoDireita();
                }
            }
        }
   }

    @Override
    public T pesquisar(T valor) {
        if (valor == null){
            return null;
        } else {
            
            No<T> atual = this.raiz;
            while (atual != null){
                int cmp = this.comparador.compare(valor, atual.getValor());
                
                if (cmp < 0){
                    atual = atual.getFilhoEsquerda();
                }
                
                else if (cmp > 0){
                    atual = atual.getFilhoDireita();
                }
                
                else {
                    return atual.getValor();
                }
                
            }
            return null;
        }
    }

    private T pesquisar(No<T> no, T valor, Comparator<T> comparador){
        if (no == null){
            return null;
        } else{
            int cmp = comparador.compare(valor, no.getValor());
            
            if (cmp == 0){
                return no.getValor();
            }
            
            T resultado_esq = pesquisar(no.getFilhoEsquerda(), valor, comparador);
            if (resultado_esq != null){
                return resultado_esq;
            }
            
            T resultado_dir = pesquisar(no.getFilhoDireita(), valor, comparador);
            if (resultado_dir != null){
                return resultado_dir;
            }
            return null;
    }
    }
    @Override
    public T pesquisar(T valor, Comparator comparador) {
        Comparator<T> cmpAux = (Comparator<T>) comparador;
        return pesquisar(this.raiz, valor, cmpAux);
   }

    @Override
    public T remover(T valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private int altura(No<T> no){
        if (no == null){
            return -1;
        } else{
            int altura_esq = altura(no.getFilhoEsquerda());
            int altura_dir = altura(no.getFilhoDireita());
            int maior = Math.max(altura_esq, altura_dir);
            int resultado = maior + 1;
            return resultado;
        }
        
    }
    

    @Override
    public int altura() {
        return altura(this.raiz);

    } 
       
    private int quantidadeNos(No<T> no){
        if (no == null){
            return 0;
        } else{
            int qtd_nosDir = quantidadeNos(no.getFilhoDireita());
            int qtd_nosEsq = quantidadeNos(no.getFilhoEsquerda());
            return qtd_nosDir + qtd_nosEsq + 1;
        }
    }
    
    @Override
    public int quantidadeNos() {
        return quantidadeNos(this.raiz);
    }

    @Override
    public String caminharEmNivel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.    
    }
    
    @Override
    public String caminharEmOrdem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.    
    }
        
}