package biblioteca;

/**
 *
 * @author VdiRemy
 */
public class Aresta {
    private Vertice destino;
    private float peso;

    public Aresta(Vertice v_destino, float peso) {
        this.destino = v_destino;
        this.peso = peso;
    }

    public Vertice get_destino() {
        return this.destino;
    }
    
    public float get_peso(){
        return this.peso;
    }
}