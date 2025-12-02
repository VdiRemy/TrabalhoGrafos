package biblioteca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * @author gainn
 * @param <T>
 */
public class Grafo<T> {
    private ArrayList<Vertice<T>> vertices;
    
    public Grafo(){
        this.vertices =  new ArrayList<>();
    }
    
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
    
    public void busca_em_largura(){
        ArrayList<Vertice> marcados = new ArrayList<Vertice>();
        ArrayList<Vertice> fila = new ArrayList<Vertice>();
        //começar a partir do primeiro vertice, o colocando na fila
        Vertice atual = this.vertices.get(0);
        fila.add(atual);
        System.out.println("Busca em largura a partir do vertice: " + atual.get_valor());
        //enquanto houver vertice na fila
        while (fila.size()>0){
            //itera sobre o proximo, adicionando na lista de marcados e imprimindo
            atual = fila.get(0);
            fila.remove(0);
            marcados.add(atual);
            System.out.println(atual.get_valor());
            //a partir da lista de adjacencia, verificar se no adjacente foi visitado, caso nao, adiciona na fila
            ArrayList<Aresta> destinos = atual.get_destinos();
            Vertice proximo;
            for(int i=0; i<destinos.size();i++){
                proximo=destinos.get(i).get_destino();
                if(!marcados.contains(proximo)&&!fila.contains(proximo)){
                    fila.add(proximo);
                }
            }
        }
    }
    
    public void dijkstra(T origem, T destino){
        Vertice<T> v_origem = obter_vertice(origem);
        Vertice<T> v_destino = obter_vertice(destino);
        
        if (v_origem == null || v_destino == null){
            System.out.println("Erro, origem ou destino nao existem");
            return;
        }
        //zera distancia e pai
        reiniciar_grafo();
        
        v_origem.distancia_minima = 0;
        
        //fila de prioridade
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
            Vertice<T> atual = fila.poll(); //pega o mais perto
            
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
        
        if (passo.pai==null &&passo.distancia_minima == Float.MAX_VALUE){
            System.out.println("nao existe caminho");
            return;
        }
        while (passo != null){
            caminho.add(passo.get_valor());
            passo = passo.pai;
        }
        Collections.reverse(caminho);
        System.out.println("menor rota: " + caminho +"(custo: "+destino.distancia_minima+")");
        
        
    }
    
    public void prim(T inicio){
        Vertice<T> vInicio = obter_vertice(inicio);
        if (vInicio == null) return;

        reiniciar_grafo();
        float custoTotal = 0;
        
        // No Prim, a "distância" representa o custo para conectar à árvore
        vInicio.distancia_minima = 0;
        
        PriorityQueue<Vertice<T>> fila = new PriorityQueue<>();
        fila.add(vInicio);

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
