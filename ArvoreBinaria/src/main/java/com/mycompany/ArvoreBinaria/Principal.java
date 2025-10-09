package com.mycompany.ArvoreBinaria;

import java.util.Comparator;

import com.mycompany.ArvoreBinaria.Aluno;
import com.mycompany.ArvoreBinaria.ArvoreBinaria;
import com.mycompany.ArvoreBinaria.lib.IArvoreBinaria;



public class Principal {
    public static void main(String[] args) {
        // Criando um comparador básico para alunos (por matrícula, por exemplo)
        Comparator<Aluno> comparador = (a1, a2) -> Integer.compare(a1.getMatricula(), a2.getMatricula());

        // Criando uma instância da árvore
        IArvoreBinaria<Aluno> arvore = new ArvoreBinaria<>(comparador);

        // Teste de todos os métodos com try-catch
        try {
            arvore.adicionar(new Aluno(1, "Fulano"));
            System.out.println("Método adicionar() funciona.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Método adicionar() ainda não implementado.");
        }

        try {
            arvore.pesquisar(new Aluno(1, "Fulano"));
            System.out.println("Método pesquisar() funciona.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Método pesquisar() ainda não implementado.");
        }

        try {
            arvore.remover(new Aluno(1, "Fulano"));
            System.out.println("Método remover() funciona.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Método remover() ainda não implementado.");
        }

        try {
            arvore.altura();
            System.out.println("Método altura() funciona.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Método altura() ainda não implementado.");
        }

        try {
            arvore.quantidadeNos();
            System.out.println("Método quantidadeNos() funciona.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Método quantidadeNos() ainda não implementado.");
        }

        try {
            arvore.caminharEmNivel();
            System.out.println("Método caminharEmNivel() funciona.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Método caminharEmNivel() ainda não implementado.");
        }

        try {
            arvore.caminharEmOrdem();
            System.out.println("Método caminharEmOrdem() funciona.");
        } catch (UnsupportedOperationException e) {
            System.out.println("Método caminharEmOrdem() ainda não implementado.");
        }

        System.out.println("Teste de integração com interface concluído.");
    }
}
