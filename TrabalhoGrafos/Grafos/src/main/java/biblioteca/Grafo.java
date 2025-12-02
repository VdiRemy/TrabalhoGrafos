package biblioteca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author VdiRemy
 * @param <T>
 */
public class Grafo<T> {
    private ArrayList<Vertice<T>> vertices;
    
    public Grafo(){
        this.vertices =  new ArrayList<>();
    }
    
    // metodo auxiliar para limpar sujeira de execucoes anteriores (distancias e pais)
    public void reiniciar_grafo(){
        for (Vertice<T> v : this.vertices){
            v.limpar();
        }
    }
    
    public Vertice<T> adicionar_vertice(T valor){
        Vertice<T> novo = new Vertice<T>(valor);
        
        this.vertices.add(novo);
        return novo;
    }
    
    private Vertice obter_vertice(T valor){
        Vertice v;
        for(int i=0;i<this.vertices.size();i++){
            v=this.vertices.get(i);
            if(v.get_valor().equals(valor)){
                return v;
            }
        }
        //percorreu tudo e nao encontrou
        return null;
    }
    
    public void adicionar_Aresta(T origem, T destino, float peso){
        Vertice v_origem,v_destino;
        
        //encontrar vertice correspondente ao valor de origem
        v_origem = obter_vertice(origem);
        
        //se esse vertice nao existe, criar ele
        
        if(v_origem==null){
            v_origem=adicionar_vertice(origem);
        }
        
        v_destino = obter_vertice(destino);
        
        if(v_destino==null){
            v_destino=adicionar_vertice(destino);
        }
        
        v_origem.adicionar_destino(new Aresta(v_destino, peso));
    } 
    
    public void busca_em_largura() {
            // verificacao de seguranca para grafo vazio
            if (this.vertices == null || this.vertices.isEmpty()) {
                System.out.println("O grafo está vazio. Adicione vértices antes de buscar.");
                return;
            }

            // Limpa dados antigos (níveis e pais) para não misturar com o Dijkstra
            reiniciar_grafo();

            ArrayList<Vertice<T>> marcados = new ArrayList<>();
            ArrayList<Vertice<T>> fila = new ArrayList<>();

            // Começar a partir do primeiro vértice
            Vertice<T> atual = this.vertices.get(0);

            // Configura a origem
            atual.distancia_minima = 0;
            fila.add(atual);

            System.out.println("\nBusca em largura a partir do vertice: " + atual.get_valor());

            while (fila.size() > 0) {
                atual = fila.get(0);
                fila.remove(0);
                marcados.add(atual);

                
                String info = "-> " + atual.get_valor() + " [Nível " + (int)atual.distancia_minima + "]";

                if (atual.pai != null) {
                    info += " (descoberto via " + atual.pai.get_valor() + ")";
                }

                System.out.println(info);
                

                ArrayList<Aresta> destinos = atual.get_destinos();
                Vertice<T> proximo;

                for (int i = 0; i < destinos.size(); i++) {
                    proximo = destinos.get(i).get_destino();

                    if (!marcados.contains(proximo) && !fila.contains(proximo)) {
                        
                        // calcula o nível do vizinho (Nível do Pai + 1)
                        proximo.distancia_minima = atual.distancia_minima + 1;
                        // salva quem descobriu ele
                        proximo.pai = atual;
                        // ---------------------------

                        fila.add(proximo);
                    }
                }
            }
            System.out.println("-----------------------------------");
        }
    
    public void dijkstra(T origem, T destino){
        // verificacao de seguranca para grafo vazio
        if (this.vertices == null || this.vertices.isEmpty()) {
            System.out.println("O grafo está vazio.");
            return; // Sai do método para não dar erro lá embaixo
        }
        
        Vertice<T> v_origem = obter_vertice(origem);
        Vertice<T> v_destino = obter_vertice(destino);
        
//        if (v_origem == null || v_destino == null){
//            System.out.println("Erro, origem ou destino nao existem");
//            return;
//        }
        //com esse método não da pra identificar ao certo qual o problema, seperar em 2 para mais informações

        // verificacoes de existencia dos vertices
        if (v_origem == null) {
            System.out.println("Erro: A cidade de origem '" + origem + "' não existe no mapa.");
            return;
        }
        if (v_destino == null) {
            System.out.println("Erro: A cidade de destino '" + destino + "' não existe no mapa.");
            return;
        }
        
        //zera distancia e pai
        reiniciar_grafo();
        
        v_origem.distancia_minima = 0;
        
        //fila de prioridade (usa o compareTo do Vertice para ordenar pelo menor custo)
        PriorityQueue<Vertice<T>> fila = new PriorityQueue<>();
        fila.add(v_origem);
//        implementacao com listas
//        ArrayList<Vertice<T>> fila = new ArrayList<>();
//        // Varre a lista inteira para achar quem tem a menor distância
//        Vertice<T> atual = fila.get(0);
//        for (Vertice<T> v : fila) {
//            if (v.distanciaMinima < atual.distanciaMinima) {
//                atual = v;
//            }
//        }
//        fila.remove(atual);;

        while(!fila.isEmpty()){
            // estrategia gulosa: pega sempre o vertice mais proximo (menor distancia) da fila
            Vertice<T> atual = fila.poll(); 
            
            if (atual.visitado) continue;
            atual.visitado = true;
            
            if (atual.equals(v_destino)) break;
            
            for(Aresta aresta : atual.get_destinos()){
                Vertice<T> vizinho = aresta.get_destino();
                float nova_distancia = atual.distancia_minima + aresta.get_peso();
                
                //se achou o mais curto, atualiza
                if (nova_distancia<vizinho.distancia_minima){
                    vizinho.distancia_minima = nova_distancia;
                    vizinho.pai = atual;
                    
                    // remove e readiciona para atualizar a posicao na fila de prioridade
                    fila.remove(vizinho);
                    fila.add(vizinho);
                }
                
            }
        }
        reconstruir_caminho(v_destino);
    }

    private void reconstruir_caminho(Vertice<T> destino) {
        //lista para armazenar caminho
        List<T> caminho = new ArrayList<>();
        Vertice<T> passo = destino;
        
        // se nao tem pai e a distancia continua infinita, nao houve conexao
        if (passo.pai==null &&passo.distancia_minima == Float.MAX_VALUE){
            System.out.println("nao existe caminho");
            return;
        }
        
        //volta do destino ate a origem pelos pais
        while (passo != null){
            caminho.add(passo.get_valor());
            passo = passo.pai;
        }
        Collections.reverse(caminho); // inverte para mostrar na ordem correta
        System.out.println("menor rota: " + caminho +"(custo: "+destino.distancia_minima+")");
        
        
    }
    
    public void prim(T inicio){
        //verificar se grafo ta vazio
        if (this.vertices == null || this.vertices.isEmpty()) {
            System.out.println("O grafo está vazio.");
            return; // Sai do método para não dar erro lá embaixo
        }
                
        Vertice<T> v_inicio = obter_vertice(inicio);
        
        
        if (v_inicio == null){
            System.out.println("Erro: A cidade inicial '" + inicio + "' não existe.");
            return;
        }

        reiniciar_grafo();
        float custoTotal = 0;
        
        // No Prim, a "distância" representa o custo para conectar à árvore
        v_inicio.distancia_minima = 0;
        
        PriorityQueue<Vertice<T>> fila = new PriorityQueue<>();
        fila.add(v_inicio);

        System.out.println("\n--- Planejamento (Prim) ---");

        while (!fila.isEmpty()) {
            Vertice<T> atual = fila.poll();

            if (atual.visitado) continue;
            atual.visitado = true;

            // Se tem pai, imprime a conexão
            if (atual.pai != null) {
                System.out.println("Conectar " + atual.pai.get_valor() + " a " + atual.get_valor());
                custoTotal += (atual.distancia_minima); // Aqui distance é o peso da aresta
            }

            for (Aresta a : atual.get_destinos()) {
                Vertice<T> vizinho = a.get_destino();
                
                // Se vizinho não está na árvore e aresta é mais barata que a conexão atual dele
                if (!vizinho.visitado && a.get_peso() < vizinho.distancia_minima) {
                    vizinho.distancia_minima = a.get_peso();
                    vizinho.pai = atual;
                    
                    fila.remove(vizinho);
                    fila.add(vizinho);
                }
            }
        }
        System.out.println("Custo Total: " + custoTotal);
    }
}
