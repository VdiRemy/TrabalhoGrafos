package biblioteca;

import java.util.ArrayList;

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
}
